package ca.mcgill.ecse420.a3.BoundedQueues;

public interface Queue<E> {
    public void enq(E elem);
    public E deq();
    public String toString();
}
