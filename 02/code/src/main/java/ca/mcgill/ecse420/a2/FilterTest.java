package ca.mcgill.ecse420.a2;

public class FilterTest {

    private static final int WAIT_FOR_RACE_CONDITION = 5000;
    private static final int WAIT_FOR_OTHER_TO_LOCK = 1000;
    private static final int INITIAL_INT_VALUE = 0;
    private static final int FINAL_INT_VALUE = 2;

    private static volatile int sharedInt;

    public static void main(String[] args) {
        Lock lock = new BakeryLock(2);
        testLockTwoThreads(lock);

        lock = new FilterLock(2);
        testLockTwoThreads(lock);
    }

    /**
     * Tests a lock for mutual exclusion with two threads.
     * @param lockUnderTest The lock to be tested.
     */
    public static void testLockTwoThreads(Lock lockUnderTest) {
        ThreadIdUtil.clear();

        sharedInt = INITIAL_INT_VALUE;

        log(lockUnderTest.getClass().getName());

        Runnable r = () -> {
            lockUnderTest.lock();
                int localInt = sharedInt;

                try {
                    Thread.sleep(WAIT_FOR_RACE_CONDITION);
                } catch (InterruptedException e) {
                    log("Wait interrupted. (Runnable 1)");
                    e.printStackTrace();
                }

                sharedInt = localInt+1;
            lockUnderTest.unlock();
        };

        Thread t1 = new Thread(r);
        Thread t2 = new Thread(r);

        t1.start();

        try {
            Thread.sleep(WAIT_FOR_OTHER_TO_LOCK);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }

        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            log("Join interrupted. (Parent thread)");
            e.printStackTrace();
        }

        log("Expected: 2, Actual: " + sharedInt);

        if (sharedInt == FINAL_INT_VALUE) {
            log("Test Pass");
        } else {
            log("Test Fail");
        }
    }

    private static void log(Object o) {
        System.out.println("[testLockTwoThreads] " + o.toString());
    }
}
