package ca.mcgill.ecse420.a2;

public class FilterTest {

    private static final int WAIT_FOR_RACE_CONDITION = 5000;
    private static final int INITIAL_INT_VALUE = 0;
    private static final int FINAL_INT_VALUE = 2;

    public static void main(String... args) {
        Lock lock = new BakeryLock(2);
        testLockTwoThreads(lock);

        ThreadIdUtil.clear();

        lock = new FilterLock(2);
        testLockTwoThreads(lock);
    }

    /**
     * Tests a lock for mutual exclusion with two threads.
     * @param lockUnderTest The lock to be tested.
     * @return True or false whether the test succeeded.
     */
    public static boolean testLockTwoThreads(Lock lockUnderTest) {
        // Shared Object
        SharedInteger integer = new SharedInteger(INITIAL_INT_VALUE);

        System.out.println("[testLockTwoThreads] Testing: " + lockUnderTest.getClass().getName());

        Runnable r1 = new Runnable() {
            @Override
            public void run() {
                lockUnderTest.lock();
                int localInt = integer.get();

                try {
                    Thread.sleep(WAIT_FOR_RACE_CONDITION);
                } catch (InterruptedException e) {
                    System.out.println("[testLockTwoThreads] Wait interrupted. (Runnable 1)");
                    e.printStackTrace();
                }

                integer.set(++localInt);
                lockUnderTest.unlock();
            }
        };

        Runnable r2 = new Runnable() {
            @Override
            public void run() {
                lockUnderTest.lock();
                int localInt = integer.get();

                try {
                    Thread.sleep(WAIT_FOR_RACE_CONDITION);
                } catch (InterruptedException e) {
                    System.out.println("[testLockTwoThreads] Wait interrupted. (Runnable 2)");
                    e.printStackTrace();
                }

                integer.set(++localInt);
                lockUnderTest.unlock();
            }
        };

        Thread t1 = new Thread(r1);
        Thread t2 = new Thread(r2);

        t1.start();

        try {
            Thread.sleep(50);
        } catch (InterruptedException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            System.out.println("[testLockTwoThreads] Join interrupted. (Parent thread)");
            e.printStackTrace();
        }

        System.out.println("[testLockTwoThreads] Expected: 2, Actual: " + integer.get());

        if (integer.get() == FINAL_INT_VALUE) {
            System.out.println("[testLockTwoThreads] Test Pass");
            return true;
        }

        System.out.println("[testLockTwoThreads] Test Fail");
        return false;
    }
}
