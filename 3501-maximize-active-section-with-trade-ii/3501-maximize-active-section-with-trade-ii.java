import java.util.*;

class Solution {
    public List<Integer> maxActiveSectionsAfterTrade(String s, int[][] queries) {
        int n = s.length();

        // ---- Build run-length encoding ----
        int[] rs = new int[n], re = new int[n], rlen = new int[n];
        char[] rc = new char[n];
        int m = 0;
        int i = 0;
        while (i < n) {
            int j = i;
            while (j < n && s.charAt(j) == s.charAt(i)) j++;
            rs[m] = i; re[m] = j - 1; rlen[m] = j - i; rc[m] = s.charAt(i);
            m++;
            i = j;
        }

        int[] posToRun = new int[n];
        for (int k = 0; k < m; k++)
            for (int p = rs[k]; p <= re[k]; p++) posToRun[p] = k;

        long totalOnesL = 0;
        for (int k = 0; k < m; k++) if (rc[k] == '1') totalOnesL += rlen[k];
        int totalOnes = (int) totalOnesL;

        // ---- g(i) = rlen[i-1] + rlen[i+1] for interior '1' runs ----
        int NEG = -1;
        int[] G = new int[Math.max(m, 1)];
        Arrays.fill(G, NEG);
        for (int k = 1; k <= m - 2; k++) {
            if (rc[k] == '1') G[k] = rlen[k - 1] + rlen[k + 1];
        }

        // ---- Sparse table for range max on G ----
        int LOG = 1;
        while ((1 << LOG) < Math.max(m, 2)) LOG++;
        int[][] sparse = new int[LOG + 1][Math.max(m, 1)];
        if (m > 0) {
            sparse[0] = G.clone();
            for (int kx = 1; kx <= LOG; kx++) {
                int half = 1 << (kx - 1);
                for (int idx = 0; idx + (1 << kx) <= m; idx++) {
                    sparse[kx][idx] = Math.max(sparse[kx - 1][idx], sparse[kx - 1][idx + half]);
                }
            }
        }
        int[] log2 = new int[m + 2];
        for (int k = 2; k <= m + 1; k++) log2[k] = log2[k / 2] + 1;

        int q = queries.length;
        List<Integer> ans = new ArrayList<>(q);

        for (int qi = 0; qi < q; qi++) {
            int l = queries[qi][0], r = queries[qi][1];
            int runL = posToRun[l], runR = posToRun[r];
            int gain = 0;

            if (runR - runL >= 2) {
                int i1 = runL + 1, i2 = runR - 1;

                if (i1 == i2) {
                    if (rc[i1] == '1') {
                        int leftLen = re[runL] - l + 1;
                        int rightLen = r - rs[runR] + 1;
                        gain = Math.max(gain, leftLen + rightLen);
                    }
                } else {
                    if (rc[i1] == '1') {
                        int leftLen = re[runL] - l + 1;
                        int rightLen = rlen[i1 + 1];
                        gain = Math.max(gain, leftLen + rightLen);
                    }
                    if (rc[i2] == '1') {
                        int leftLen = rlen[i2 - 1];
                        int rightLen = r - rs[runR] + 1;
                        gain = Math.max(gain, leftLen + rightLen);
                    }
                    int lo = i1 + 1, hi = i2 - 1;
                    if (lo <= hi) {
                        int len = hi - lo + 1;
                        int kx = log2[len];
                        int mx = Math.max(sparse[kx][lo], sparse[kx][hi - (1 << kx) + 1]);
                        if (mx > gain) gain = mx;
                    }
                }
            }

            ans.add(totalOnes + gain);
        }

        return ans;
    }
}