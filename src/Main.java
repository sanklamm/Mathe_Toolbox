public class Main {

    public static void main(String[] args){

        double[][] matData = {{7, 2, 3},{2, 4, 9},{3, 20, 2}};
        Matrix mat = new Matrix(matData);
        double[][] vecData = {{1}, {3}, {5}};
        Matrix vec = new Matrix(vecData);
        Matrix randMat = Matrix.random(5, 5);
        mat.show();
        // System.out.println("Diagonalisiert");
        // randMat.diagonalize().show();

        System.out.println();
        System.out.println("Diagonaldominant?");

        if (!mat.makeDominant()) {
            System.out.println("The system isn't diagonally dominant: " +
                    "The method cannot guarantee convergence.");
        }
        mat.show();
        mat.gaussSeidel(vec);

        /*
        double[][] matData = {{1, 2, 3},{2, 3, 4},{3, 4, 7}};
        double[][] vecData = {{1}, {3}, {5}};
        Matrix mat = new Matrix(matData);
        Matrix vec = new Matrix(vecData);

        System.out.println("Mat A");
        mat.show();
        System.out.println();
        System.out.println("Vec b");
        vec.show();
        System.out.println();

        System.out.println("Solved;");
        mat.solve(vec).show();
        System.out.println();
        System.out.println("Det A");
        double det = Matrix.evalDet(mat);
        System.out.println(det);
        mat.diagonalize().show();


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
        System.out.println();
        Matrix.transpose(L).show();
        */
    }

}
