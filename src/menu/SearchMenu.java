package menu;

import ds.DynamicArray;
import search.SearchAlgorithms;
import search.SearchResult;
import util.ConsoleUI;
import util.InputValidator;
import util.ResultLog;

import java.util.Scanner;

/**
 * Console submenu for the Searching component. It works on the same array the
 * user built in the Array menu so both components are visibly integrated.
 *
 * @author MF Sheeraz Gulzum (23da2-0589)
 */
public class SearchMenu {
    private final DynamicArray array;
    private final Scanner scanner;

    public SearchMenu(DynamicArray array, Scanner scanner) {
        this.array = array;
        this.scanner = scanner;
    }

    public void run() {
        boolean back = false;
        while (!back) {
            ConsoleUI.subHeader("SEARCHING OPERATIONS");
            System.out.println("1. Linear Search");
            System.out.println("2. Binary Search");
            System.out.println("3. Compare Linear vs Binary Search");
            System.out.println("4. Load Sorted Sample Dataset");
            System.out.println("5. Return to Main Menu");
            int choice = InputValidator.readInt(scanner, "Enter your choice: ", 1, 5);
            switch (choice) {
                case 1: linear(); break;
                case 2: binary(); break;
                case 3: compare(); break;
                case 4: loadSample(); break;
                default: back = true;
            }
        }
    }

    private void linear() {
        if (emptyWarning()) return;
        int target = InputValidator.readInt(scanner, "Enter value to search for: ");
        SearchResult result = SearchAlgorithms.linearSearch(array.rawData(), array.size(), target);
        System.out.println("  " + result);
        ResultLog.add("Search", "target " + target + " | " + result);
    }

    private void binary() {
        if (emptyWarning() || !ensureSorted()) return;
        int target = InputValidator.readInt(scanner, "Enter value to search for: ");
        SearchResult result = SearchAlgorithms.binarySearch(array.rawData(), array.size(), target);
        System.out.println("  " + result);
        ResultLog.add("Search", "target " + target + " | " + result);
    }

    private void compare() {
        if (emptyWarning() || !ensureSorted()) return;
        int target = InputValidator.readInt(scanner, "Enter value to search for: ");
        SearchResult linear = SearchAlgorithms.linearSearch(array.rawData(), array.size(), target);
        SearchResult binary = SearchAlgorithms.binarySearch(array.rawData(), array.size(), target);

        ConsoleUI.header("SEARCH COMPARISON (n = " + array.size() + ")");
        System.out.printf("%-16s %-12s %-8s %-10s%n", "Algorithm", "Result", "Steps", "Time (ns)");
        ConsoleUI.line();
        printRow(linear);
        printRow(binary);
        ConsoleUI.line();
        System.out.println("Linear search checks elements one by one: O(n).");
        System.out.println("Binary search halves the range each step: O(log n), but needs sorted data.");
        if (binary.getSteps() < linear.getSteps()) {
            System.out.println("Here binary search needed " + (linear.getSteps() - binary.getSteps())
                    + " fewer steps.");
        } else {
            System.out.println("For this target/size both need a similar number of steps; "
                    + "try a larger dataset (option 4).");
        }
        ResultLog.add("Search", "compare target " + target + " | linear " + linear.getSteps()
                + " steps vs binary " + binary.getSteps() + " steps (n=" + array.size() + ")");
    }

    private void printRow(SearchResult r) {
        System.out.printf("%-16s %-12s %-8d %-10d%n", r.getAlgorithm(),
                r.isFound() ? "idx " + r.getIndex() : "not found", r.getSteps(), r.getNanoTime());
    }

    private void loadSample() {
        int count = InputValidator.readInt(scanner, "How many elements (1-1000000)? ", 1, 1000000);
        array.fillSequential(count, 2);
        ConsoleUI.success(count + " sorted values loaded (0, 2, 4, ... " + 2 * (count - 1)
                + "). Tip: search the last value, or an odd number to see a miss.");
    }

    /** Binary search needs sorted data - offer to sort instead of failing. */
    private boolean ensureSorted() {
        if (array.isSorted()) return true;
        ConsoleUI.error("Binary search requires a sorted array, and the array is not sorted.");
        if (InputValidator.readYesNo(scanner, "Sort the array now?")) {
            array.sort();
            ConsoleUI.success("Array sorted.");
            return true;
        }
        return false;
    }

    private boolean emptyWarning() {
        if (array.isEmpty()) {
            ConsoleUI.error("The array is empty. Add values in the Array menu or load a sample dataset (option 4).");
            return true;
        }
        return false;
    }
}
