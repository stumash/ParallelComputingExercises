package ca.mcgill.ecse420.a2;

public class BakeryLock implements Lock {
	
    private final int n;
	private volatile boolean[] flag;
	private volatile int[] label;
	
	public BakeryLock(int n) {
        this.n = n;
		flag = new boolean[n];
		label = new int[n];
	}

	@Override
	public void lock() {
		int me = ThreadIdUtil.getId();
		flag[me] = true;
		label[me] = maxLabel() + 1;
		
		for (int k = 0; k < n; ++k) {
			if (k == me) continue;
            while (flag[k] && (label[k] < label[me] || (label[k] == label[me] && k < me) ));
		}
		
	}

	@Override
	public void unlock() {
		int me = ThreadIdUtil.getId();
		flag[me] = false;
	}
	
	private int maxLabel() {
		int max = -1;
		
		for (int l : label) {
			if (l > max) {
				max = l;
			}
		}
		
		return max;
	}
}
