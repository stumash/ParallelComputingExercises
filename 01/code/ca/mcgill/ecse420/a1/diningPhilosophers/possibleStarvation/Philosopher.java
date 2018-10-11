package ca.mcgill.ecse420.a1.diningPhilosophers.possibleStarvation;

import java.util.concurrent.ThreadLocalRandom;

public class Philosopher implements Runnable {
    private int id;
    private Chopstick leftChopstick;
    private Chopstick rightChopstick;

    /**
     * @param id Philosopher number
     *
     * @param leftChopstick
     * @param rightChopstick
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
                think();

                if (this.leftChopstick == null) {
                    System.out.println("OH fuck left");
                    System.exit(1);
                }
                if (leftChopstick.bePickedUpBy(this, "left")) {
                    if (rightChopstick.bePickedUpBy(this, "right")) {
                        eat();
                        rightChopstick.bePutDownBy(this, "right");
                        leftChopstick.bePutDownBy(this, "left");
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void think() throws InterruptedException {
        System.out.println("Philosopher "+id+" is thinking");
        Thread.sleep(ThreadLocalRandom.current().nextInt(500));
    }

    private void eat() throws InterruptedException {
        try {
            System.out.println("Philosopher "+id+" started eating");
            Thread.sleep(ThreadLocalRandom.current().nextInt(500));
            System.out.println("Philosopher "+id+" finished eating");
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }
}
