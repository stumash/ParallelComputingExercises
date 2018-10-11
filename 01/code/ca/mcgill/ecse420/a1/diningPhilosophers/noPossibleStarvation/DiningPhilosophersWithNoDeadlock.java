package ca.mcgill.ecse420.a1.diningPhilosophers.noPossibleStarvation;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DiningPhilosophersWithNoDeadlock {

    public static void main(String[] args) {
        int numberOfPhilosophers = 10;
        Philosopher[] philosophers = new Philosopher[numberOfPhilosophers];
        Chopstick[] chopsticks = new Chopstick[numberOfPhilosophers];

        ExecutorService executorService = null;

        try {
            for (int i = 0; i < numberOfPhilosophers; i++) {
                chopsticks[i] = new Chopstick(i);
            }

            executorService = Executors.newFixedThreadPool(numberOfPhilosophers);

            for (int i = 0; i < numberOfPhilosophers; i++) {
                philosophers[i] = new Philosopher(i, chopsticks[i], chopsticks[(i + 1) % numberOfPhilosophers]);
                executorService.execute(philosophers[i]);
            }
        }finally{
            executorService.shutdown();
        }
    }
}
