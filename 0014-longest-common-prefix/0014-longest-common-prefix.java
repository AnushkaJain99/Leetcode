import java.util.*;
class Solution {
    public String longestCommonPrefix(String[] strs) {
        Arrays.sort(strs);
        String first=strs[0];
        String last=strs[strs.length-1];
        int i=0;
        for(i=0;i<first.length() && i<last.length();i++){
            if(first.charAt(i)!=last.charAt(i)){
                 break;
            }
        }
        return last.substring(0,i);
    }
}