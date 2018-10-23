package ca.mcgill.ecse420.a1.diningPhilosophers.cannotDeadlock;

import java.util.concurrent.ThreadLocalRandom;

public class Philosopher implements Runnable {
    private final int id;
    private final boolean leftFirst;
    private final Chopstick leftChopstick;
    private final Chopstick rightChopstick;

    /**
     * @param id Philosopher number
     *
     * @param chopsticks
     * @param chopsticks2
     */
    public Philosopher(int id, boolean leftFirst, Chopstick leftChopstick, Chopstick rightChopstick) {
        this.id = id;
        this.leftFirst = leftFirst;
        this.leftChopstick = leftChopstick;
        this.rightChopstick = rightChopstick;
    }

    public int getId() {
        return this.id;
    }

    @Override
    public void run() {
        boolean ate = true;

        try {
            while(true) {
                // only start thinking AGAIN if you ate, else just continue waiting to eat
                if (ate) {
                    think();
                }

                ate = false;
                if (rightChopstick.bePickedUpBy(this, "right")) {
                    if (leftChopstick.bePickedUpBy(this, "left")) {
                        eat();
                        ate = true;
                        leftChopstick.bePutDownBy(this, "left");
                    }
                    // drop rightChopstick even if couldn't get left one. prevents 'hold and wait'
                    rightChopstick.bePutDownBy(this, "right");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void think() throws InterruptedException {
        System.out.println("Philosopher "+id+" is thinking");
        Thread.sleep(ThreadLocalRandom.current().nextInt(500));
    }

    private void eat() throws InterruptedException {
        System.out.println("Philosopher "+id+" is eating");
        Thread.sleep(ThreadLocalRandom.current().nextInt(500));
    }
}
