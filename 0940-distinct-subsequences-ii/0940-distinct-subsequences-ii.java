class Solution {
    public int distinctSubseqII(String s) {
        long MOD = 1000000007;
        long[] end = new long[26];
        long total = 0;

        for (char c : s.toCharArray()) {
            int x = c - 'a';

            long oldEnd = end[x];

            long newEnd = (total + 1) % MOD;

            total = (2 * total + 1 - oldEnd + MOD) % MOD;

            end[x] = newEnd;
        }

        return (int) total;
    }
}