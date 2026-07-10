class Solution {
    public int myAtoi(String input) {
        int n = input.length();
        int i = 0;

        while (i < n && input.charAt(i) == ' ') {
            i++;
        }

        if (i == n) return 0;

        int sign = 1;
        if (input.charAt(i) == '+') {
            i++;
        } else if (input.charAt(i) == '-') {
            sign = -1;
            i++;
        }

        long result = 0;

        while (i < n && Character.isDigit(input.charAt(i))) {
            result = result * 10 + (input.charAt(i) - '0');

            if (sign * result > Integer.MAX_VALUE) {
                return Integer.MAX_VALUE;
            }

            if (sign * result < Integer.MIN_VALUE) {
                return Integer.MIN_VALUE;
            }

            i++;
        }

        return (int) (sign * result);
    }
}