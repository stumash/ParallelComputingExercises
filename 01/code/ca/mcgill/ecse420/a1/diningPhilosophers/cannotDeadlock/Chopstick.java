package ca.mcgill.ecse420.a1.diningPhilosophers.cannotDeadlock;

import java.util.concurrent.locks.ReentrantLock;

public class Chopstick {
    private ReentrantLock lock = new ReentrantLock();
    private int id;

    public Chopstick(int id) {
        this.id = id;
    }

    /**
     * Chopstick's lock is acquired by Philosopher's thread
     *
     * @param p Philosopher acquiring this Chopstick's lock
     * @param which this Chopstick is p's "left" or "right" Chopstick
     *
     * @return true if lock is acquired, else false
     */
    public void bePickedUpBy(Philosopher p, String which) throws InterruptedException {
        System.out.println("Philosopher "+p.getId()+" waiting to pick up "+which+" Chopstick "+id);
        lock.lock();
        System.out.println("Philosopher "+p.getId()+" picked up "+which+" Chopstick "+id);
    }

    /**
     * Chopstick's lock is released by Philosopher's thread
     *
     * @param p Philosopher releasing this Chopsticks's lock
     * @param which this Chopstick is p's "left" or "right" Chopstick
     */
    public void bePutDownBy(Philosopher p, String which) {
        lock.unlock();
        System.out.println("Philosopher "+p.getId()+" put down "+which+" Chopstick "+id);
    }
}
