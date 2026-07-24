class Solution {
    public int uniqueXorTriplets(int[] nums) {
        int MAX = 2048;

        boolean[] two = new boolean[MAX];

        for (int a : nums) {
            for (int b : nums) {
                two[a ^ b] = true;
            }
        }

        boolean[] ans = new boolean[MAX];

        for (int a : nums) {
            for (int x = 0; x < MAX; x++) {
                if (two[x]) {
                    ans[a ^ x] = true;
                }
            }
        }

        int count = 0;
        for (boolean b : ans) {
            if (b) count++;
        }

        return count;
    }
}