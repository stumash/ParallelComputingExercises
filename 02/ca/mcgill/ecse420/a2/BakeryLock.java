package ca.mcgill.ecse420.a2;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class BakeryLock implements Lock {
	
	private AtomicBoolean[] flag;
	private AtomicInteger[] label;
	
	public BakeryLock(int n) {
		flag = new AtomicBoolean[n];
		label = new AtomicInteger[n];
		
		for (int i = 0; i < flag.length; ++i) {
			// Default AtomicBoolean value: false
			// Default AtomicInteger value: 0
			flag[i] = new AtomicBoolean();
			label[i] = new AtomicInteger();
		}
	}

	@Override
	public void lock() {
		int me = ThreadIdUtil.getId();
		flag[me].set(true);
		label[me].set(maxLabel() + 1);
		
		for (int k = 0; k < flag.length; ++k) {
			if (k != me) {
				while (flag[k].get() && (label[k].get() < label[me].get() ||
						(label[k].get() == label[me].get() && k < me) )) {
					// Spin
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
		flag[me].set(false);
	}
	
	private int maxLabel() {
		int max = -1;
		
		for (AtomicInteger l : label) {
			if (l.get() > max) {
				max = l.get();
			}
		}
		
		return max;
	}

}
