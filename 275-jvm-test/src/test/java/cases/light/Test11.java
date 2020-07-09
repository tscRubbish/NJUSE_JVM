package cases.light;

import cases.TestUtil;

public class Test11 implements inter{
    private int x=250;
    int func(int p){
        return x-p+i;
    }
    public static void main(String[] args) {
        Test11 test11=new Test11();
        int v3=test11.func(7);
        TestUtil.reach(v3);
    }
}
