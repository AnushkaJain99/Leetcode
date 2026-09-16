class Solution {
    public int numberOfSets(int n, int k) {
        long MOD = 1000000007L;
        long[] dp = new long[2 * k + 1];
        
        dp[0] = 1;
        
        for (int i = 1; i <= n + k - 1; i++) {
            for (int j = Math.min(2 * k, i); j >= 1; j--) {
                dp[j] = (dp[j] + dp[j - 1]) % MOD;
            }
        }
        
        return (int) dp[2 * k];
    }
}