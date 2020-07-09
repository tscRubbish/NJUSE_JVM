package cases.light;

import cases.TestUtil;

public class Test7 {
    static int[][][] a;
    int x;
    static {
        a=new int[2][2][2];
    }
    int func(Test7[][][] t,int i,int j,int k){
        return t[i][j][k].x+i+j+k;
    }
    public static void main(String[] args) {
        Test7[][][] a2=new Test7[2][2][2];
        for (int i=0;i<2;i++){
            for (int j=0;j<2;j++){
                for (int k=0;k<2;k++){
                    a2[i][j][k]=new Test7();
                    a2[i][j][k].x=i+j+k;
                    a[i][j][k]=a2[i][j][k].x;
                    if (i==0&&j==1&&k==1)
                    TestUtil.reach(a2[i][j][k].func(a2,i,j,k));
                }
            }
        }
    }
}
