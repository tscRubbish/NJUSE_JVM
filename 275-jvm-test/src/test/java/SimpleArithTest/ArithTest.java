public class ArithTest {
    private int a = 3;
    private int b = 4;

    public void testAdd(){
        int res = this.a + this.b;
        TestUtil.equalInt(3, res);
    }

    public void testSub(){
        int res = this.a - this.b; //-1
        TestUtil.equalInt(3, res);
    }

    public void testMul(){
        int res = this.a * this.b; //12
        TestUtil.equalInt(3, res);
    }

    public void testDiv(){
        int res = this.b / 2;      //2
        TestUtil.equalInt(3, res);
    }

    public void testAnd(){
        int res = this.a & 2; // 2
        TestUtil.equalInt(3, res);
    }

    public void testOr(){
        int res = this.a | 2; // 3
        TestUtil.equalInt(4, res);
    }

    public void testXor(){
        int res = this.a ^ 2; // 1
        TestUtil.equalInt(4, res);
    }

    public void testInc(){
        this.a++;
        TestUtil.equalInt(2, this.a);
    }

    public int testInvokeAdd(int a, int b){
        return a+b;
    }

    public static void main(String[] args){
        ArithTest arithTest = new ArithTest();
        int res = arithTest.testInvokeAdd(1, 2);
        TestUtil.reach(res);
    }
}

class TestUtil {
    public static void reach(int x){

    }

    public static boolean equalInt(int a, int b) {

        return true;
    }

    public static boolean equalFloat(float a, float b){

        return true;}
}