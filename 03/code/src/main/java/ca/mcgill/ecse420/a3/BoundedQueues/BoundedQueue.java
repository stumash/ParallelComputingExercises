package ca.mcgill.ecse420.a3.BoundedQueues;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * A Bounded, Lock-Based Queue backed by an array.
 * Uses two locks: one for enq() and one for deq()
 *
 * enq() and deq() are blocking calls
 * @param <E>
 */
public class BoundedQueue<E> implements Queue<E>
{
    private E[] items;

    private volatile int head;
    private volatile int tail;

    private Lock lockHead = new ReentrantLock();
    private Lock lockTail = new ReentrantLock();

    private Condition notEmpty = lockHead.newCondition();
    private Condition notFull  = lockTail.newCondition();

    @SuppressWarnings("unchecked")
    public BoundedQueue(int capacity) {
        items = (E[]) new Object[capacity];
        head = 0;
        tail = 0;
    }

    /**
     * Append an object at the tail of the queue
     */
    public void enq(E item) {
        boolean mustWakeDequeuers = false;
        lockTail.lock();
        try {
            while (tail - head == items.length) {
                try { notFull.await(); } catch (InterruptedException ie) {}
            }
            items[tail % items.length] = item;

            tail++;

            if (tail - head == 1) {
                mustWakeDequeuers = true;
            }
        } finally {
            lockTail.unlock();
        }
        if (mustWakeDequeuers) {
            lockHead.lock();
            try {
                notEmpty.signalAll();
            } finally {
                lockHead.unlock();
            }
        }
    }

    /**
     * Remove the object at the head of the queue and return it
     */
    public E deq() {
        E result;
        boolean mustWakeEnqueuers = false;
        lockHead.lock();
        try {
            while (tail - head == 0) {
                try { notEmpty.await(); } catch (InterruptedException ie) {}
            }
            result = items[head % items.length];

            head++;

            if (tail - head == items.length - 1) {
                mustWakeEnqueuers = true;
            }
        } finally {
            lockHead.unlock();
        }
        if (mustWakeEnqueuers) {
            lockTail.lock();
            try {
                notFull.signalAll();
            } finally {
                lockTail.unlock();
            }
        }
        return result;
    }

    /**
     * not thread-safe!
     *
     * @return a string representation of the q's current state
     */
    public String toString() {
        StringBuilder strBldr = new StringBuilder("Start");
        for (int i = head; i < tail; i++) {
            strBldr.append(" -> " + items[i % items.length]);
        }
        strBldr.append(" -> End");
        return strBldr.toString();
    }
}

