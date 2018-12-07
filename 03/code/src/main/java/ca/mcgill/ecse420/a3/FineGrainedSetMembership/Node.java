package ca.mcgill.ecse420.a3.FineGrainedSetMembership;

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Lock;


public class Node<T>
{
    private int key;
    private Lock lock;

    public T item;
    public Node<T> next;

    public Node(T item) {
        this.item = item;
        this.key = item.hashCode();
        lock = new ReentrantLock();
    }

    public Node(T item, boolean useMaxKey) {
        this.item = item;
        this.key = Integer.MAX_VALUE;
        this.lock = new ReentrantLock();
    }

    public int getKey() {
        return this.key;
    }

    public void lock() {
        this.lock.lock();
    }

    public void unlock() {
        this.lock.unlock();
    }
}
