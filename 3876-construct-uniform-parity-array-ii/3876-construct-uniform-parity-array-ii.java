class Solution {
    public boolean uniformArray(int[] nums1) {
        boolean hasOdd = false;
        boolean hasEven = false;
        int min = Integer.MAX_VALUE;

        for (int x : nums1) {
            min = Math.min(min, x);

            if ((x & 1) == 0)
                hasEven = true;
            else
                hasOdd = true;
        }

        if (!hasOdd || !hasEven)
            return true;

        return (min & 1) == 1;
    }
}