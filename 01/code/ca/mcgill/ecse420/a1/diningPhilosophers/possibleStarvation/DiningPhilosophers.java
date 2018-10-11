package ca.mcgill.ecse420.a1.diningPhilosophers.possibleStarvation;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DiningPhilosophers {
    public static void main(String[] args) {
        int numberOfPhilosophers = 5;
        Philosopher[] philosophers = new Philosopher[numberOfPhilosophers];
        Chopstick[] chopsticks = new Chopstick[numberOfPhilosophers];

        ExecutorService executorService = null;

        try {
            //      Place the chopstick on table   
            for (int i = 0; i < numberOfPhilosophers; i++) {
                chopsticks[i] = new Chopstick(i);
            }
            //      Creates thread pool, one for each philosopher
            executorService = Executors.newFixedThreadPool(numberOfPhilosophers);

            //      Populate the philosopher and assign the chopstick on their sides
            for (int i = 0; i < numberOfPhilosophers; i++) {
                philosophers[i] = new Philosopher(i, chopsticks[i], chopsticks[(i + 1) % numberOfPhilosophers]);
                executorService.execute(philosophers[i]);
            }
        }finally{
            //        Threads will stop accepting new tasks
            executorService.shutdown();
        }
    }
}
