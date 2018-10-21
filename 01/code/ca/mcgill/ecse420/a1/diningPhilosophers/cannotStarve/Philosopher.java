package ca.mcgill.ecse420.a1.diningPhilosophers.cannotStarve;

import java.util.concurrent.ThreadLocalRandom;

public class Philosopher implements Runnable {
    private final int id;
    private final Chopstick firstChopstick;
    private final Chopstick secondChopstick;

    /**
     * @param id Philosopher number
     *
     * @param chopsticks
     * @param chopsticks2
     */
    public Philosopher(int id, Chopstick firstChopstick, Chopstick secondChopstick) {
        this.id = id;
        this.firstChopstick = firstChopstick;
        this.secondChopstick = secondChopstick;
    }

    public int getId() {
        return this.id;
    }

    @Override
    public void run() {
        String firstChopstickSide  = "left";
        String secondChopstickSide = "right";
        if (this.id % 2 == 0) {
            firstChopstickSide  = "right";
            secondChopstickSide = "left";
        }

        try {
            while(true) {
                think();

                firstChopstick.bePickedUpBy(this, firstChopstickSide);
                secondChopstick.bePickedUpBy(this, secondChopstickSide);

                eat();

                firstChopstick.bePutDownBy(this, firstChopstickSide);
                secondChopstick.bePutDownBy(this, secondChopstickSide);
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
