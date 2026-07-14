class Solution {

    private static final int MOD = 1_000_000_007;
    private Long[][][] memo;
    private int[] nums;
    private int n;

    public int subsequencePairCount(int[] nums) {
        this.nums = nums;
        this.n = nums.length;

        
        memo = new Long[n][201][201];

        return (int) dfs(0, 0, 0);
    }

    private long dfs(int idx, int g1, int g2) {
        if (idx == n) {
            return (g1 != 0 && g1 == g2) ? 1 : 0;
        }

        if (memo[idx][g1][g2] != null) {
            return memo[idx][g1][g2];
        }

        long ans = 0;

        
        ans = dfs(idx + 1, g1, g2);

        
        int ng1 = (g1 == 0) ? nums[idx] : gcd(g1, nums[idx]);
        ans = (ans + dfs(idx + 1, ng1, g2)) % MOD;

       
        int ng2 = (g2 == 0) ? nums[idx] : gcd(g2, nums[idx]);
        ans = (ans + dfs(idx + 1, g1, ng2)) % MOD;

        memo[idx][g1][g2] = ans;
        return ans;
    }

    private int gcd(int a, int b) {
        while (b != 0) {
            int temp = a % b;
            a = b;
            b = temp;
        }
        return a;
    }
}