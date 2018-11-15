package ca.mcgill.ecse420.a2;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This class is used to assign IDs to threads
 */
public class ThreadIdUtil {
    private static Map<Long, Integer> threadIdMap = new ConcurrentHashMap<Long, Integer>();

    /**
     * Get current thread 'ID'.
     * @return Current thread ID.
     */
    public static int getId() {
        long realThreadId = Thread.currentThread().getId();

        if (!threadIdMap.containsKey(realThreadId)) {
            threadIdMap.put(realThreadId, threadIdMap.size());
        }

        return threadIdMap.get(realThreadId);
    }

    /**
     * Clears the thread map so that the class can be reused in a fresh state.
     */
    public static void clear() {
        threadIdMap.clear();
    }
}
