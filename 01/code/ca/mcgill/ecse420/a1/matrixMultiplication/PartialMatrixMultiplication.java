package ca.mcgill.ecse420.a1.matrixMultiplication;

public class PartialMatrixMultiplication implements Runnable
{
    private double[][] a;
    private double[][] b;
    private double[][] c;
    private int colRangeStart;
    private int colRangeEnd;

    public PartialMatrixMultiplication(double[][] a, double[][] b, double[][] c, int start, int end)
    {
        this.a = a;
        this.b = b;
        this.c = c;
        this.colRangeStart = start;
        this.colRangeEnd   = end;
    }

    @Override
    public void run()
    {
        sequentialMultiplyMatrix();
    }

    private void sequentialMultiplyMatrix()
    {
        int n = a.length; // assume a.length == b.length

        // for each column of b
        for (int bj = colRangeStart; bj < colRangeEnd; bj++) {

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
    }
}
