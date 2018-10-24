package ca.mcgill.ecse420.a1.diningPhilosophers.cannotStarve;

import java.util.Deque;
import java.util.ArrayDeque;
import java.util.concurrent.Semaphore;

/**
 * Chopstick, essentially a fair-lock.
 *
 * If you try to pick up the chopstick while someone else has it, you get put in a queue.
 * This queue prevents starvation by guaranteeing FIFO lock acquisition.
 */
public class Chopstick {
    private static final String wait_fString = "Philsopher %d waiting to pick up %s Chopstick %d\n";
    private static final String pickUp_fString = "Philosopher %d picked up %s Chopstick %d\n";
    private static final String putDown_fString = "Philosopher %d put down %s Chopstick %d\n";

    private Deque<Semaphore> q = new ArrayDeque<>();
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
        Semaphore s = new Semaphore(1);
        synchronized(q) {
            if (!q.isEmpty()) {
                s.acquire(); // queue not empty, next acquire() should block
            }
            q.addLast(s);
        }

        System.out.format(wait_fString, p.getId(), which, this.id);
        s.acquire(); // decrement semaphore, blocks
        System.out.format(pickUp_fString, p.getId(), which, this.id);
    }

    /**
     * Chopstick's lock is released by Philosopher's thread
     *
     * @param p Philosopher releasing this Chopsticks's lock
     * @param which this Chopstick is p's "left" or "right" Chopstick
     */
    public void bePutDownBy(Philosopher p, String which) {
        synchronized(q) {
            q.removeFirst(); // dequeue yourself
            System.out.format(putDown_fString, p.getId(), which, this.id);
        }
        if (!q.isEmpty()) {
            Semaphore s = q.peekFirst();
            s.release();
        }
    }
}
