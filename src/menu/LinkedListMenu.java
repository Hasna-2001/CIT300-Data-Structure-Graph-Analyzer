package menu;

import ds.EmptyStructureException;
import ds.SinglyLinkedList;
import search.SearchResult;
import util.ConsoleUI;
import util.InputValidator;
import util.ResultLog;

import java.util.Scanner;

/**
 * Console submenu for the Linked List component.
 *
 * @author M Hathiqu Ahamath (23da2-0526)
 */
public class LinkedListMenu {
    private final SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
    private final Scanner scanner;

    public LinkedListMenu(Scanner scanner) {
        this.scanner = scanner;
    }

    public void run() {
        boolean back = false;
        while (!back) {
            ConsoleUI.subHeader("LINKED LIST OPERATIONS");
            System.out.println("1. Insert at Head");
            System.out.println("2. Insert at Tail");
            System.out.println("3. Insert at Position");
            System.out.println("4. Delete by Value");
            System.out.println("5. Delete at Position");
            System.out.println("6. Search for a Value");
            System.out.println("7. Display List");
            System.out.println("8. Reverse List");
            System.out.println("9. Return to Main Menu");
            int choice = InputValidator.readInt(scanner, "Enter your choice: ", 1, 9);
            try {
                switch (choice) {
                    case 1: insertHead(); break;
                    case 2: insertTail(); break;
                    case 3: insertPosition(); break;
                    case 4: deleteValue(); break;
                    case 5: deletePosition(); break;
                    case 6: search(); break;
                    case 7: System.out.println("  " + list.display() + "  (size " + list.size() + ")"); break;
                    case 8: reverse(); break;
                    default: back = true;
                }
            } catch (EmptyStructureException | IndexOutOfBoundsException e) {
                ConsoleUI.error(e.getMessage());
            }
        }
    }

    private void insertHead() {
        int value = InputValidator.readInt(scanner, "Enter value: ");
        list.insertAtHead(value);
        ConsoleUI.success(value + " inserted at head.");
    }

    private void insertTail() {
        int value = InputValidator.readInt(scanner, "Enter value: ");
        list.insertAtTail(value);
        ConsoleUI.success(value + " inserted at tail.");
    }

    private void insertPosition() {
        int position = InputValidator.readInt(scanner, "Enter position (0 to " + list.size() + "): ");
        int value = InputValidator.readInt(scanner, "Enter value: ");
        list.insertAt(position, value);
        ConsoleUI.success(value + " inserted at position " + position + ".");
    }

    private void deleteValue() {
        if (emptyWarning()) return;
        int value = InputValidator.readInt(scanner, "Enter value to delete: ");
        if (list.deleteByValue(value)) ConsoleUI.success(value + " deleted.");
        else ConsoleUI.error(value + " was not found in the list.");
    }

    private void deletePosition() {
        if (emptyWarning()) return;
        int position = InputValidator.readInt(scanner, "Enter position (0 to " + (list.size() - 1) + "): ");
        ConsoleUI.success("Deleted " + list.deleteAt(position) + " from position " + position + ".");
    }

    private void search() {
        if (emptyWarning()) return;
        int value = InputValidator.readInt(scanner, "Enter value to search for: ");
        SearchResult result = list.search(value);
        System.out.println("  " + result);
        ResultLog.add("LinkedList", "search " + value + " -> " + result);
    }

    private void reverse() {
        if (emptyWarning()) return;
        list.reverse();
        ConsoleUI.success("List reversed: " + list.display());
    }

    private boolean emptyWarning() {
        if (list.isEmpty()) {
            ConsoleUI.error("The linked list is empty. Insert some values first.");
            return true;
        }
        return false;
    }
}
