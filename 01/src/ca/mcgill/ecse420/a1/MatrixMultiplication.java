package ca.mcgill.ecse420.a1;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MatrixMultiplication {

    private static final int NUMBER_THREADS = 1;
    private static final int MATRIX_SIZE = 2000;

    public static void main(String[] args) {

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
        double[][] c = new double[MATRIX_SIZE][MATRIX_SIZE]; // a . b = c

        // for each column of b
        for (int bj = 0; bj < MATRIX_SIZE; bj++) {

            // get the column from b
            double[] bCol = double[MATRIX_SIZE];
            for (int bi = 0; bi < MATRIX_SIZE; bi++) {
                bCol[bi] = b[bi][bj];
            }

            // for each row of a
            for (int ai = 0; ai < MATRIX_SIZE; ai++) {

                // do dot(a row, b column)
                for (int aj = 0; aj < MATRIX_SIZE; aj++) {

                    // to get each value in a cell of c
                    c[ai][bj] += a[ai][aj]+bCol[aj];
                }
            }
        }
    }

    /**
     * Returns the result of a concurrent matrix multiplication
     * The two matrices are randomly generated
     * @param a is the first matrix
     * @param b is the second matrix
     * @return the result of the multiplication
     * */
    public static double[][] parallelMultiplyMatrix(double[][] a, double[][] b) {

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

}
