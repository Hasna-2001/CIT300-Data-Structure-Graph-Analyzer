package menu;

import ds.CircularQueue;
import ds.EmptyStructureException;
import util.ConsoleUI;
import util.InputValidator;

import java.util.Scanner;

/**
 * Console submenu for the Queue component.
 *
 * @author R Halidha Nashath (23da2-0535)
 */
public class QueueMenu {
    private final CircularQueue<Integer> queue = new CircularQueue<>(8);
    private final Scanner scanner;

    public QueueMenu(Scanner scanner) {
        this.scanner = scanner;
    }

    public void run() {
        boolean back = false;
        while (!back) {
            ConsoleUI.subHeader("QUEUE OPERATIONS");
            System.out.println("1. Enqueue");
            System.out.println("2. Dequeue");
            System.out.println("3. Peek / Front");
            System.out.println("4. Display Queue");
            System.out.println("5. Queue Status");
            System.out.println("6. Return to Main Menu");
            int choice = InputValidator.readInt(scanner, "Enter your choice: ", 1, 6);
            try {
                switch (choice) {
                    case 1: enqueue(); break;
                    case 2: ConsoleUI.success("Dequeued: " + queue.dequeue()); break;
                    case 3: ConsoleUI.success("Front element: " + queue.peek()); break;
                    case 4: System.out.println("  " + queue.display()); break;
                    case 5: status(); break;
                    default: back = true;
                }
            } catch (EmptyStructureException e) {
                ConsoleUI.error(e.getMessage());
            }
        }
    }

    private void enqueue() {
        int value = InputValidator.readInt(scanner, "Enter value to enqueue: ");
        if (queue.enqueue(value)) {
            ConsoleUI.success(value + " enqueued.");
        } else {
            ConsoleUI.error("Queue Overflow: the queue is full (capacity " + queue.capacity() + ").");
        }
    }

    private void status() {
        System.out.println("  Size: " + queue.size() + " / " + queue.capacity()
                + " | Empty: " + queue.isEmpty() + " | Full: " + queue.isFull());
        System.out.println("  Front index: " + queue.frontIndex() + " | Rear index: " + queue.rearIndex()
                + " (circular: indices wrap around)");
    }
}
