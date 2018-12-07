package ca.mcgill.ecse420.a3.FineGrainedSetMembership;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FineGrainedSetTest
{
    static final int numElements = 10;

    static FineGrainedSet<Integer> set;

    @BeforeClass
    public static void setUp() {
        set = new FineGrainedSet<Integer>();

        for (int i = 1; i <= numElements; i++) {
            set.add(i);
        }
    }

    @Test
    public void findsContainedElements() {
        for (int i = 1; i <= numElements; i++) {
            assertTrue(set.contains(i));
        }
    }

    @Test
    public void doesNotFindMissingElements() {
        assertFalse(set.contains(0));
        assertFalse(set.contains(11));
    }

    @Test
    public void concurrentAccessTest() {
        class AddContainsRemoveContains extends Thread {
            static final int repetitions = 50;
            private int n;
            AddContainsRemoveContains(int n) {
                this.n = n;
            }
            @Override
            public void run() {
                try {
                    for (int i = 0; i < repetitions; i++) {
                        Thread.sleep(1);
                        set.add(n);
                        Thread.sleep(1);
                        assertTrue(set.contains(n));
                        Thread.sleep(1);
                        set.remove(n);
                        Thread.sleep(1);
                        assertFalse(set.contains(n));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    System.exit(1);
                }
            }
        }
        Thread[] ts = new Thread[] {
            new AddContainsRemoveContains(20),
            new AddContainsRemoveContains(21),
            new AddContainsRemoveContains(22),
        };
        for (Thread t : ts)
            t.start();
        for (Thread t : ts) {
            try { t.join(); }
            catch (InterruptedException e) { e.printStackTrace(); }
        }
    }
}
