package ds;

import java.util.Arrays;
import java.util.Random;

/**
 * DynamicArray - a resizable array of ints built on a plain int[].
 * When the internal array is full its capacity is doubled (amortised O(1) append).
 *
 * Complexity: get O(1) | insert at end O(1) amortised | insert/delete at index O(n)
 *
 * @author MF Sheeraz Gulzum (23da2-0589)
 */
public class DynamicArray {
    private int[] data;
    private int size;

    public DynamicArray(int initialCapacity) {
        if (initialCapacity < 1) {
            throw new IllegalArgumentException("Capacity must be at least 1.");
        }
        data = new int[initialCapacity];
    }

    public int size() { return size; }

    public int capacity() { return data.length; }

    public boolean isEmpty() { return size == 0; }

    public int get(int index) {
        checkIndex(index);
        return data[index];
    }

    /** Appends a value at the end. */
    public void insertAtEnd(int value) {
        ensureCapacity();
        data[size++] = value;
    }

    /**
     * Inserts a value at the given index, shifting later elements right.
     * @return number of elements that had to be shifted (the "steps" of this operation)
     */
    public int insertAt(int index, int value) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index must be between 0 and " + size + ".");
        }
        ensureCapacity();
        int shifts = 0;
        for (int i = size; i > index; i--) {
            data[i] = data[i - 1];
            shifts++;
        }
        data[index] = value;
        size++;
        return shifts;
    }

    /** Deletes the element at index, shifting later elements left. Returns the removed value. */
    public int deleteAt(int index) {
        checkIndex(index);
        int removed = data[index];
        for (int i = index; i < size - 1; i++) {
            data[i] = data[i + 1];
        }
        size--;
        return removed;
    }

    /** Deletes the first occurrence of value. Returns its old index, or -1 if not found. */
    public int deleteByValue(int value) {
        for (int i = 0; i < size; i++) {
            if (data[i] == value) {
                deleteAt(i);
                return i;
            }
        }
        return -1;
    }

    /** Sorts ascending (uses the JDK's O(n log n) dual-pivot quicksort). */
    public void sort() { Arrays.sort(data, 0, size); }

    public boolean isSorted() {
        for (int i = 1; i < size; i++) {
            if (data[i - 1] > data[i]) return false;
        }
        return true;
    }

    /** Replaces content with {@code count} random values in [0, bound). */
    public void fillRandom(int count, int bound) {
        clear();
        Random random = new Random();
        for (int i = 0; i < count; i++) {
            insertAtEnd(random.nextInt(bound));
        }
    }

    /** Replaces content with the sorted sequence 0, step, 2*step, ... (count items). */
    public void fillSequential(int count, int step) {
        clear();
        for (int i = 0; i < count; i++) {
            insertAtEnd(i * step);
        }
    }

    public void clear() { size = 0; }

    /** Direct access to the backing array (only the first size() cells are valid). */
    public int[] rawData() { return data; }

    public int[] toArray() { return Arrays.copyOf(data, size); }

    @Override
    public String toString() {
        if (size == 0) return "[]";
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            if (i > 0) sb.append(", ");
            sb.append(data[i]);
        }
        return sb.append("]").toString();
    }

    private void ensureCapacity() {
        if (size == data.length) {
            data = Arrays.copyOf(data, data.length * 2);
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(
                    "Index " + index + " is out of range (valid: 0 to " + (size - 1) + ").");
        }
    }
}
