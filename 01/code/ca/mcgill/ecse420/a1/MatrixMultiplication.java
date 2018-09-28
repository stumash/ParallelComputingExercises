package ca.mcgill.ecse420.a1;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import java.util.List;
import java.util.ArrayList;

public class MatrixMultiplication {

    private static final String helpMessage = "\n" +
        "usage: java MatrixMultiplication [-s size] [-p parallelize[, -n numThreads]]\n" +
        "\n" +
        "    size,        int:     dimensions of all square matrices used in multiplication\n" +
        "                          (defaults to 2000)\n" +
        "    parallelize, boolean: if true do parallel matrix multiply else do sequential one\n" +
        "                          (defaults to false)\n" +
        "    numThreads,  int:     number of threads to use for parallel matrix multiply\n" +
        "                          (can only supply this arg if parallelize is true)\n" +
        "                          (must be divisible by 2 and less than size)\n" +
        "                          (defaults to 1)\n" +
        "\n" +
        "Examples:\n" +
        "\n" +
        "    java MatrixMultiplication -s 1000 -p true -n 8\n" +
        "    java MatrixMultiplication -p true -n 8\n" +
        "    java MatrixMultiplication -p true\n" +
        "    java MatrixMultiplication -s 1000 -p true\n";

    private static boolean PARALLELIZE = false;
    private static int MATRIX_SIZE     = 2000;
    private static int NUMBER_THREADS  = 1;

    public static void main(String[] args) {
        // sets values of PARALLELIZE, NUMBER_THREADS, and MATRIX_SIZE
        if (!parseCommandLineArgs(args)) {
            System.exit(1);
        } 

        // generate two random matrices, same size
        double[][] a = generateRandomMatrix(MATRIX_SIZE, MATRIX_SIZE);
        double[][] b = generateRandomMatrix(MATRIX_SIZE, MATRIX_SIZE);

        long startTime = System.currentTimeMillis();

        if (PARALLELIZE) {
            parallelMultiplyMatrix(a, b);
        } else {
            sequentialMultiplyMatrix(a, b);
        }

        System.out.println(System.currentTimeMillis() - startTime);
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
     *
     * @pre a and b are nxn square matrices
     * */
    public static double[][] parallelMultiplyMatrix(double[][] a, double[][] b) {
        int n = a.length; // assume a.length == b.length
        double[][] c = new double[n][n];

        List<Callable<Object>> tasks = new ArrayList<Callable<Object>>();

        // TODO: logic to divide mult into tasks based on NUMBER_THREADS
        for (int i = 0; i < NUMBER_THREADS; i++) {
            tasks.add(Executors.callable(() -> {
                System.out.println(); // TODO: fill this crap in
            }));
        }

        ExecutorService threadPool = Executors.newFixedThreadPool(NUMBER_THREADS);
        try {
            threadPool.invokeAll(tasks);
            threadPool.awaitTermination(10, TimeUnit.MINUTES); // wait a really long time if needed
        } catch(Exception e){
            e.printStackTrace();
            System.exit(1);
        }

        return c;
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

    private static boolean parseCommandLineArgs(String[] args) {
        if (args.length % 2 != 0 || args.length > 6) {
            System.out.println("ERROR: wrong number of args, expecting 2, 4, or 6");
            System.out.println(helpMessage);
            return false;
        }

        boolean matrixSizeIsSet  = false;
        boolean parallelizeIsSet = false;
        boolean numThreadsIsSet  = false;
        for (int i = 0; i < args.length; i+=2) {
            String flag = args[i];
            if (!flag.equals("-s") && !flag.equals("-p") && !flag.equals("-n")) {
                System.out.println("ERROR: bad syntax");
                System.out.println(helpMessage);
                return false;
            }

            String arg = args[i+1];
            if (flag.equals("-s")) {
                if (matrixSizeIsSet) {
                    System.out.println("ERROR: cannot set size twice");
                    System.out.println(helpMessage);
                    return false;
                }
                try {
                    MATRIX_SIZE = Integer.parseInt(arg);
                    matrixSizeIsSet = true;
                } catch(Exception e){
                    System.out.println("ERROR: size must be an integer");
                    System.out.println(helpMessage);
                    return false;
                }
            }
            else if (flag.equals("-p")) {
                if (parallelizeIsSet) {
                    System.out.println("ERROR: cannot set parallelize twice");
                    System.out.println(helpMessage);
                    return false;
                }
                try {
                    PARALLELIZE = Boolean.parseBoolean(arg);
                    parallelizeIsSet = true;
                } catch(Exception e) {
                    System.out.println("ERROR: parallelize must be a boolean");
                    System.out.println(helpMessage);
                    return false;
                }
            }
            else if (flag.equals("-n")) {
                if (numThreadsIsSet) {
                    System.out.println("ERROR: cannot set numThreads twice");
                    System.out.println(helpMessage);
                    return false;
                }
                if (!parallelizeIsSet || (parallelizeIsSet && !PARALLELIZE)) {
                    System.out.println("ERROR: '-p true' must be provided before '-n numThreads'");
                    System.out.println(helpMessage);
                    return false;
                }
                try {
                    NUMBER_THREADS = Integer.parseInt(arg);
                    numThreadsIsSet = true;
                } catch(Exception e) {
                    System.out.println("ERROR: numThreads must be an integer");
                    System.out.println(helpMessage);
                    return false;
                }
                if (NUMBER_THREADS % 2 != 0) {
                    System.out.println("ERROR: numThreads must be divisible by 2");
                    System.out.println(helpMessage);
                    return false;
                }
                if (NUMBER_THREADS > MATRIX_SIZE) {
                    System.out.println("ERROR: numThreads must be less than size");
                    System.out.println(helpMessage);
                    return false;
                }
            }
        }

        return true;
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
     * Non-exhaustive tests.
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
     * Non-exhaustive tests.
     */
    public static void testParallelMatrixMultiply() {
        double[][] A = new double[][] {{1,0},{0,1}};
        double[][] B = new double[][] {{1,0},{0,1}};
        double[][] C = parallelMultiplyMatrix(A, B);
        printMatrix(C);
    }
}
