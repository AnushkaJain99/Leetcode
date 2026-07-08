class Solution {
    static final int MOD = 1000000007;

    public int[] sumAndMultiply(String s, int[][] queries) {

        int n = s.length();

        ArrayList<Integer> pos = new ArrayList<>();
        ArrayList<Integer> digit = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            int d = s.charAt(i) - '0';
            if (d != 0) {
                pos.add(i);
                digit.add(d);
            }
        }

        int m = digit.size();

        long[] pow10 = new long[m + 1];
        long[] invPow10 = new long[m + 1];

        pow10[0] = 1;

        for (int i = 1; i <= m; i++)
            pow10[i] = pow10[i - 1] * 10 % MOD;

        invPow10[m] = modPow(pow10[m], MOD - 2);

        for (int i = m - 1; i >= 0; i--)
            invPow10[i] = invPow10[i + 1] * 10 % MOD;

        long[] prefixNum = new long[m + 1];
        long[] prefixSum = new long[m + 1];

        for (int i = 0; i < m; i++) {
            prefixNum[i + 1] = (prefixNum[i] * 10 + digit.get(i)) % MOD;
            prefixSum[i + 1] = prefixSum[i] + digit.get(i);
        }

        int[] ans = new int[queries.length];

        for (int i = 0; i < queries.length; i++) {

            int l = queries[i][0];
            int r = queries[i][1];

            int left = lowerBound(pos, l);
            int right = upperBound(pos, r) - 1;

            if (left > right) {
                ans[i] = 0;
                continue;
            }

            int len = right - left + 1;

            long number = (prefixNum[right + 1] -
                    prefixNum[left] * pow10[len] % MOD + MOD) % MOD;

            long sum = prefixSum[right + 1] - prefixSum[left];

            ans[i] = (int) (number * sum % MOD);
        }

        return ans;
    }

    private static int lowerBound(ArrayList<Integer> list, int x) {
        int l = 0, r = list.size();

        while (l < r) {
            int mid = (l + r) / 2;

            if (list.get(mid) >= x)
                r = mid;
            else
                l = mid + 1;
        }

        return l;
    }

    private static int upperBound(ArrayList<Integer> list, int x) {
        int l = 0, r = list.size();

        while (l < r) {
            int mid = (l + r) / 2;

            if (list.get(mid) > x)
                r = mid;
            else
                l = mid + 1;
        }

        return l;
    }

    private static long modPow(long a, long b) {

        long ans = 1;

        while (b > 0) {

            if ((b & 1) == 1)
                ans = ans * a % MOD;

            a = a * a % MOD;

            b >>= 1;
        }

        return ans;
    }
}