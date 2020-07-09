package cases.light;

import cases.TestUtil;

public class Test10 {
    static int[][] multiply(int[][] a, int[][] b,int n){

        int[][] result = new int[80][80];
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                for(int k=0;k<n;k++){
                    result[i][j] += (a[i][k]*b[k][j]);
                }
            }
        }
        return result;
    }

    static int[][] quick(int k,int n,int[][] matrix){

        int[][] cur = new int[80][80];
        int[][] result = new int[80][80];

        for(int i=0;i<n;i++){
            result[i][i]=1;
        }
        cur = matrix;

        while(k!=0){
            if(k%2 ==1) result = multiply(result, cur, n);
            cur = multiply(cur, cur, n);
            k/=2;
        }
        return result;
    }
    static public void main(String args[]){
        int[][] a={{1,1},{1,1}};
        TestUtil.reach(quick(2,2,a)[0][0]);
    }
}
