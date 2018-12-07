package ca.mcgill.ecse420.a3.FineGrainedSetMembership;

public class FineGrainedSet<T> implements Set<T>
{
    private Node<T> head;

    public FineGrainedSet() {
        // head and tail sentinel nodes. useMaxKey=true
        this.head = new Node<T>(null, true);
        head.next = new Node<T>(null, true);
    }

    public boolean add(T item) {
        int key = item.hashCode();
        head.lock();
        Node<T> pred = head;
        try {
            Node<T> curr = pred.next;
            curr.lock();
            try {
                while (curr.getKey() < key && !curr.item.equals(item)) {
                    pred.unlock();
                    pred = curr;
                    curr = curr.next;
                    curr.lock();
                }
                if (curr.getKey() == key) {
                    return false;
                }
                Node<T> newNode = new Node<T>(item);
                newNode.next = curr;
                pred.next = newNode;
                return true;
            }
            finally {
                curr.unlock();
            }
        }
        finally {
            pred.unlock();
        }
    }

    public boolean remove(T item) {
        int key = item.hashCode();
        head.lock();
        Node<T> pred = head;
        try {
            Node<T> curr = pred.next;
            curr.lock();
            try {
                while (curr.getKey() < key && !curr.item.equals(item)) {
                    pred.unlock();
                    pred = curr;
                    curr = curr.next;
                    curr.lock();
                }
                if (curr.getKey() == key) {
                    pred.next = curr.next;
                    return true;
                }
                return false;
            }
            finally {
                curr.unlock();
            }
        }
        finally {
            pred.unlock();
        }
    }

    public boolean contains(T item) {
        int key = item.hashCode();
        head.lock();
        Node<T> pred = head;
        try {
            pred = head;
            Node<T> curr = pred.next;
            curr.lock();
            try {
                while (curr.getKey() < key && !curr.item.equals(item)) {
                    pred.unlock();
                    pred = curr;
                    curr = curr.next;
                    curr.lock();
                }
                return curr.getKey() == key;
            } finally {
                curr.unlock();
            }
        } finally {
            pred.unlock();
        }
    }
}
