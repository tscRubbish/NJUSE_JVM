package cases.light;

import cases.TestUtil;

public class Test14 {
    int[][] numbers=new int[0][0];
    char[] letters=new char[1];
    Test14[][] refs=new Test14[1][1];
    public static void main(String[] args) {
        Test14 test14=new Test14();
        test14.letters=new char[]{'H','E','L','L','O','M','U','T','I','A','R','R','A','Y'};
        test14.numbers=new int[test14.letters.length][test14.letters.length-1];
        TestUtil.equalInt('O',test14.letters[4]);
        TestUtil.reach(test14.letters[4]);
        test14.numbers[2][2]=1;
        TestUtil.equalInt(1,test14.numbers[2][2]);
        TestUtil.reach(test14.numbers[2][2]);
        TestUtil.equalInt(1,test14.refs.length);
        TestUtil.reach(test14.refs.length);
        test14.refs=new Test14[3][3];
        test14.refs[1][1]=new Test14();
        test14.refs[1][1].letters=new char[]{'G','O','O','D','B','Y','E'};
        TestUtil.reach(test14.refs[1][1].letters[5]);
    }
}
