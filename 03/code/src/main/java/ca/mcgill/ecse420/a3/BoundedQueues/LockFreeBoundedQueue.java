package ca.mcgill.ecse420.a3.BoundedQueues;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * A Bounded Lock-Free Queue backed by an array.
 * Uses two extra atomic integers for head and tail to prevent data corruption.
 *
 * enq() and deq() are blocking calls
 *
 * @param <E>
 */
public class LockFreeBoundedQueue<E> implements Queue<E>
{
    private E[] items;

    private AtomicInteger head = new AtomicInteger(0);
    private AtomicInteger tail = new AtomicInteger(0);
    private AtomicInteger tailCommit = new AtomicInteger(0);
    private AtomicInteger latentCapacity;

    public LockFreeBoundedQueue(int capacity) {
        items = (E[]) new Object[capacity];
        latentCapacity = new AtomicInteger(capacity);
    }

    public void enq(E item) {
        int lc = latentCapacity.get();
        while (lc <= 0 || !latentCapacity.compareAndSet(lc, lc - 1))
            lc = latentCapacity.get();

        int t = tail.getAndIncrement();
        items[t % items.length] = item;

        while (tailCommit.compareAndSet(t, t + 1)) { };
    }

    public E deq() {
        int h = head.getAndIncrement();
        while (h >= tailCommit.get()) { };
        E item = items[h % items.length];
        latentCapacity.incrementAndGet();

        return item;
    }

    public String toString() {
        return null;
    }
}
