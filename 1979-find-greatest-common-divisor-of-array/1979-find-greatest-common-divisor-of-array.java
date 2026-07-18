import java.lang.Math;
class Solution {
    public int findGCD(int[] nums) {
      int min=nums[0];
      int max=nums[0];

       for(int i=0;i<nums.length;i++){
        min=Math.min(min,nums[i]);
        max=Math.max(max,nums[i]);
       }

        while (max!=0){
            int temp = max;
            max = min % max;
            min= temp;
        }
      return min;
    }
}