class Solution {
    public String lexPalindromicPermutation(String s, String target) {
        int n = s.length();
        int[] cnt = new int[26];
        for (char c : s.toCharArray()) cnt[c - 'a']++;
        
        int oddCount = 0, oddLetter = -1;
        for (int c = 0; c < 26; c++) {
            if (cnt[c] % 2 == 1) { oddCount++; oddLetter = c; }
        }
        if (n % 2 == 0 ? oddCount != 0 : oddCount != 1) return "";
        
        int half = n / 2;
        int[] halfCnt = new int[26];
        for (int c = 0; c < 26; c++) halfCnt[c] = cnt[c] / 2;
        char mid = (n % 2 == 1) ? (char) ('a' + oddLetter) : 0;
        
        String prefixTarget = target.substring(0, half);
        int[] prefixCnt = new int[26];
        for (char c : prefixTarget.toCharArray()) prefixCnt[c - 'a']++;
        
        boolean exactMatch = true;
        for (int c = 0; c < 26; c++) {
            if (prefixCnt[c] != halfCnt[c]) { exactMatch = false; break; }
        }
        
        if (exactMatch) {
            StringBuilder revBase = new StringBuilder(prefixTarget).reverse();
            if (n % 2 == 1) {
                char tmid = target.charAt(half);
                if (mid > tmid) {
                    return prefixTarget + mid + revBase;
                } else if (mid == tmid) {
                    String candidate = prefixTarget + mid + revBase;
                    if (candidate.compareTo(target) > 0) return candidate;
                }
            } else {
                String candidate = prefixTarget + revBase;
                if (candidate.compareTo(target) > 0) return candidate;
            }
        }
        
        int[] cntCopy = halfCnt.clone();
        int fallbackPos = -1;
        char fallbackChar = 0;
        int[] fallbackCnt = null;
        
        for (int i = 0; i < half; i++) {
            char t = prefixTarget.charAt(i);
            int gc = -1;
            for (int c = t - 'a' + 1; c < 26; c++) {
                if (cntCopy[c] > 0) { gc = c; break; }
            }
            if (gc != -1) {
                fallbackPos = i;
                fallbackChar = (char) ('a' + gc);
                fallbackCnt = cntCopy.clone();
            }
            if (cntCopy[t - 'a'] > 0) cntCopy[t - 'a']--;
            else break;
        }
        
        if (fallbackPos == -1) return "";
        
        StringBuilder hb = new StringBuilder();
        hb.append(prefixTarget, 0, fallbackPos);
        hb.append(fallbackChar);
        fallbackCnt[fallbackChar - 'a']--;
        
        for (int c = 0; c < 26; c++) {
            for (int j = 0; j < fallbackCnt[c]; j++) hb.append((char) ('a' + c));
        }
        
        String H = hb.toString();
        StringBuilder result = new StringBuilder(H);
        if (n % 2 == 1) result.append(mid);
        result.append(new StringBuilder(H).reverse());
        
        return result.toString();
    }
}