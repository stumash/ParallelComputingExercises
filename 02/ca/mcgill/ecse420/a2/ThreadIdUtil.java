package ca.mcgill.ecse420.a2;

import java.util.HashMap;

/**
 * This class is used to assign IDs to threads
 * 
 *
 */
public class ThreadIdUtil {
	private static HashMap<Long, Integer> threadIdMap;
	
	/**
	 * This serves as a "static constructor". It initializes the threadIdMap HashMap
	 * when the class is loaded.
	 */
	static {
		ThreadIdUtil.threadIdMap = new HashMap<Long, Integer>();
	}
	
	/**
	 * Register the current thread. A 0-indexed thread ID will be assigned to it.
	 * This must be called by all participant threads before getId() is called.
	 */
	public static void register() {
		long myId = Thread.currentThread().getId();
		ThreadIdUtil.threadIdMap.put(myId, ThreadIdUtil.threadIdMap.size());
	}
	
	/**
	 * Get current thread ID.
	 * @return Current thread ID.
	 */
	public static int getId() {
		return ThreadIdUtil.threadIdMap.get(Thread.currentThread().getId());
	}
	
	/**
	 * Clears the thread map so that the class can be reused in a fresh state.
	 */
	public static void clear() {
		ThreadIdUtil.threadIdMap.clear();
	}
}
