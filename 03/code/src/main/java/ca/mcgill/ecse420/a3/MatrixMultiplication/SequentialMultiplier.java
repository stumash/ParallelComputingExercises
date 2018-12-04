package ca.mcgill.ecse420.a3.MatrixMultiplication;

public class SequentialMultiplier implements MatrixMultiplier
{
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

    private int[] _multiplyMbyA(int[][] M, int[] A)
    {
        int N = A.length;
        int[] B = new int[N];

        for (int i = 0; i < N; i++) {
            B[i] = _dotProduct(M[i], A);
        }

        return B;
    }

    private int _dotProduct(int[] A, int[] B)
    {
        int N = A.length;

        int sum = 0;
        for (int i = 0; i < N; i++) {
            sum += A[i] * B[i];
        }
        return sum;
    }
}
