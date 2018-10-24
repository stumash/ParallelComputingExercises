package ca.mcgill.ecse420.a1.diningPhilosophers.cannotDeadlock;

import java.util.concurrent.locks.ReentrantLock;

public class Chopstick {
    private static final String wait_fString    = "Philosopher %d waiting for %s Chopstick %d\n";
    private static final String pickUp_fString  = "Philosopher %d picked up %s Chopstick %d\n";
    private static final String putDown_fString = "Philosopher %d put down %s Chopstick %d\n";

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
        System.out.format(wait_fString, p.getId(), which, this.id);
        lock.lock();
        System.out.format(pickUp_fString, p.getId(), which, this.id);
    }

    /**
     * Chopstick's lock is released by Philosopher's thread
     *
     * @param p Philosopher releasing this Chopsticks's lock
     * @param which this Chopstick is p's "left" or "right" Chopstick
     */
    public void bePutDownBy(Philosopher p, String which) {
        lock.unlock();
        System.out.format(putDown_fString, p.getId(), which, this.id);
    }
}
