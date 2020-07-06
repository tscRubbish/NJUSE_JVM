package cases.light;

import cases.TestUtil;

public class Test1 {
    private static int a = 7;
    static {
        TestUtil.reach(3);
    }
    double func1(double d1,double d2){
        if (d1<d2) return d1;
        return d2;
    }
    public static void main(String[] args) {
        double d1=1.0;
        double d2=d1;
        float f1=(float) d1;
        float f2=(float) d2;
        TestUtil.equalFloat(f1,f2);
        TestUtil.reach((int)new Test1().func1(d1,0.0));
    }
}
