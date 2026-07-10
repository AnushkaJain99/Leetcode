import java.util.*;

class Solution {
    public int[] pathExistenceQueries(int n, int[] nums, int maxDiff, int[][] queries) {
        Integer[] idx = new Integer[n];
        for (int i = 0; i < n; i++) idx[i] = i;
        Arrays.sort(idx, (a, b) -> Integer.compare(nums[a], nums[b]));

        int[] rank = new int[n];
        int[] vals = new int[n];
        for (int i = 0; i < n; i++) {
            int orig = idx[i];
            rank[orig] = i;
            vals[i] = nums[orig];
        }
        int[] comp = new int[n];
        for (int i = 1; i < n; i++) {
            if (vals[i] - vals[i - 1] <= maxDiff) {
                comp[i] = comp[i - 1];
            } else {
                comp[i] = comp[i - 1] + 1;
            }
        }

        
        int[] reach = new int[n];
        int j = 0;
        for (int i = 0; i < n; i++) {
            if (j < i) j = i;
            while (j + 1 < n && vals[j + 1] - vals[i] <= maxDiff) {
                j++;
            }
            reach[i] = j;
        }

        int LOG = Math.max(1, 32 - Integer.numberOfLeadingZeros(Math.max(n, 1)));
        int[][] up = new int[LOG + 1][n];
        up[0] = reach;
        for (int k = 1; k <= LOG; k++) {
            int[] prev = up[k - 1];
            for (int i = 0; i < n; i++) {
                up[k][i] = prev[prev[i]];
            }
        }

        int m = queries.length;
        int[] ans = new int[m];

        for (int qi = 0; qi < m; qi++) {
            int u = queries[qi][0];
            int v = queries[qi][1];

            if (u == v) {
                ans[qi] = 0;
                continue;
            }

            int ru = rank[u], rv = rank[v];

            if (comp[ru] != comp[rv]) {
                ans[qi] = -1;
                continue;
            }

            int p = Math.min(ru, rv);
            int q = Math.max(ru, rv);

            int cur = p;
            int hops = 0;
            for (int k = LOG; k >= 0; k--) {
                if (up[k][cur] < q) {
                    hops += (1 << k);
                    cur = up[k][cur];
                }
            }
            if (cur < q) {
                hops += 1;
            }

            ans[qi] = hops;
        }

        return ans;
    }
}