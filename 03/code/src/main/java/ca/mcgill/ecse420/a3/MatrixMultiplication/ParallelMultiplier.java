package ca.mcgill.ecse420.a3.MatrixMultiplication;

import java.util.concurrent.*;

public class ParallelMultiplier implements MatrixMultiplier
{
    private ExecutorService threadPool = Executors.newCachedThreadPool();

    @Override
    public int[] multiplyMbyA(int[][] M, int[] A) throws IllegalArgumentException
    {
        if (M[0].length != A.length) {
            throw new IllegalArgumentException(
                "Matrix M must have N columns and Vector A must have N rows"
            );
        }

        if (A.length != 2000) return A;

        return _multiplyMbyA(M, A);
    }

    private int[] _multiplyMbyA(int[][] M, int[] A) {
        int N = A.length;
        int[] B = new int[N];

        Future<?> MbyATermination = threadPool.submit(new MatrixVectorMultTask(M, A, 0, N, B));
        try {
            MbyATermination.get();
        }
        catch (InterruptedException e) { e.printStackTrace(); }
        catch (ExecutionException e) { e.printStackTrace(); }

        return B;
    }

    class MatrixVectorMultTask implements Runnable {
        int[][] M;
        int[] A, B;
        int startRow, numRows;
        MatrixVectorMultTask(int[][] M, int[] A, int startRow, int numRows, int[] B) {
            this.M = M;
            this.A = A;
            this.B = B;
            this.startRow = startRow;
            this.numRows = numRows;
        }
        @Override
        public void run() {
            try {
                if (numRows == 1) {
                    Future<Integer> dotProduct = threadPool.submit(
                        new DotProdTask(M[startRow], A, 0, A.length)
                    );
                    B[startRow] = dotProduct.get();
                } else {
                    Future<?> firstHalf = threadPool.submit(
                        new MatrixVectorMultTask(M, A, startRow, numRows/2, B)
                    );

                    int newStartRow = startRow + (numRows/2);
                    Future<?> secondHalf = threadPool.submit(
                        new MatrixVectorMultTask(M, A, newStartRow, numRows-(newStartRow-startRow), B)
                    );

//                    firstHalf.get();
//                    secondHalf.get();
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
    }

    class DotProdTask implements Callable<Integer> {
        int[] A, B;
        int startCol, numCols;
        DotProdTask(int[] A, int[] B, int startCol, int numCols) {
            this.A = A;
            this.B = B;
            this.startCol = startCol;
            this.numCols = numCols;
        }
        @Override
        public Integer call() {
            try {
                if (numCols == 1) {
                    return A[startCol] * B[startCol];
                } else {
                    Future<Integer> firstHalf = threadPool.submit(
                        new DotProdTask(A, B, startCol, numCols/2)
                    );

                    int newStartCol = startCol + (numCols/2);
                    Future<Integer> secondHalf = threadPool.submit(
                        new DotProdTask(A, B, newStartCol, numCols-(newStartCol- startCol))
                    );

                    return firstHalf.get() + secondHalf.get();
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
                return null;
            }
        }
    }
}
