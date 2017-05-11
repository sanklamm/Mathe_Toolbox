/**
 * Created by s on 12.12.16.
 */

public class Matrix {

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

    public static Matrix addMatrix(Matrix A, Matrix B){
        int n = A.n;
        int m = A.m;
        Matrix C = new Matrix(n, m);
        if (A.n != B.n || A.m != B.m) throw new RuntimeException("Matrices don't have same dimensions!");
        else{
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    C.data[i][j] =  A.data[i][j] + B.data[i][j];
                }
            }
        }
        return C;
    }

    public static Matrix subtractMatrix(Matrix A, Matrix B){
        int n = A.n;
        int m = A.m;
        Matrix C = new Matrix(n, m);
        if (A.n != B.n || A.m != B.m) throw new RuntimeException("Matrices don't have same dimensions!");
        else{
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    C.data[i][j] =  A.data[i][j] - B.data[i][j];
                }
            }
        }
        return C;
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
        int swap_exp = 0;
        for (int i = 0; i < n; i++) {

            // find pivot row and swap
            int max = i;
            for (int j = i + 1; j < n; j++)
                if (Math.abs(A.data[j][i]) > Math.abs(A.data[max][i]))
                    max = j;

            swap_exp += Math.abs(max - i);
            A.swap(i, max);

            // pivot A
            // TODO: Hier irgendwo ein Fehler!
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

    public static double determinant(double A[][],int N)
    {
        double det=0;
        if(N == 1)
        {
            det = A[0][0];
        }
        else if (N == 2)
        {
            det = A[0][0]*A[1][1] - A[1][0]*A[0][1];
        }
        else
        {
            det=0;
            for(int j1=0;j1<N;j1++)
            {
                double[][] m = new double[N-1][];
                for(int k=0;k<(N-1);k++)
                {
                    m[k] = new double[N-1];
                }
                for(int i=1;i<N;i++)
                {
                    int j2=0;
                    for(int j=0;j<N;j++)
                    {
                        if(j == j1)
                            continue;
                        m[i-1][j2] = A[i][j];
                        j2++;
                    }
                }
                det += Math.pow(-1.0,1.0+j1+1.0)* A[0][j1] * determinant(m,N-1);
            }
        }
        return det;
    }

    public static double evalDet(Matrix A){
        double det;

        if (A.n != A.m){
            throw new RuntimeException("Matrix is not square");
        }
        else{
            det = determinant(A.data, A.n);
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
            if (A.data[i][i] == 0.0) throw new RuntimeException("Matrix singulaer.");

            // pivot b
            for (int j = i + 1; j < n; j++)
                b.data[j][0] -= b.data[i][0] * A.data[j][i] / A.data[i][i];

            // pivot A
            for (int j = i + 1; j < n; j++) {
                double m = A.data[j][i] / A.data[i][i];
                for (int k = i+1; k < n; k++) {
                    A.data[j][k] -= A.data[i][k] * m;
                }
                A.data[j][i] = 0.0;
            }
        }
        // back substitution
        Matrix x = new Matrix(n, 1);
        for (int j = n - 1; j >= 0; j--) {
            double t = 0.0;
            for (int k = j + 1; k < n; k++)
                t += A.data[j][k] * x.data[k][0];
            x.data[j][0] = (b.data[j][0] - t) / A.data[j][j];
        }
        A.show();

        return x;

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
            for (int j = 0; j <N; j++) {
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

    private static Matrix identity(int n){
        Matrix E = new Matrix(n, n);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j){
                    E.data[i][j] = 1;
                }
                else {
                    E.data[i][j] = 0;
                }
            }
        }
        return E;
    }

    public static Matrix remainderMatrix(Matrix A, int K, int L){
        int n = A.n;
        Matrix R = new Matrix(n-1, n-1);
        int p = 0;
        for (int i = 0; i < n; ++i) {
            if (i == K){
                continue;
            }
            int q = 0;
            for (int j = 0; j < n; ++j) {
                if (j == L){
                    continue;
                }
                R.data[p][q] = A.data[i][j];
                ++q;
            }
            ++p;
        }
        return R;
    }

    public static Matrix scaleMatrix(Matrix A, double a){
        int n = A.n;
        int m = A.m;
        Matrix B = new Matrix(n, m);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                B.data[i][j] = A.data[i][j] * a;
            }
        }
        return B;
    }

    public static Matrix calculateInverse(Matrix A){
        int n = A.n;
        if (!isSquare(A)) {
            throw new RuntimeException("Matrix is not square");
        }
        if (evalDet(A) == 0){
            throw new RuntimeException("Matrix not regular");
        }
        Matrix E = identity(A.n);
        Matrix temp = new Matrix(n, n);
        Matrix I = new Matrix(n, n);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                temp.data[i][j] = evalDet(remainderMatrix(A, i, j)) * Math.pow(-1, i+j);
            }
        }
        I = scaleMatrix(transpose(temp), 1/(evalDet(A)));
        return I;
    }
    public static Matrix calculateLeontiefInverse(Matrix A){
        int n = A.n;
        if (!isSquare(A)) {
            throw new RuntimeException("Matrix is not square");
        }
        if (evalDet(A) == 0){
            throw new RuntimeException("Matrix not regular");
        }
        Matrix E = identity(A.n);
        Matrix temp = new Matrix(n, n);
        Matrix I = new Matrix(n, n);
        temp = subtractMatrix(E, A);
        I = calculateInverse(temp);
        return I;
    }

}
