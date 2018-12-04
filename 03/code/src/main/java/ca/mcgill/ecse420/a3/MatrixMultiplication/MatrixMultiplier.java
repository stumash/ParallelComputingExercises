package ca.mcgill.ecse420.a3.MatrixMultiplication;

public interface MatrixMultiplier {
    public int[] multiplyMbyA(int[][] M, int[] A)
        throws IllegalArgumentException;
}
