public class Main {

    public static void main(String[] args){

        double[][] matData = {{7, 2, 3},{2, 4, 9},{3, 20, 2}};
        Matrix mat = new Matrix(matData);

        double[][] matData_leon = {{1/10., 1/10., 1/5.},{3/10., 1/5., 1/10.},{2/5., 1/10., 3/10.}};
        double[][] matData_leon_2 = {{3/12., 4/16., 1/8.},{3/12., 4/16., 2/8.},{3/12., 0/16., 4/8.}};
        //double[][] matData_leon = {{9/10., -1/10., -1/5.},{-3/10., 4/5., -1/10.},{-2/5., -1/10., 7/10.}};
        //double[][] matData_leon = {{1., 0., 2.},{2., 1., 3.},{0., 3., 1.}};
        Matrix mat_leon = new Matrix(matData_leon);
        Matrix mat_leon_2 = new Matrix(matData_leon_2);
        Matrix mat_leon_3 = new Matrix(matData_leon_2);
        Matrix rand01 = Matrix.random(4, 4);

        // Test Streichmatrix
//        System.out.println("Leontief");
//        mat_leon.show();
        //System.out.println("Diagonalisiert");
        //mat_leon.diagonalize().show();
        //System.out.println("Streichmatrix");
        //Matrix.remainderMatrix(mat_leon, 0, 0).show();
        //System.out.println("Transponierte");
        //Matrix.transpose(mat_leon).show();
        //System.out.println("Inverse");
        //Matrix.calculateInverse(mat_leon).show();
        //System.out.println("Leontief-Inverse");
        //Matrix.calculateLeontiefInverse(mat_leon).show();
        //System.out.println("Determinante");
        //System.out.println(Matrix.evalDet(mat_leon));

        mat_leon_3.show();
        System.out.println("Diagonalmatrix");
        mat_leon_3.diagonalize().show();
        System.out.println("Inverse");
        Matrix.calculateInverse(mat_leon_2).show();
        System.out.println("Leontief-Inverse");
        Matrix.calculateLeontiefInverse(mat_leon_2).show();
        System.out.println("Determinante");
        System.out.println(Matrix.evalDet(mat_leon_2));

        System.out.println("Random Matrix");
        rand01.show();
        System.out.println("Random - Inverse");
        Matrix.calculateInverse(rand01).show();
        System.out.println("Random - Leontief-Inverse");
        Matrix.calculateLeontiefInverse(rand01).show();

/**
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
 **/
    }

}
