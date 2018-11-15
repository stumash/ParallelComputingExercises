package ca.mcgill.ecse420.a2;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class FilterLock implements Lock {
	
	private AtomicInteger[] level;
	private AtomicInteger[] victim;
	
	public FilterLock(int n) {
		
		level = new AtomicInteger[n];
		victim = new AtomicInteger[n];
		
		for (int i = 0; i < n; ++i) {
			// Default initial value for AtomicInteger is 0
			level[i] = new AtomicInteger();
			victim[i] = new AtomicInteger();
		}
	}

	@Override
	public void lock() {
		int me = ThreadIdUtil.getId();
		
		for (int i = 1; i < level.length; ++i) {
			level[me].set(i);
			victim[i].set(me);
			
			// Spin while conflicts exist
			// Iterate over all possible k's
			for (int k = 0; k < level.length; ++k) {
				if (k != me) {
					while (level[k].get() >= i && victim[i].get() == me) {
						// Spin
					}
				}
			}
		}
	}

	@Override
	public void lockInterruptibly() throws InterruptedException {
	}

	@Override
	public Condition newCondition() {
		return null;
	}

	@Override
	public boolean tryLock() {
		return false;
	}

	@Override
	public boolean tryLock(long arg0, TimeUnit arg1) throws InterruptedException {
		return false;
	}

	@Override
	public void unlock() {
		int me = ThreadIdUtil.getId();
		level[me].set(0);
	}
}
