public class Main {

    public static void main(String[] args){

        Matrix randMat = Matrix.random(5, 5);
        randMat.show();
        System.out.println("Diagonalisiert");
        randMat.diagonalize().show();

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
		/*
        double[][] choleskyData = {
                { 1, 5,  3 },
                { 1, 3, 15 }
              };
              */
        Matrix A = new Matrix(choleskyData);
        System.out.println();

        System.out.println("New Mat A");
        A.show();
        System.out.println();
        Matrix L = Matrix.cholesky(A);
        L.show();
        System.out.println();
        Matrix.transpose(L).show();
    }

}
