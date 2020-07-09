package cases.light;

import cases.TestUtil;

public class Test6 {
    public static void main(String[] args) {
        int[][][] a=new int[3][3][3];
        for (int i=0;i<3;i++){
            for (int j=0;j<3;j++) {
                for (int k=0;k<3;k++){
                    a[i][j][k]=i+j+k;
                }
            }
        }
        if (a instanceof java.lang.Cloneable){
            TestUtil.reach(a[0][1][2]);
        }
    }
}
