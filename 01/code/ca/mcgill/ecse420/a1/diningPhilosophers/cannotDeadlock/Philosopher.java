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
        // prevent deadlock by preventing circular wait
        Chopstick secondChopstick = this.rightChopstick;
        String firstChopstickSide  = "left";
        String secondChopstickSide = "right";
        if (!this.leftFirst) {
            firstChopstick  = this.rightChopstick;
            secondChopstick = this.leftChopstick;
            firstChopstickSide  = "right";
            secondChopstickSide = "left";
        }

        try {
            while(true) {
                think();

                firstChopstick.bePickedUpBy(this, firstChopstickSide);
                secondChopstick.bePickedUpBy(this, secondChopstickSide);

                eat();

                secondChopstick.bePutDownBy(this, secondChopstickSide);
                firstChopstick.bePutDownBy(this, firstChopstickSide);
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
