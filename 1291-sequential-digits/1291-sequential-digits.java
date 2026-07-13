class Solution {
    public List<Integer> sequentialDigits(int low, int high) {

        List<Integer> ans=new ArrayList<>();

        String digits="123456789";

        int lowl=String.valueOf(low).length();
        int highl=String.valueOf(high).length();

        for(int len=lowl;len<=highl;len++){

        for(int i=0; i+len<=9;i++){
            int num=Integer.parseInt(digits.substring(i,i+len));

            if(num>=low && num<=high){
                ans.add(num);
            }
        }
    }
       return ans;  
    }
}