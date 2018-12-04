package ca.mcgill.ecse420.a3.MatrixMultiplication;

import java.util.concurrent.ThreadLocalRandom;

public class Utils {

    public static int[][] makeIdentityMatrix(int N) {
        int[][] retval = new int[N][N];

        for (int i = 0; i < N; i++) {
            retval[i][i] = 1;
        }

        return retval;
    }

    public static int[] makeRandomVector(int N) {
        int[] A = new int[N];

        for (int i = 0; i < N; i++) {
            A[i] = _randInt_in_0_to_1000();
        }

        return A;
    }

    public static int[][] makeRandomMatrix(int N) {
        int[][] M = new int[N][N];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                M[i][j] = _randInt_in_0_to_1000();
            }
        }

        return M;
    }

    private static int _randInt_in_0_to_1000() {
        return ThreadLocalRandom.current().nextInt(0,1001);
    }

    public static void printMatrix(int[][] M) {
        for (int i = 0; i < M.length; i++) {
            for (int j = 0; j < M[i].length; j++) {
                if (j != 0) {
                    System.out.print(",");
                }
                System.out.print(String.format("%1$-" + 4 + "s", (M[i][j])));
            }
            System.out.println();
        }
    }

    public static void printVector(int[] A) {
        for (int i = 0; i < A.length; i++) {
            if (i != 0) {
                System.out.print(",");
            }
            System.out.print(String.format("%1$-" + 4 + "s", (A[i])));
        }
        System.out.println();
    }
}
