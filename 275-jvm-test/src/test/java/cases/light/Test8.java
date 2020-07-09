package cases.light;

import cases.TestUtil;

class adcd{
    int x;
    public adcd(int x){
        this.x=x;
    }
}
public class Test8 {
    static boolean func1(){
        char[] a2={'a','b','c','d'};
        for (int i=0;i<a2.length;i++)
            TestUtil.reach((int)a2[i]);
        adcd[][] a3=new adcd[3][3];
        a3[2][1]=new adcd(5);
        return a3[2][1].x>3;
    }

    public static void main(String[] args) {
        if (func1()){
            int[] a={0,1,2,3};
            if (a.length<3) TestUtil.fail();
            else {
                TestUtil.reach(2222);
            }
        }
    }
}
