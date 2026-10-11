package ds;

import search.SearchResult;

/**
 * SinglyLinkedList - nodes linked by 'next' references, with head and tail pointers.
 *
 * Complexity: insert at head O(1) | insert at tail O(1) (tail pointer)
 *             insert/delete at position O(n) | search O(n)
 *
 * @author M Hathiqu Ahamath (23da2-0526)
 */
public class SinglyLinkedList<T> {

    private static class Node<E> {
        E data;
        Node<E> next;

        Node(E data) { this.data = data; }
    }

    private Node<T> head;
    private Node<T> tail;
    private int size;

    public int size() { return size; }
    public boolean isEmpty() { return size == 0; }

    public void insertAtHead(T value) {
        Node<T> node = new Node<>(value);
        node.next = head;
        head = node;
        if (tail == null) tail = node;
        size++;
    }

    public void insertAtTail(T value) {
        Node<T> node = new Node<>(value);
        if (tail == null) {
            head = tail = node;
        } else {
            tail.next = node;
            tail = node;
        }
        size++;
    }

    /** Inserts at a 0-based position (0 = head, size = tail). */
    public void insertAt(int position, T value) {
        if (position < 0 || position > size) {
            throw new IndexOutOfBoundsException("Position must be between 0 and " + size + ".");
        }
        if (position == 0) {
            insertAtHead(value);
        } else if (position == size) {
            insertAtTail(value);
        } else {
            Node<T> previous = nodeAt(position - 1);
            Node<T> node = new Node<>(value);
            node.next = previous.next;
            previous.next = node;
            size++;
        }
    }

    /** Deletes the first node holding value. @return true if a node was deleted */
    public boolean deleteByValue(T value) {
        Node<T> previous = null;
        Node<T> current = head;
        while (current != null) {
            if (current.data.equals(value)) {
                unlink(previous, current);
                return true;
            }
            previous = current;
            current = current.next;
        }
        return false;
    }

    /** Deletes the node at a 0-based position and returns its value. */
    public T deleteAt(int position) {
        if (isEmpty()) {
            throw new EmptyStructureException("Linked list is empty: nothing to delete.");
        }
        if (position < 0 || position >= size) {
            throw new IndexOutOfBoundsException("Position must be between 0 and " + (size - 1) + ".");
        }
        Node<T> previous = position == 0 ? null : nodeAt(position - 1);
        Node<T> target = position == 0 ? head : previous.next;
        unlink(previous, target);
        return target.data;
    }

    /** Linear search that counts one step per node visited. */
    public SearchResult search(T target) {
        long start = System.nanoTime();
        long steps = 0;
        int index = 0;
        int foundAt = -1;
        for (Node<T> current = head; current != null; current = current.next) {
            steps++;
            if (current.data.equals(target)) {
                foundAt = index;
                break;
            }
            index++;
        }
        return new SearchResult("Linked List Search", foundAt >= 0, foundAt, steps,
                System.nanoTime() - start);
    }

    /** Reverses the list in place by flipping every 'next' pointer - O(n). */
    public void reverse() {
        Node<T> previous = null;
        Node<T> current = head;
        tail = head;
        while (current != null) {
            Node<T> next = current.next;
            current.next = previous;
            previous = current;
            current = next;
        }
        head = previous;
    }

    public void clear() {
        head = tail = null;
        size = 0;
    }

    public String display() {
        if (isEmpty()) return "(list is empty)";
        StringBuilder sb = new StringBuilder("HEAD -> ");
        for (Node<T> current = head; current != null; current = current.next) {
            sb.append(current.data).append(" -> ");
        }
        return sb.append("null").toString();
    }

    private Node<T> nodeAt(int index) {
        Node<T> current = head;
        for (int i = 0; i < index; i++) current = current.next;
        return current;
    }

    private void unlink(Node<T> previous, Node<T> target) {
        if (previous == null) {
            head = target.next;
        } else {
            previous.next = target.next;
        }
        if (target == tail) tail = previous;
        size--;
    }
}
