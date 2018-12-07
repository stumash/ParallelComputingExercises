package ca.mcgill.ecse420.a3.FineGrainedSetMembership;

public interface Set<T>
{
    /**
     * Add an element to the set.
     *
     * Returns true if the element was not already in the set, else returns false.
     */
    public boolean add(T item);

    /**
     * Remove an element from the set.
     *
     * Returns true if the element was actually in the set, else returns false.
     */
    public boolean remove(T item);

    /**
     * Check if an element is in the set.
     *
     * Returns true if the element is in the set, else returns false.
     */
    public boolean contains(T item);
}
