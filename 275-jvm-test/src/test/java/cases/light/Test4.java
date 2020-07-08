package cases.light;

import cases.TestUtil;
import org.junit.Test;

public class Test4 {
    static int count;
    static {
        count=0;
    }
    int x=0;
    Test4(){
        x=count++;
    }
    public static void main(String args[]){
        int[] a1={(int)1,(int)97.99};
        for (int i=0;i<a1.length;i++){
            if (a1[i]>1)
                TestUtil.reach(a1[i]);
        }
        Test4[][] a2=new Test4[4][4];
        for (int i=0;i<3;i++){
            for (int j=0;j<3;j++){
                a2[i][j]=new Test4();
                TestUtil.reach(a2[i][j].x);
            }
        }
    }
}
