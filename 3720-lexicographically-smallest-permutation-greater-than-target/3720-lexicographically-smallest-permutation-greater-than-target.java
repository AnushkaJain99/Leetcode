class Solution {
    public String lexGreaterPermutation(String s, String target) {
        int n = s.length();
        int[] cnt = new int[26];
        for (char c : s.toCharArray()) cnt[c - 'a']++;
        
        int fallbackPos = -1;
        char fallbackChar = 0;
        int[] fallbackCnt = null;
        
        for (int i = 0; i < n; i++) {
            char t = target.charAt(i);
            int gc = -1;
            for (int c = t - 'a' + 1; c < 26; c++) {
                if (cnt[c] > 0) { gc = c; break; }
            }
            if (gc != -1) {
                fallbackPos = i;
                fallbackChar = (char) ('a' + gc);
                fallbackCnt = cnt.clone();
            }
            
            if (cnt[t - 'a'] > 0) {
                cnt[t - 'a']--;
            } else {
                break;
            }
        }
        
        if (fallbackPos == -1) {
            return "";
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append(target, 0, fallbackPos);
        sb.append(fallbackChar);
        fallbackCnt[fallbackChar - 'a']--;
        
        for (int c = 0; c < 26; c++) {
            for (int j = 0; j < fallbackCnt[c]; j++) {
                sb.append((char) ('a' + c));
            }
        }
        
        return sb.toString();
    }
}