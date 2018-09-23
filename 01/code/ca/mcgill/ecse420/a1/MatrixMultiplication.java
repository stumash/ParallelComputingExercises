package ca.mcgill.ecse420.a1;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MatrixMultiplication {

    private static final int NUMBER_THREADS = 1;
    private static final int MATRIX_SIZE = 2000;

    public static void main(String[] args) {
        //testSequentialMatrixMultiply();
        //testParallelMatrixMultiply();

        // Generate two random matrices, same size
        double[][] a = generateRandomMatrix(MATRIX_SIZE, MATRIX_SIZE);
        double[][] b = generateRandomMatrix(MATRIX_SIZE, MATRIX_SIZE);
        sequentialMultiplyMatrix(a, b);
        //parallelMultiplyMatrix(a, b);
    }

    /**
     * Returns the result of a sequential matrix multiplication
     * The two matrices are randomly generated
     * @param a is the first matrix
     * @param b is the second matrix
     * @return the result of the multiplication
     *
     * @pre a and b are nxn square matrices
     * */
    public static double[][] sequentialMultiplyMatrix(double[][] a, double[][] b) {
        int n = a.length; // assume a.length == b.length
        double[][] c = new double[n][n]; // a . b = c

        // for each column of b
        for (int bj = 0; bj < n; bj++) {

            // get the column from b
            double[] bCol = new double[n];
            for (int bi = 0; bi < n; bi++) {
                bCol[bi] = b[bi][bj];
            }

            // for each row of a
            for (int ai = 0; ai < n; ai++) {

                // dot_product(a row, b column)
                for (int aj = 0; aj < n; aj++) {

                    // to get each value in a cell of c
                    c[ai][bj] += a[ai][aj]*bCol[aj];
                }
            }
        }

        return c;
    }

    /**
     * Returns the result of a concurrent matrix multiplication
     * The two matrices are randomly generated
     * @param a is the first matrix
     * @param b is the second matrix
     * @return the result of the multiplication
     * */
    public static double[][] parallelMultiplyMatrix(double[][] a, double[][] b) {
        return new double[][] {{1,2},{3,4}};
    }

    /**
     * Populates a matrix of given size with randomly generated integers between 0-10.
     * @param numRows number of rows
     * @param numCols number of cols
     * @return matrix
     */
    private static double[][] generateRandomMatrix (int numRows, int numCols) {
        double matrix[][] = new double[numRows][numCols];
        for (int row = 0 ; row < numRows ; row++ ) {
            for (int col = 0 ; col < numCols ; col++ ) {
                matrix[row][col] = (double) ((int) (Math.random() * 10.0));
            }
        }
        return matrix;
    }

    /**
     * Print a matrix. Useful for testing.
     */
    public static void printMatrix(double[][] M) {
        if (M.length == 0 || M[0].length == 0) return;

        for (double[] row : M) {
            System.out.print(row[0]);
            for (int j = 1; j < row.length; j++) {
                System.out.print(" " + row[j]);
            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     * Test the static method sequentialMatrixMultiply(a,b).
     *
     * Multiplies two 2x2 identity matrices and prints the results.
     */
    public static void testSequentialMatrixMultiply() {
        double[][] A = new double[][] {{1,0},{0,1}};
        double[][] B = new double[][] {{1,0},{0,1}};
        double[][] C = sequentialMultiplyMatrix(A, B);
        printMatrix(C);
    }

    /**
     * Test the static method parallelMatrixMultiply(a,b).
     *
     * Multiplies two 2x2 identity matrices and prints the results.
     */
    public static void testParallelMatrixMultiply() {
        double[][] A = new double[][] {{1,0},{0,1}};
        double[][] B = new double[][] {{1,0},{0,1}};
        double[][] C = parallelMultiplyMatrix(A, B);
        printMatrix(C);
    }
}
