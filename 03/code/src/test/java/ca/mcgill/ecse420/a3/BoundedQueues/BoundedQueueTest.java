package ca.mcgill.ecse420.a3.BoundedQueues;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BoundedQueueTest {

    static final int capacity = 200;

    static BoundedQueue<Integer> q;

    @Before
    public void setUp() {
        q = new BoundedQueue<>(capacity);
        q.enq(1);
        q.enq(2);
        q.enq(3);
        q.enq(4);
        q.enq(5);
    }

    @Test
    public void qToString() {
        assertEquals(q.toString(), "Start -> 1 -> 2 -> 3 -> 4 -> 5 -> End");
    }

    @Test
    public void concurrentBoundedQueueUsage() {
        q = new BoundedQueue<>(capacity); // fresh q for this test

        final int num = 999;

        for (int i = 0; i < capacity; i++) {
            q.enq(num);
        }

        class deqNum extends Thread {
            @Override
            public void run() {
                for (int i = 0; i < capacity/2; i++) {
                    assertTrue(q.deq() == num);
                }
            }
        }
        Thread[] ts = new Thread[] { new deqNum(), new deqNum() };

        for (Thread t : ts)
            t.start();
        for (Thread t : ts) {
            try { t.join(); }
            catch (InterruptedException e) { e.printStackTrace(); }
        }
    }
}
