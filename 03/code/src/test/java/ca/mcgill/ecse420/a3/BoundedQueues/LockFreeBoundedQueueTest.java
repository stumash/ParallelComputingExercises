package ca.mcgill.ecse420.a3.BoundedQueues;

import org.junit.Before;
import org.junit.Test;

public class LockFreeBoundedQueueTest {

    static final int capacity = 200;
    static final int num = 999;
    static final int leftover = 2;
    static LockFreeBoundedQueue<Integer> q;

    @Before
    public void setUp() {
        q = new LockFreeBoundedQueue<>(capacity);
        for (int i = 0; i < capacity-leftover; i++)
            q.enq(num);
        for (int i = 0; i < leftover; i++)
            q.enq(i+1);
    }

    @Test
    public void concurrentTest() {
        class qUser extends Thread {
            @Override
            public void run() {
                for (int i = 0; i < (capacity-leftover)/2; i++)
                    assert(num == q.deq());
            }
        }
        Thread[] ts = new Thread[] { new qUser(), new qUser() };
        for (Thread t : ts)
            t.start();
        for (Thread t : ts) {
            try { t.join(); }
            catch (InterruptedException e) { e.printStackTrace(); }
        }
        for (int i = 0; i < leftover; i++)
            assert(i+1 == q.deq());
    }
}
