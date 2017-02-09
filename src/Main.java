public class Main {

    public static void main(String[] args){

        double[][] matData = {{7, 2, 3},{2, 4, 9},{3, 20, 2}};
        Matrix mat = new Matrix(matData);
        double[][] vecData = {{1}, {3}, {5}};
        Matrix vec = new Matrix(vecData);
        Matrix randMat = Matrix.random(5, 5);
        Matrix randVec = Matrix.random(5, 1);
        System.out.println("Mat A");
        mat.show();
        System.out.println("Vec b");
        vec.show();

        System.out.println("Gauss (Pivotisierung)");
        mat.solve(vec).show();
        System.out.println("Gauss-Seidel method");
        GaussSeidel gausSeidel = new GaussSeidel(matData, vecData);

        gausSeidel.print();
        if (!gausSeidel.makeDiagDominant()) {
            System.out.println("The system isn't diagonally dominant: " +
                    "The method cannot guarantee convergence.");
        }

        System.out.println();
        gausSeidel.print();
        gausSeidel.solve();

        System.out.println();
        System.out.println("Cholesky");
        double[][] choleskyData = { { 4, 1,  1 },
                { 1, 5,  3 },
                { 1, 3, 15 }
        };

        Matrix A = new Matrix(choleskyData);
        System.out.println();

        System.out.println("New Mat A");
        A.show();
        System.out.println();
        Matrix L = Matrix.cholesky(A);
        L.show();
    }

}
