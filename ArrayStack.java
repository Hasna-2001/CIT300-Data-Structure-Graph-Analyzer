package ds;

/**
 * ArrayStack - a fixed-capacity LIFO stack backed by an array.
 * push / pop / peek are all O(1).
 * Pop or peek on an empty stack throws EmptyStructureException (stack underflow);
 * push on a full stack returns false (stack overflow).
 *
 * @author R Halidha Nashath (23da2-0535)
 */
public class ArrayStack<T> {
    private final Object[] items;
    private int top = -1;

    public ArrayStack(int capacity) {
        if (capacity < 1) {
            throw new IllegalArgumentException("Capacity must be at least 1.");
        }
        items = new Object[capacity];
    }

    /** @return true if pushed, false if the stack is full (overflow) */
    public boolean push(T item) {
        if (isFull()) return false;
        items[++top] = item;
        return true;
    }

    @SuppressWarnings("unchecked")
    public T pop() {
        if (isEmpty()) {
            throw new EmptyStructureException("Stack Underflow: cannot pop from an empty stack.");
        }
        T value = (T) items[top];
        items[top--] = null;        // help garbage collection
        return value;
    }

    @SuppressWarnings("unchecked")
    public T peek() {
        if (isEmpty()) {
            throw new EmptyStructureException("Stack is empty: nothing to peek.");
        }
        return (T) items[top];
    }

    public boolean isEmpty() { return top == -1; }
    public boolean isFull() { return top == items.length - 1; }
    public int size() { return top + 1; }
    public int capacity() { return items.length; }

    /** Returns the stack drawn from top to bottom. */
    public String display() {
        if (isEmpty()) return "(stack is empty)";
        StringBuilder sb = new StringBuilder("TOP -> ");
        for (int i = top; i >= 0; i--) {
            sb.append("[").append(items[i]).append("] ");
        }
        return sb.append("<- BOTTOM").toString();
    }
}
