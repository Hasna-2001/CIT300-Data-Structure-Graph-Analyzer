package ds;

/**
 * CircularQueue - a fixed-capacity FIFO queue backed by a circular array.
 * Indices wrap around with the modulo operator so freed cells are reused,
 * which keeps enqueue and dequeue O(1) with no shifting.
 *
 * @author R Halidha Nashath (23da2-0535)
 */
public class CircularQueue<T> {
    private final Object[] items;
    private int front = 0;
    private int size = 0;

    public CircularQueue(int capacity) {
        if (capacity < 1) {
            throw new IllegalArgumentException("Capacity must be at least 1.");
        }
        items = new Object[capacity];
    }

    /** @return true if added, false if the queue is full (overflow) */
    public boolean enqueue(T item) {
        if (isFull()) return false;
        items[(front + size) % items.length] = item;
        size++;
        return true;
    }

    @SuppressWarnings("unchecked")
    public T dequeue() {
        if (isEmpty()) {
            throw new EmptyStructureException("Queue Underflow: cannot dequeue from an empty queue.");
        }
        T value = (T) items[front];
        items[front] = null;
        front = (front + 1) % items.length;
        size--;
        return value;
    }

    @SuppressWarnings("unchecked")
    public T peek() {
        if (isEmpty()) {
            throw new EmptyStructureException("Queue is empty: no front element.");
        }
        return (T) items[front];
    }

    public boolean isEmpty() { return size == 0; }
    public boolean isFull() { return size == items.length; }
    public int size() { return size; }
    public int capacity() { return items.length; }
    public int frontIndex() { return front; }
    public int rearIndex() { return isEmpty() ? -1 : (front + size - 1) % items.length; }

    /** Returns the queue drawn from front to rear. */
    public String display() {
        if (isEmpty()) return "(queue is empty)";
        StringBuilder sb = new StringBuilder("FRONT -> ");
        for (int i = 0; i < size; i++) {
            sb.append(items[(front + i) % items.length]).append(" ");
        }
        return sb.append("<- REAR").toString();
    }
}
