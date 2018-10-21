package ca.mcgill.ecse420.a1.diningPhilosophers.canDeadlock;

import java.util.concurrent.locks.ReentrantLock;

public class Chopstick {
    private final ReentrantLock lock = new ReentrantLock();
    private final int id;

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
    public boolean bePickedUpBy(Philosopher p, String which) throws InterruptedException {
        if (lock.tryLock()) {
            System.out.println("Philosopher "+p.getId()+" picked up "+which+" Chopstick "+id);
            return true;
        }

        System.out.println("Philosopher "+p.getId()+" COULD NOT pick up "+which+" Chopstick "+id);
        return false;
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
