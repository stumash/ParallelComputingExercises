package myPackage;

import java.util.concurrent.atomic.AtomicInteger;

public class LockFreeBoundedQueue<E> {
    private E[] items;

    private AtomicInteger head = new AtomicInteger(0);
    private AtomicInteger tail = new AtomicInteger(0);
    private AtomicInteger tailCommit = new AtomicInteger(0);
    private AtomicInteger latentCapacity;

    @SuppressWarnings("unchecked")
    public LockFreeBoundedQueue(int capacity) {
        items = (E[]) new Object[capacity];
        latentCapacity = new AtomicInteger(capacity);
    }

    public void enq(E item) throws InterruptedException {
        int lc = latentCapacity.get();
        while (lc <= 0 || !latentCapacity.compareAndSet(lc, lc - 1)) 
            lc = latentCapacity.get();
        
        int t = tail.getAndIncrement();
        items[t % items.length] = item;

        while (tailCommit.compareAndSet(t, t + 1)) { };
    }

    public E deq() throws InterruptedException {
        int h = head.getAndIncrement();
        while (h >= tailCommit.get()) { };
        E item = items[h % items.length];
        latentCapacity.incrementAndGet();

        return item;
    }
}
