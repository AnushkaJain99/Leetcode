class Solution {
    static final long CAP = 2_000_000L;

    public String smallestPalindrome(String s, int k) {
        int n = s.length();
        int[] freq = new int[26];
        for (char ch : s.toCharArray()) freq[ch - 'a']++;

        int mid = -1;
        for (int i = 0; i < 26; i++) {
            if (freq[i] % 2 != 0) {
                mid = i;
                freq[i]--;
            }
        }

        int half = n / 2;
        int[] halfFreq = new int[26];
        for (int i = 0; i < 26; i++) halfFreq[i] = freq[i] / 2;

        long total = multinomialCapped(half, halfFreq);
        if (total < k) return "";

        StringBuilder sb = new StringBuilder();
        int remaining = half;
        long kk = k;

        for (int pos = 0; pos < half; pos++) {
            for (int c = 0; c < 26; c++) {
                if (halfFreq[c] == 0) continue;
                halfFreq[c]--;
                long cnt = multinomialCapped(remaining - 1, halfFreq);
                if (cnt >= kk) {
                    sb.append((char) ('a' + c));
                    remaining--;
                    break;
                } else {
                    kk -= cnt;
                    halfFreq[c]++;
                }
            }
        }

        StringBuilder result = new StringBuilder();
        result.append(sb);
        if (mid != -1) result.append((char) ('a' + mid));
        result.append(sb.reverse());
        return result.toString();
    }
    
    private long multinomialCapped(int total, int[] freq) {
        long res = 1L;
        int remaining = total;
        for (int c = 0; c < 26; c++) {
            int cnt = freq[c];
            if (cnt == 0) continue;
            for (int i = 1; i <= cnt; i++) {
                res = res * (remaining - cnt + i) / i;
                if (res > CAP) return CAP + 1;
            }
            remaining -= cnt;
        }
        return res;
    }
}