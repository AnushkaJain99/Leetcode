class Solution {
    public boolean sumGame(String num) {

        int n=num.length();
        int leftsum=0;
        int rightsum=0;
        int leftQ=0;
        int rightQ=0;

        for(int i=0;i<n/2;i++){
            if(num.charAt(i)=='?'){
                leftQ++;
            }else{
                leftsum+=num.charAt(i)-'0';
            }
        }

        for(int i=n/2;i<n;i++){
            if(num.charAt(i)=='?'){
                rightQ++;
            }else{
                rightsum+=num.charAt(i)-'0';
            } 
        }
        int diff=leftsum-rightsum;
        int qdiff=rightQ-leftQ;

        return 2*diff!= qdiff*9;
    }
}