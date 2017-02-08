/**
 * Created by s on 12.12.16.
 */

public class Matrix {

    private static final double EPSILON = 1e-10;

    private int m;
    private int n;
    private double[][] data;
    private double[] vector;

    public Matrix(int m, int n){
        this.data = new double[m][n];
        this.m = m;
        this.n = n;
    }
    public Matrix(int m){
        this.vector = new double[m];
        this.m = m;
    }

    public Matrix(double[][] data) {
        m = data.length;
        n = data[0].length;
        this.data = new double[m][n];
        for (int i = 0; i < m; i++){
            for (int j = 0; j < n; j++){
                this.data[i][j] = data[i][j];
            }
        }
    }

    public Matrix(Matrix mat) {
        this.m = mat.m;
        this.n = mat.n;
        this.data = new double[m][m];
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                this.data[i][j] = mat.data[i][j];
    }

    public Matrix(double[] data) {
        m = data.length;
        //n = 1;
        this.vector = new double[m];
        for (int i = 0; i < m; i++)
            this.vector[i] = data[i];
    }

    public int[] getDim(Matrix mat){
        int[] dim = new int[2];
        dim[0] = this.m;
        dim[1] = this.n;
        return dim;
    }

    public void swap(int i, int j) {
        double[] temp = data[i];
        data[i] = data[j];
        data[j] = temp;
    }

/*    private void addRows(int i, int j, double fac){
    	//double[] temp = data[i];
    	for (int k = 0; k < m; k++){
        	data[j][k] = data[j][k] + fac * data[i][k];
    	}
    }*/

    public Matrix diagonalize(){
        Matrix A = new Matrix(this);
        for (int i = 0; i < n; i++) {

            // find pivot row and swap
            int max = i;
            for (int j = i + 1; j < n; j++)
                if (Math.abs(A.data[j][i]) > Math.abs(A.data[max][i]))
                    max = j;
            A.swap(i, max);

            // pivot A
            for (int j = i + 1; j < n; j++) {
                double m = A.data[j][i] / A.data[i][i];
                for (int k = i+1; k < n; k++) {
                    A.data[j][k] = A.data[j][k] - A.data[i][k] * m;
                }
                A.data[j][i] = 0.0;
            }
        }
        return A;
    }

    public static double evalDet(Matrix A){
        double det = 1;

        if (A.n != A.m){
            throw new RuntimeException("Matrix is not square");
        }
        else{
            for (int i = 0; i < A.n; i++){
                det = det * A.diagonalize().data[i][i];
            }
        }
        return det;
    }

    public Matrix solve(Matrix vec){
        //	double[][] res = new double[this.m][this.n];


        Matrix A = new Matrix(this);
        Matrix b = new Matrix(vec);
        for (int i = 0; i < n; i++) {

            // find pivot row and swap
            int max = i;
            for (int j = i + 1; j < n; j++)
                if (Math.abs(A.data[j][i]) > Math.abs(A.data[max][i]))
                    max = j;
            A.swap(i, max);
            b.swap(i, max);

            // singular
            if (A.data[i][i] == 0.0) throw new RuntimeException("Matrix singul‰r.");

            // pivot b
            for (int j = i + 1; j < n; j++)
                b.data[j][0] -= b.data[i][0] * A.data[j][i] / A.data[i][i];

            // pivot A
            for (int j = i + 1; j < n; j++) {
                double m = A.data[j][i] / A.data[i][i];
                for (int k = i+1; k < n; k++) {
                    A.data[j][k] = A.data[j][k] - A.data[i][k] * m;
                }
                A.data[j][i] = 0.0;
            }
        }
        b.show();

        return A;

    }


    public void show() {
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++)
                System.out.printf("%9.4f ", data[i][j]);
            System.out.println();
        }
    }

    public static Matrix transpose(Matrix A){
        if (!isSquare(A)) {
            throw new RuntimeException("Matrix is not square");
        }
        int N = A.n;
        double[][] transData = new double[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j <= i; j++) {
                transData[j][i] = A.data[i][j];
            }
        }

        Matrix result = new Matrix(transData);

        return result;
    }


    // is symmetric
    public static boolean isSymmetric(Matrix A) {

        int N = A.n;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < i; j++) {
                if (A.data[i][j] != A.data[j][i]) return false;
            }
        }
        return true;
    }

    // is square
    public static boolean isSquare(Matrix A) {
        if (A.n == A.m) {
            return true;
        }
        else{
            return false;
        }
    }


    // return Cholesky factor L of psd matrix A = L L^T
    public static Matrix cholesky(Matrix A) {
        if (!isSquare(A)) {
            throw new RuntimeException("Matrix is not square");
        }
        if (!isSymmetric(A)) {
            throw new RuntimeException("Matrix is not symmetric");
        }

        int N  = A.n;
        double[][] L = new double[N][N];

        for (int i = 0; i < N; i++)  {
            for (int j = 0; j <= i; j++) {
                double sum = 0.0;
                for (int k = 0; k < j; k++) {
                    sum += L[i][k] * L[j][k];
                }
                if (i == j) L[i][i] = Math.sqrt(A.data[i][i] - sum);
                else        L[i][j] = 1.0 / L[j][j] * (A.data[i][j] - sum);
            }
            if (L[i][i] <= 0) {
                throw new RuntimeException("Matrix not positive definite");
            }
        }
        Matrix result = new Matrix(L);

        return result;
    }


}
