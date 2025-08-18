class Solution {
    public String removeOuterParentheses(String s) {
        StringBuilder res = new StringBuilder();
        int open  =0;
        for(char c:s.toCharArray())
        {
            if(c == '(' && open++>0)
            {
                res.append(c);
            }
            if(c == ')' && open-->1)
            {
                res.append(c);
            }
        }
        return res.toString();
    }
}
