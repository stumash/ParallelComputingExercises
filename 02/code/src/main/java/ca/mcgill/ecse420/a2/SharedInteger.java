package ca.mcgill.ecse420.a2;

/**
 * This is an implementation of a integer class that can be passed around
 * by reference.
 * 
 *
 */
public class SharedInteger {
	private int value;
	
	/**
	 * Default constructor.
	 * @param n Initial value for the integer.
	 */
	public SharedInteger(int n) {
		value = n;
	}
	
	/**
	 * Sets the integer value.
	 * @param n Value to be assigned.
	 */
	public void set(int n) {
		value = n;
	}
	
	/**
	 * Gets the integer value.
	 * @return The integer value.
	 */
	public int get() {
		return value;
	}
}
