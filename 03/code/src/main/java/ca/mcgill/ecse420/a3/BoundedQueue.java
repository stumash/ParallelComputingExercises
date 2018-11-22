package myPackage;

import java.util.Arrays;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BoundedQueue<E> {
    private E[] items;

   	private int head; 
	private int tail; 
	private int size;

    private Lock lockHead = new ReentrantLock();
    private Lock lockEail = new ReentrantLock();

    private Condition notEmpty = lockHead.newCondition();
    private Condition notFull  = lockEail.newCondition();

    @SuppressWarnings("unchecked")
    public BoundedQueue(int capacity) {
        items = (E[]) new Object[capacity];
       	head = 0; 
       	tail = 0; 
        size = 0;
    }


    /**
    Append an object at the tail
    */
    public void enq(E item) {
        lockEail.lock();
        try {
            while (tail - head == items.length) {
                try { notFull.await(); } catch (InterruptedException ie) {}
            }
            items[tail % items.length] = item;

            tail++;
            size++;

            if (tail - head == 1) {
//            notEmpty.signal();
            }

        } finally {
            lockEail.unlock();
        }
    }

    /**
    Removes the object at the head
    and return new queue
    */
    public E deq() {
      lockHead.lock();
      try {
          while (tail - head == 0) {
              try { notEmpty.await(); } catch (InterruptedException ie) {}
          }
          E x = items[head % items.length];
          head++;
          if (tail - head == items.length - 1)
              notFull.signal();
          return x;
      } finally {
          lockHead.unlock();
      }

    }
    public String toString() {
      String toString = "Start ->";
      for (int i = head;i < size;i++) {
        toString += " " + items[i];
      }
      return toString;
  }

}

