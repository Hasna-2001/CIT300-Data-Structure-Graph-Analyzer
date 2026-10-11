import ds.DynamicArray;
import graph.Graph;
import menu.ArrayMenu;
import menu.GraphMenu;
import menu.LinkedListMenu;
import menu.PerformanceMenu;
import menu.QueueMenu;
import menu.SearchMenu;
import menu.StackMenu;
import util.ConsoleUI;
import util.InputValidator;
import util.ResultLog;

import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * CIT300 Graded Practical Assignment 2 - Data Structure and Graph Performance Analyzer.
 * Main entry point: builds every module once and wires them into one menu-driven system.
 *
 * @author MS Faathima Hasna (23da2-1093) - system integration
 */
public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // One shared array and one shared graph: the Array, Searching and Performance menus
        // all work on the same array; the Graph and Performance menus share the same graph.
        DynamicArray array = new DynamicArray(10);
        Graph graph = new Graph(false);

        ArrayMenu arrayMenu = new ArrayMenu(array, scanner);
        StackMenu stackMenu = new StackMenu(scanner);
        QueueMenu queueMenu = new QueueMenu(scanner);
        LinkedListMenu linkedListMenu = new LinkedListMenu(scanner);
        SearchMenu searchMenu = new SearchMenu(array, scanner);
        GraphMenu graphMenu = new GraphMenu(graph, scanner);
        PerformanceMenu performanceMenu = new PerformanceMenu(array, graph, scanner);

        try {
            boolean running = true;
            while (running) {
                printMainMenu();
                int choice = InputValidator.readInt(scanner, "Enter your choice: ", 1, 9);
                switch (choice) {
                    case 1: arrayMenu.run(); break;
                    case 2: stackMenu.run(); break;
                    case 3: queueMenu.run(); break;
                    case 4: linkedListMenu.run(); break;
                    case 5: searchMenu.run(); break;
                    case 6: graphMenu.run(); break;
                    case 7: performanceMenu.run(); break;
                    case 8: ResultLog.display(); break;
                    default: running = false;
                }
            }
            System.out.println();
            System.out.println("Thank you for using the Data Structure & Graph Analyzer. Goodbye!");
        } catch (NoSuchElementException e) {
            System.out.println();
            System.out.println("Input closed. Exiting the program.");
        }
    }

    private static void printMainMenu() {
        ConsoleUI.header("DATA STRUCTURE & GRAPH ANALYZER");
        System.out.println("1. Array Operations");
        System.out.println("2. Stack Operations");
        System.out.println("3. Queue Operations");
        System.out.println("4. Linked List Operations");
        System.out.println("5. Searching Operations");
        System.out.println("6. Graph Operations");
        System.out.println("7. Performance Comparison");
        System.out.println("8. Display All Results");
        System.out.println("9. Exit");
    }
}
