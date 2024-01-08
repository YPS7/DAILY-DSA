class Solution {
    public static int[] getSecLar(int n, int []a)
    {
        n = a.length;
        int min = Integer.MAX_VALUE;
        int secmin=Integer.MAX_VALUE;
        int max=Integer.MIN_VALUE;
        int secmax=Integer.MIN_VALUE;
        for (int i=0;i<n;i++)
        {
            if(a[i]>max)
            {
                secmax = max;
                max = a[i];
            }
            else if(a[i]>secmax && a[i]!=max)
            {
                secmax = a[i];
            }
            if(a[i]<min)
            {
                secmin = min;
                min = a[i];
            }
            else if(a[i]<secmin && a[i]!=max)
            {
                secmin = a[i];
            }
        }
        return new int[]{min,secmin,max,secmax};
    }
}