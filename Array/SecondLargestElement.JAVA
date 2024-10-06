import java.util.*;
class HelloWorld {
    public static void main(String[] args) {
        int[] a= {1,2,6,8,5};
        int n = a.length;
        Arrays.sort(a);
        int largest = a[n-1];
        int sl=0;
        for(int i=n-2;i>=0;i--)
        {
            if(a[i]!=largest)
            {
               sl=a[i];
               break;
            }
        }
        System.out.println("The 2nd larget number is "+ sl);
    }
}

// The idea is that we firt sortv the array and then pick the element from n-2 position that will autoatically be the second largest then we will just check if there is a dupli or not and if not then we got the answer.
