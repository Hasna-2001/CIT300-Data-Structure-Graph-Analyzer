package menu;

import ds.DynamicArray;
import search.SearchAlgorithms;
import search.SearchResult;
import util.ConsoleUI;
import util.InputValidator;
import util.ResultLog;

import java.util.Scanner;

/**
 * Console submenu for the Array component.
 *
 * @author MF Sheeraz Gulzum (23da2-0589)
 */
public class ArrayMenu {
    private final DynamicArray array;
    private final Scanner scanner;

    public ArrayMenu(DynamicArray array, Scanner scanner) {
        this.array = array;
        this.scanner = scanner;
    }

    public void run() {
        boolean back = false;
        while (!back) {
            ConsoleUI.subHeader("ARRAY OPERATIONS");
            System.out.println("1. Insert at End");
            System.out.println("2. Insert at Index");
            System.out.println("3. Delete by Index");
            System.out.println("4. Delete by Value");
            System.out.println("5. Search for a Value");
            System.out.println("6. Display Array");
            System.out.println("7. Sort Array (ascending)");
            System.out.println("8. Fill with Random Numbers");
            System.out.println("9. Clear Array");
            System.out.println("10. Return to Main Menu");
            int choice = InputValidator.readInt(scanner, "Enter your choice: ", 1, 10);
            try {
                switch (choice) {
                    case 1: insertAtEnd(); break;
                    case 2: insertAtIndex(); break;
                    case 3: deleteByIndex(); break;
                    case 4: deleteByValue(); break;
                    case 5: search(); break;
                    case 6: display(); break;
                    case 7: sort(); break;
                    case 8: fillRandom(); break;
                    case 9: array.clear(); ConsoleUI.success("Array cleared."); break;
                    default: back = true;
                }
            } catch (IndexOutOfBoundsException e) {
                ConsoleUI.error(e.getMessage());
            }
        }
    }

    private void insertAtEnd() {
        int value = InputValidator.readInt(scanner, "Enter value to insert: ");
        array.insertAtEnd(value);
        ConsoleUI.success(value + " inserted at index " + (array.size() - 1) + ".");
    }

    private void insertAtIndex() {
        int index = InputValidator.readInt(scanner, "Enter index (0 to " + array.size() + "): ");
        int value = InputValidator.readInt(scanner, "Enter value to insert: ");
        int shifts = array.insertAt(index, value);
        ConsoleUI.success(value + " inserted at index " + index + " (" + shifts + " elements shifted right).");
    }

    private void deleteByIndex() {
        if (isEmptyWarning()) return;
        int index = InputValidator.readInt(scanner, "Enter index to delete (0 to " + (array.size() - 1) + "): ");
        int removed = array.deleteAt(index);
        ConsoleUI.success("Deleted " + removed + " from index " + index + ".");
    }

    private void deleteByValue() {
        if (isEmptyWarning()) return;
        int value = InputValidator.readInt(scanner, "Enter value to delete: ");
        int index = array.deleteByValue(value);
        if (index >= 0) ConsoleUI.success("Deleted " + value + " (was at index " + index + ").");
        else ConsoleUI.error(value + " was not found in the array.");
    }

    private void search() {
        if (isEmptyWarning()) return;
        int target = InputValidator.readInt(scanner, "Enter value to search for: ");
        SearchResult result = SearchAlgorithms.linearSearch(array.rawData(), array.size(), target);
        System.out.println("  " + result);
        ResultLog.add("Array", "search " + target + " -> " + result);
    }

    private void display() {
        System.out.println("  Array (" + array.size() + " elements, capacity " + array.capacity() + "):");
        System.out.println("  " + array);
    }

    private void sort() {
        if (isEmptyWarning()) return;
        array.sort();
        ConsoleUI.success("Array sorted: " + array);
    }

    private void fillRandom() {
        int count = InputValidator.readInt(scanner, "How many random numbers (1-1000000)? ", 1, 1000000);
        array.fillRandom(count, 1000);
        ConsoleUI.success(count + " random numbers (0-999) loaded.");
        if (count <= 30) display();
    }

    private boolean isEmptyWarning() {
        if (array.isEmpty()) {
            ConsoleUI.error("The array is empty. Insert some values first.");
            return true;
        }
        return false;
    }
}
