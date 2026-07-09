class Solution {
    public int maxProfit(int[] prices) {
        int buy=prices[0];
        int Max=0;

        for(int i=1;i<prices.length;i++){
            if(prices[i]<buy){
                buy=prices[i];
             }
             else{
                Max=Math.max(Max,prices[i]-buy);
                
             }
        }
        return Max;
    }
}
