import java.util.Arrays;

/**
 * Created by s on 12.12.16.
 */

public class Matrix {

    private static final double EPSILON = 1e-10;
    public static final int MAX_ITERATIONS = 100;

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

    // create and return a random M-by-N matrix with values between 0 and 10
    public static Matrix random(int M, int N) {
        Matrix A = new Matrix(M, N);
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                A.data[i][j] = (int) (10 * Math.random());
            }
        }
        return A;
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

    // Everything Gauss-Seidel

    int count = 0;
    public boolean transformToDominant(double[][] M, int r, boolean[] V, int[] R)
    {
        int n = M.length;
        if (r == n) {
            double[][] T = new double[n][n];
            for (int i = 0; i < R.length; i++) {
                for (int j = 0; j < n; j++) {
                    T[i][j] = M[R[i]][j];
                }
            }

            M = T;
            this.data = M;

            return true;
        }

        for (int i = 0; i < n; i++) {
            if (V[i]) continue;

            double sum = 0;

            for (int j = 0; j < n; j++)
                sum += Math.abs(M[i][j]);

            if (2 * Math.abs(M[i][r]) > sum) { // diagonally dominant?
                V[i] = true;
                R[r] = i;

                if (transformToDominant(M, r + 1, V, R))
                    return true;

                V[i] = false;
            }
        }

        return false;
    }


    /**
     * Returns true if is possible to transform M to a diagonally
     * dominant matrix, false otherwise.
     */
    public boolean makeDominant()
    {
        double[][] M = this.data;
        int n = this.n;
        boolean[] visited = new boolean[n];
        int[] rows = new int[n];

        Arrays.fill(visited, false);

        return transformToDominant(M, 0, visited, rows);
    }


    public void gaussSeidel(Matrix vec_b)
    {
        double[][] b = vec_b.data;
        double[][] A = this.data;
        double[][] Ab = Arrays.copyOf(A, A.length + 1); // New array with row size of old array + 1

        //Ab[A.length] = new String[A.length]; // Initializing the new row

// Copying data from d array to the newsr array
        for (int i = 0; i < A.length; i++) {
            Ab[A.length][i] = b[i][0];
        }

        Matrix temp1 = new Matrix(Ab);
        System.out.println("---Ab---");
        temp1.show();



        int iterations = 0;
        int n = this.n;
        double[][] M = this.data;
        Matrix temp = new Matrix(M);
        System.out.println("---GS---");
        temp.show();
        double epsilon = 1e-15;
        double[] X = new double[n]; // Approximations
        double[] P = new double[n]; // Prev
        Arrays.fill(X, 0);

        while (true) {
            for (int i = 0; i < n; i++) {
                double sum = M[i][n]; // b_n

                for (int j = 0; j < n; j++)
                    if (j != i)
                        sum -= M[i][j] * X[j];

                // Update x_i to use in the next row calculation
                X[i] = 1/M[i][i] * sum;
            }

            System.out.print("X_" + iterations + " = {");
            for (int i = 0; i < n; i++)
                System.out.print(X[i] + " ");
            System.out.println("}");

            iterations++;
            if (iterations == 1) continue;

            boolean stop = true;
            for (int i = 0; i < n && stop; i++)
                if (Math.abs(X[i] - P[i]) > epsilon)
                    stop = false;

            if (stop || iterations == MAX_ITERATIONS) break;
            P = (double[])X.clone();
        }
    }

}
