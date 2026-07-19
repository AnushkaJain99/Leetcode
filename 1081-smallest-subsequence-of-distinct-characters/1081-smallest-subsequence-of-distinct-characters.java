class Solution {
    public String smallestSubsequence(String s) {
        int[] freq = new int[26];
        boolean[] present = new boolean[26];

        for (char ch : s.toCharArray()) {
            freq[ch - 'a']++;
        }

        ArrayDeque<Character> stack = new ArrayDeque<>();

        for (char ch : s.toCharArray()) {
            freq[ch - 'a']--;

            if (present[ch - 'a'])
                continue;

            while (!stack.isEmpty()
                    && stack.peekLast() > ch
                    && freq[stack.peekLast() - 'a'] > 0) {
                present[stack.pollLast() - 'a'] = false;
            }

            stack.addLast(ch);
            present[ch - 'a'] = true;
        }

        StringBuilder ans = new StringBuilder();
        while (!stack.isEmpty()) {
            ans.append(stack.pollFirst());
        }

        return ans.toString();
    }
}