import cases.TestUtil;

public class ConditionTest {
    public static void test(int small, int big, long smallL, long bigL, float smallF, float bigF,
                            double smallD, double bigD) {
        if (small == 3) {
            if (small < big && smallL < bigL && smallF < bigF && smallD < bigD) {

            } else {
                TestUtil.equalInt(1,2);
            }
            big++;
            if (big > small && bigL > smallL && bigF > smallF && bigD > smallD) {

            } else {
                TestUtil.equalInt(2,3);
            }
        } else {
            TestUtil.equalInt(3,4);
        }


        if (small <= big) {
            if (big > small) {

            } else {
                TestUtil.equalInt(4,5);
            }
            if (big + 1 >= small) {
                if (big == small) {
                    TestUtil.equalInt(5,6);
                }
                if (big != small) {

                } else {
                    TestUtil.equalInt(6,7);
                }
            }
        }
    }

    public static void main(String[] args) {
        test(3, 4, 5, 6, 7f, 8f, 9, 10);
    }
}
