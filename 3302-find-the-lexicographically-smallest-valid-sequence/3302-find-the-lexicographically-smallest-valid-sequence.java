class Solution {
    public int[] validSequence(String word1, String word2) {
        int n = word1.length(), m = word2.length();
        int[] matched = new int[n + 1];
        int j = m - 1;
        for (int i = n - 1; i >= 0; i--) {
            if (j >= 0 && word1.charAt(i) == word2.charAt(j)) {
                j--;
            }
            matched[i] = m - 1 - j;
        }

        int[] result = new int[m];
        int i = 0, j2 = 0;
        boolean usedChange = false;

        while (j2 < m) {
            if (i >= n) {
                return new int[0];
            }
            if (word1.charAt(i) == word2.charAt(j2)) {
                result[j2] = i;
                i++;
                j2++;
            } else if (!usedChange && matched[i + 1] >= m - j2 - 1) {
                result[j2] = i;
                usedChange = true;
                i++;
                j2++;
            } else {
                i++;
            }
        }

        return result;
    }
}