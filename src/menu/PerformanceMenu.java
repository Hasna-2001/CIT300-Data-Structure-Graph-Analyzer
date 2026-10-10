package menu;

import analysis.PerformanceAnalyzer;
import ds.DynamicArray;
import graph.Graph;
import util.ConsoleUI;
import util.InputValidator;

import java.util.Scanner;

/**
 * Console submenu for Performance / Complexity demonstration.
 *
 * @author MS Faathima Hasna (23da2-1093)
 */
public class PerformanceMenu {
    private static final int[] BENCHMARK_SIZES = {100, 1000, 10000, 100000};

    private final DynamicArray array;
    private final Graph graph;
    private final Scanner scanner;

    public PerformanceMenu(DynamicArray array, Graph graph, Scanner scanner) {
        this.array = array;
        this.graph = graph;
        this.scanner = scanner;
    }

    public void run() {
        boolean back = false;
        while (!back) {
            ConsoleUI.subHeader("PERFORMANCE COMPARISON");
            System.out.println("1. Run Automated Benchmark (n = 100 ... 100,000)");
            System.out.println("2. Analyze My Array (Linear vs Binary Search)");
            System.out.println("3. Analyze My Graph (BFS vs DFS)");
            System.out.println("4. Complexity Cheat Sheet");
            System.out.println("5. Return to Main Menu");
            int choice = InputValidator.readInt(scanner, "Enter your choice: ", 1, 5);
            switch (choice) {
                case 1: PerformanceAnalyzer.runAutomatedBenchmark(BENCHMARK_SIZES); break;
                case 2: analyzeArray(); break;
                case 3: analyzeGraph(); break;
                case 4: PerformanceAnalyzer.printComplexityCheatSheet(); break;
                default: back = true;
            }
        }
    }

    private void analyzeArray() {
        if (array.isEmpty()) {
            ConsoleUI.error("Your array is empty. Fill it in the Array or Searching menu first.");
            return;
        }
        int target = InputValidator.readInt(scanner, "Enter value to search for: ");
        PerformanceAnalyzer.analyzeArray(array, target);
    }

    private void analyzeGraph() {
        if (graph.isEmpty()) {
            ConsoleUI.error("Your graph is empty. Build it or load the sample in the Graph menu first.");
            return;
        }
        String start;
        do {
            start = InputValidator.readLabel(scanner, "Enter start vertex: ");
            if (!graph.hasVertex(start)) {
                ConsoleUI.error("Vertex '" + start + "' does not exist. Existing: " + graph.vertices());
            }
        } while (!graph.hasVertex(start));
        PerformanceAnalyzer.analyzeGraph(graph, start);
    }
}
