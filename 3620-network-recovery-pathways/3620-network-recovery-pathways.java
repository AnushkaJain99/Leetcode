import java.util.*;

class Solution {
    public int findMaxPathScore(int[][] edges, boolean[] online, long k) {
        int n = online.length;

        List<List<Integer>> adjFull = new ArrayList<>();
        for (int i = 0; i < n; i++) adjFull.add(new ArrayList<>());
        int[] indeg = new int[n];

        List<int[]> validEdges = new ArrayList<>();

        for (int[] e : edges) {
            int u = e[0], v = e[1], c = e[2];
            adjFull.get(u).add(v);
            indeg[v]++;
            if (online[u] && online[v]) {
                validEdges.add(new int[]{u, v, c});
            }
        }

        int[] order = new int[n];
        int idx = 0;
        int[] indegCopy = indeg.clone();
        Deque<Integer> dq = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            if (indegCopy[i] == 0) dq.offer(i);
        }
        while (!dq.isEmpty()) {
            int u = dq.poll();
            order[idx++] = u;
            for (int v : adjFull.get(u)) {
                if (--indegCopy[v] == 0) dq.offer(v);
            }
        }

        List<List<long[]>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
        TreeSet<Long> costSet = new TreeSet<>();

        for (int[] e : validEdges) {
            int u = e[0], v = e[1], c = e[2];
            adj.get(u).add(new long[]{v, c});
            costSet.add((long) c);
        }

        if (costSet.isEmpty()) return -1;

        long[] cand = new long[costSet.size()];
        int i2 = 0;
        for (long c : costSet) cand[i2++] = c;

        int lo = 0, hi = cand.length - 1;
        long ans = -1;

        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (feasible(n, order, adj, cand[mid], k)) {
                ans = cand[mid];
                lo = mid + 1;
            } else {
                hi = mid - 1;
            }
        }

        return (int) ans;
    }

    private boolean feasible(int n, int[] order, List<List<long[]>> adj, long t, long k) {
        long[] dist = new long[n];
        Arrays.fill(dist, Long.MAX_VALUE);
        dist[0] = 0;

        for (int u : order) {
            if (dist[u] == Long.MAX_VALUE) continue;
            long du = dist[u];
            for (long[] edge : adj.get(u)) {
                int v = (int) edge[0];
                long c = edge[1];
                if (c >= t && du + c < dist[v]) {
                    dist[v] = du + c;
                }
            }
        }

        return dist[n - 1] != Long.MAX_VALUE && dist[n - 1] <= k;
    }
}