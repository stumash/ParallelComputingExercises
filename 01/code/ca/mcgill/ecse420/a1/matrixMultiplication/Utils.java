package ca.mcgill.ecse420.a1.matrixMultiplication;

public class Utils {

    /**
     * Print a matrix. Useful for testing.
     */
    public static void printMatrix(double[][] M) {
        if (M.length == 0 || M[0].length == 0)
            return;

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
     * Generate nxn identity matrix
     */
    public static double[][] identityMatrix(int n) {
        double[][] matrix = new double[n][n];
        for (int ij = 0; ij < n; ij++) {
            matrix[ij][ij] = 1.0;
        }
        return matrix;
    }

    /**
     * Verify that matrix argument A is an nxn identity matrix.
     * Assume matrix A is sqaure
     */
    public static boolean verifyIdentityMatrix(double[][] A, int n) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if ((i != j && A[i][j] != 0.0) || (i == j && A[i][j] != 1.0)) {
                    System.out.println("ERROR ERROR ERROR");
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Populates a matrix of given size with randomly generated integers between 0-10.
     */
    public static double[][] generateRandomMatrix (int numRows, int numCols) {
        double matrix[][] = new double[numRows][numCols];
        for (int row = 0 ; row < numRows ; row++ ) {
            for (int col = 0 ; col < numCols ; col++ ) {
                matrix[row][col] = (double) ((int) (Math.random() * 10.0));
            }
        }
        return matrix;
    }
}
