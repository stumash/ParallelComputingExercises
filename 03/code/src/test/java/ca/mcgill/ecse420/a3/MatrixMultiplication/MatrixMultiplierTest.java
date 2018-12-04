package ca.mcgill.ecse420.a3.MatrixMultiplication;

import org.junit.BeforeClass;
import org.junit.Test;

import static ca.mcgill.ecse420.a3.MatrixMultiplication.Utils.makeIdentityMatrix;
import static ca.mcgill.ecse420.a3.MatrixMultiplication.Utils.makeRandomVector;
import static org.junit.Assert.assertArrayEquals;

public class MatrixMultiplierTest {

    private static final int N = 100;

    private static int[][] M;
    private static int[] A;

    @BeforeClass
    public static void setUp() {
        M = makeIdentityMatrix(N);
        A = makeRandomVector(N);
    }

    @Test
    public void testSequentialMultiplier() {
        long startTime = System.currentTimeMillis();
        assertArrayEquals(A, new SequentialMultiplier().multiplyMbyA(M, A));
        System.out.println("sequential running time: "+(System.currentTimeMillis() - startTime));
    }

    @Test
    public void testParallelMultiplier() {
        long startTime = System.currentTimeMillis();
        assertArrayEquals(A, new ParallelMultiplier().multiplyMbyA(M, A));
        System.out.println("parallel running time:   "+(System.currentTimeMillis() - startTime));
    }
}
