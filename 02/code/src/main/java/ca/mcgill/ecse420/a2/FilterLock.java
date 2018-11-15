package ca.mcgill.ecse420.a2;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;

public class FilterLock implements Lock {

    private final int n;
    private volatile int[] level;
    private volatile int[] victim;

    public FilterLock(int n) {
        this.n = n;
        level = new int[n];
        victim = new int[n];
    }

    @Override
    public void lock() {
        int me = ThreadIdUtil.getId();

        for (int i = 1; i < n; ++i) {
            level[me] = i;
            victim[i] = me;

            for (int k = 0; k < n; ++k) {
                if (k != me) {
                    while (level[k] >= i && victim[i] == me);
                }
            }
        }
    }

    @Override
    public void unlock() {
        int me = ThreadIdUtil.getId();
        level[me] = 0;
    }
}
