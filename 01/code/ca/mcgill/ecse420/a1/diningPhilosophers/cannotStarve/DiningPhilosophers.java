package ca.mcgill.ecse420.a1.diningPhilosophers.cannotStarve;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class DiningPhilosophers {
    public static void main(String[] args) {
        int numberOfPhilosophers = 5;

        Chopstick[] chopsticks = new Chopstick[numberOfPhilosophers];
        for (int i = 0; i < numberOfPhilosophers; i++) {
            chopsticks[i] = new Chopstick(i);
        }

        List<Callable<Object>> philosopherTasks = new ArrayList<Callable<Object>>();
        for (int i = 0; i < numberOfPhilosophers; i++) {
            philosopherTasks.add(
                Executors.callable(
                    new Philosopher(
                        i,
                        i % 2 == 0, // pick up left Chopstick first if even-numbered
                        chopsticks[i],                           // first Chopstick
                        chopsticks[(i+1) % numberOfPhilosophers] // second Chopstick
                    )
                )
            );
        }

        try {
            ExecutorService executorService =
                Executors.newFixedThreadPool(numberOfPhilosophers);

            executorService.invokeAll(philosopherTasks);
            executorService.shutdown();
            executorService.awaitTermination(1, TimeUnit.MINUTES);
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
