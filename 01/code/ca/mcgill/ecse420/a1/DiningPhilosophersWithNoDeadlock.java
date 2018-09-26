package ca.mcgill.ecse420.a1;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

public class DiningPhilosophersWithNoDeadlock {
    
    public static void main(String[] args) {
      int numberOfPhilosophers = 10;
      Philosopher[] philosophers = new Philosopher[numberOfPhilosophers];
      Object[] chopsticks = new Object[numberOfPhilosophers];
      
      ExecutorService executorService = null;
      
      try {
//      Place the chopstick on table   
        for (int i = 0; i < numberOfPhilosophers; i++) {
          chopsticks[i] = new Object(i);
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
    
    //  This object represents a chopstick
    public static class Object {
      ReentrantLock lock = new ReentrantLock();
      private final int id;
  
      public Object(int id) {
        this.id = id;
      }
      
      //return true if chopstick is free and false otherwise
      public boolean pickUp(Philosopher philosopher, String where) throws InterruptedException {
//        TryLock() acquires the lock only if it is free at the time of invocation.
        if (lock.tryLock()) {
//          System.out.println("Philosopher - "+ philosopher.id + " picked up " + where + " Chopstick " + id);
          return true;
        }
//        System.out.println("Philosopher - "+ philosopher.id + " COULD NOT picked up " + where + " Chopstick " + id);
        return false;
      }
      public void putDown(Philosopher philosopher, String name) {
        lock.unlock();
//        System.out.println("Philosopher - "+ philosopher.id + " put down " + name + " Chopstick " + id);
      }
    }
    
    public static class Philosopher implements Runnable {
      private final int id;
      private final Object leftChopStick;
      private final Object rightChopStick;

      /**
       * @param id Philosopher number
       *
       * @param chopsticks
       * @param chopsticks2
       */
      public Philosopher(int id, Object chopsticks, Object chopsticks2) {
        this.id = id;
        this.leftChopStick = chopsticks;
        this.rightChopStick = chopsticks2;
      }
        @Override
        public void run() {
          try {
            while(true) {
              if((this.id & 1) == 0 ) {
//              Philosopher with an even id
               // Think for a while
               think();
               if (rightChopStick.pickUp(this, "right")) {
                 if (leftChopStick.pickUp(this, "left")) {
                   eat();
                   leftChopStick.putDown(this, "left");
                 }
//                Resource allocation, drop the rChopStick even if philosopher was not able to eat giving the possibility for others to pick it up
                 rightChopStick.putDown(this, "right");
                 }
               }else{
//              Philosopher with an old id
              // Think for a while
              think();
              if (leftChopStick.pickUp(this, "left")) {
                if (rightChopStick.pickUp(this, "right")) {
                  eat();
                  rightChopStick.putDown(this, "right");
                }
//                Resource allocation, drop the rChopStick even if philosopher was not able to eat giving the possibility for others to pick it up
                leftChopStick.putDown(this, "left");
                }
              }
              }
            }catch (Exception e) {
            // Catch the exception outside the loop.
            e.printStackTrace();
          } 
        }
        //The philosopher is spending a random period to think
        private void think() throws InterruptedException {
//          System.out.println("Philosopher - " +id +" is thinking");
          Thread.sleep(new Random().nextInt(500));
        }
        //The philosopher is spending a random period to eat
        private void eat() throws InterruptedException {
          System.out.println("Philosopher - " +id +" is eating");
          Thread.sleep(new Random().nextInt(500));
        }

    }

}
