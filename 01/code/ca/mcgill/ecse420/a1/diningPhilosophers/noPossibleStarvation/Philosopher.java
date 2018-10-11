package ca.mcgill.ecse420.a1.diningPhilosophers.noPossibleStarvation;

import java.util.concurrent.ThreadLocalRandom;

public class Philosopher implements Runnable {
    private final int id;
    private final Chopstick leftChopstick;
    private final Chopstick rightChopstick;

    /**
     * @param id Philosopher number
     *
     * @param chopsticks
     * @param chopsticks2
     */
    public Philosopher(int id, Chopstick leftChopstick, Chopstick rightChopstick) {
        this.id = id;
        this.leftChopstick = leftChopstick;
        this.rightChopstick = rightChopstick;
    }

    public int getId() {
        return this.id;
    }

    @Override
    public void run() {
        try {
            while(true) {
                // Philosopher with even id pick up left first
                if ((this.id & 1) == 0 ) {
                    think();

                    if (rightChopstick.bePickedUpBy(this, "right")) {
                        if (leftChopstick.bePickedUpBy(this, "left")) {
                            eat();
                            leftChopstick.bePutDownBy(this, "left");
                        }
                        // drop rightChopstick even if couldn't get left one. prevents 'hold and wait'
                        rightChopstick.bePutDownBy(this, "right");
                    }
                }
                // Philosopher with an odd id pick up right first
                else {
                    think();
                    if (leftChopstick.bePickedUpBy(this, "left")) {
                        if (rightChopstick.bePickedUpBy(this, "right")) {
                            eat();
                            rightChopstick.bePutDownBy(this, "right");
                        }
                        // drop leftChopstick even if couldn't get right one. prevents 'hold and wait'
                        leftChopstick.bePutDownBy(this, "left");
                    }
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
