package ca.mcgill.ecse420.a3.MatrixMultiplication;

import java.util.concurrent.*;

/**
 * Highly parallel implementation of matrix-vector multiplication.
 *
 * Algorithm design inspired by recursive and highly parallel matrix addition
 * and multiplication algorithms in chapter 16-1 of Herlihy and Shavit's
 * 'The Art of Multiprocessor programming'.
 *
 * This implementation, despite being highly parallel, performs terribly. Given
 * the low upper limit on parallelism in practice (most computers can hardly
 * run more than 8 threads in true parallel), the degree of multithreading employed
 * by this algorithm costs much more in overhead than what is gained in parallelism.
 *
 * In fact, the design of this program violates a basic guideline of multithreading,
 * which is to avoid spawning many more long-living threads than there are CPU cores.
 * Worse still is the high degree of inter-dependence of the (often thousands) of
 * threads that are spawned. The sheer number of threads added to the fact that the
 * majority of them must wait for others to complete nearly guarantees that the thread
 * scheduler will be overwhelmed and much of the execution of this code will be spent
 * rapidly switching between thousands of blocked threads.
 *
 * This program can even be used as a lesson in the avoidance of impractically
 * multithreaded algorithms.
 *
 * For this reason, passing in inputs where the matrix rows and vector columns are
 * larger than 200 throws an error. If inputs are larger than 1000, the entire computer
 * may crash due to total depletion of memory resources.
 */

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
        if (M[0].length > 200) {
            throw new IllegalArgumentException(
                "Will not accept N greater than 200 to prevent OS crashes"
            );
        }

        return _multiplyMbyA(M, A);
    }

    private int[] _multiplyMbyA(int[][] M, int[] A)
    {
        int N = A.length;
        int[] B = new int[N];

        try {
            Future<?> MbyATermination = threadPool.submit(new MatrixVectorMultTask(M, A, 0, N, B));
            try {
                MbyATermination.get();
            }
            catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }

            threadPool.shutdown();
            threadPool.awaitTermination(30, TimeUnit.SECONDS);
        }
        catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        return B;
    }

    class MatrixVectorMultTask implements Runnable
    {
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

                    firstHalf.get();
                    secondHalf.get();
                }
            }
            catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
    }

    class DotProdTask implements Callable<Integer>
    {
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
