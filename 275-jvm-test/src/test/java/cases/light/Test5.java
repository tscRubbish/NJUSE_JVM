package cases.light;

import cases.TestUtil;

public class Test5 {
    public static void main(String[] args) {
        int[] numbers=new int[]{1,5,8,2,3,9,4};
        int i,j;
        for(i=0;i<numbers.length-1;i++)
        {
            for(j=0;j<numbers.length-1-i;j++)
            {
                if(numbers[j]>numbers[j+1])
                {
                    int temp=numbers[j];
                    numbers[j]=numbers[j+1];
                    numbers[j+1]=temp;
                }
            }
        }
        for (i=0;i<numbers.length;i++)
            TestUtil.reach(numbers[i]);
    }

}
