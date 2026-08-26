class Solution {
    public String shortestBeautifulSubstring(String s, int k) {
        int n = s.length();
        int bestLen = Integer.MAX_VALUE;
        int bestStart = -1;
        
        int left = 0, ones = 0;
        
        for (int right = 0; right < n; right++) {
            if (s.charAt(right) == '1') {
                ones++;
            }
            
            while (left <= right && (ones > k || (ones == k && s.charAt(left) == '0'))) {
                if (s.charAt(left) == '1') {
                    ones--;
                }
                left++;
            }
            
            if (ones == k) {
                int len = right - left + 1;
                if (len < bestLen) {
                    bestLen = len;
                    bestStart = left;
                } else if (len == bestLen) {
                    String candidate = s.substring(left, right + 1);
                    String current = s.substring(bestStart, bestStart + bestLen);
                    if (candidate.compareTo(current) < 0) {
                        bestStart = left;
                    }
                }
            }
        }
        
        return bestStart == -1 ? "" : s.substring(bestStart, bestStart + bestLen);
    }
}