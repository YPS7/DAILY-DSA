class Solution {
    public int romanToInt(String s) {
        int prev =0;
        int res = 0;
        for(int i=s.length()-1;i>=0;i--)
        {
            int curr =0;
            char c = s.charAt(i);
            switch(c)
            {
                case 'I':
                curr = 1;
                break;
            case 'V':
                curr = 5;
                break;
            case 'X':
                curr = 10;
                break;
            case 'L':
                curr = 50;
                break;
            case 'C':
                curr = 100;
                break;
            case 'D':
                curr = 500;
                break;
            case 'M':
                curr = 1000;
                break;
            }
            if(curr<prev)
            {
                res = res - curr;
            }
            else
            {
                res = res + curr;
            }
            prev = curr;
        }
        return res;
        
    }
}
