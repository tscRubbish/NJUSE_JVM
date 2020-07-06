package cases.light;

import cases.TestUtil;

public class Test3 {
    int func1(double d){
        char[] a3=new char[(int)d];
        return a3.length;
    }
    public static void main(String args[]){
        int[] a1={(int)1,(int)97.99};
        for (int i=0;i<a1.length;i++){
            if (a1[i]>1)
                TestUtil.reach(a1[i]);
        }
        int[][] a2={{1,2,3},{4,5,6},{7,8,9}};
        for (int i=0;i<3;i++) {
            for (int j=0;j<3;j++){
                if (a2[i][j]==6) {
                    TestUtil.reach(i+j);
                }
            }
        }
        double d1=114.514;
        TestUtil.reach(new Test3().func1(d1));
    }
}
