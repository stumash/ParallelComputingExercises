package ca.mcgill.ecse420.a3.MatrixMultiplication;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ParallelMultiplier_Practical implements MatrixMultiplier
{
    private static final int NUM_PROCESSORS = Runtime.getRuntime().availableProcessors();

    @Override
    public int[] multiplyMbyA(int[][] M, int[] A) throws IllegalArgumentException
    {
        if (M[0].length != A.length) {
            throw new IllegalArgumentException(
                    "Matrix M must have N columns and Vector A must have N rows"
            );
        }

        return _multiplyMbyA(M, A);
    }

    public int[] _multiplyMbyA(int[][] M, int[] A)
    {
        int N = A.length;
        int[] B = new int[N];

        int numThreads = Math.min(N, NUM_PROCESSORS);

        // Each thread will compute 1 or more rows of product vector B.
        // The row ranges given to each thread are dependent on N and numThreads.
        // One task per thread is created for the matrix-vector multiplication corresponding to each row range.
        List<Callable<Object>> tasks = getTasks(N, numThreads, M, A, B);

        ExecutorService threadPool = Executors.newFixedThreadPool(numThreads);
        try {
            threadPool.invokeAll(tasks);
            threadPool.shutdown();
            threadPool.awaitTermination(10, TimeUnit.MINUTES);
        } catch(Exception e){
            e.printStackTrace();
            System.exit(1);
        }

        return B;
    }


    private List<Callable<Object>> getTasks(int N, int numThreads, int[][] M, int[] A, int[] B) {
        double rowsPerThread  = N / (double)numThreads;
        int lowRowsPerThread  = (int)Math.floor(rowsPerThread);
        int highRowsPerThread = (int)Math.ceil(rowsPerThread);

        double fracRowsPerThreadHighToLow = rowsPerThread % 1;
        int numThreadsHighRowsPerThread   = (int)(fracRowsPerThreadHighToLow * numThreads);
        int numThreadsLowRowsPerThread    = numThreads-1-numThreadsHighRowsPerThread;

        if (fracRowsPerThreadHighToLow == 0.0) {
            numThreadsHighRowsPerThread = numThreads;
            numThreadsLowRowsPerThread  = 0;
        }

        List<Callable<Object>> tasks = new ArrayList<>();

        int rowRangeStart = 0;
        int rowRangeEnd = 0;
        for (int threadNum = 0; threadNum < numThreads; threadNum++) {
            if (threadNum < numThreadsHighRowsPerThread) {
                rowRangeEnd = rowRangeStart + highRowsPerThread;
            } else if (threadNum < numThreadsHighRowsPerThread + numThreadsLowRowsPerThread) {
                rowRangeEnd = rowRangeStart + lowRowsPerThread;
            } else {
                rowRangeEnd = N;
            }

            PartialMatrixVectorMultiplication p =
                    new PartialMatrixVectorMultiplication(M,A,B, rowRangeStart,rowRangeEnd);
            rowRangeStart = rowRangeEnd;

            tasks.add(Executors.callable(p));
        }

        return tasks;
    }

    // assume that M[0].length == A.length. The check is done above
    class PartialMatrixVectorMultiplication implements Runnable
    {
        private int[][] M;
        private int[] A, B;
        private int rowRangeStart, rowRangeEnd;
        PartialMatrixVectorMultiplication(int[][] M, int[] A, int[] B, int rowRangeStart, int rowRangeEnd) {
            this.M = M;
            this.A = A;
            this.B = B;
            this.rowRangeStart = rowRangeStart;
            this.rowRangeEnd = rowRangeEnd;
        }
        @Override
        public void run() {
            // compute M . A = B in place
            for (int rowIdx = rowRangeStart; rowIdx < rowRangeEnd; rowIdx++) {
                int[] row = M[rowIdx];

                int dotProduct = 0;
                for (int i = 0; i < A.length; i++) {
                    dotProduct += row[i] * A[i];
                }
                B[rowIdx] = dotProduct;
            }
        }
    }
}
