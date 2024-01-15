class Solution {
   public static String reverseWords(String s) {
       if(s == null || s.length() == 0)
       {
           return "";
       }
       String[] words = s.trim().split("\\s+");
       String revStr ="";
       for(int i=words.length-1;i>=0;i--)
       {
           revStr = revStr+words[i]+" ";
   }
   return revStr.trim();
   }
    

}
    
    