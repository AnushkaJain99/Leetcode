class Solution {
    public int minScore(int n, int[][] roads) {

        List<int[]>[] graph = new ArrayList[n + 1];

        for (int i = 1; i <= n; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int[] road : roads) {
            int u = road[0];
            int v = road[1];
            int d = road[2];

            graph[u].add(new int[]{v, d});
            graph[v].add(new int[]{u, d});
        }

        boolean[] visited = new boolean[n + 1];

        return dfs(1, graph, visited);
    }

    private int dfs(int node, List<int[]>[] graph, boolean[] visited) {

        visited[node] = true;

        int ans = Integer.MAX_VALUE;

        for (int[] edge : graph[node]) {

            int next = edge[0];
            int dist = edge[1];

            ans = Math.min(ans, dist);

            if (!visited[next]) {
                ans = Math.min(ans, dfs(next, graph, visited));
            }
        }

        return ans;
    }
}