package analysis;

import ds.DynamicArray;
import ds.SinglyLinkedList;
import graph.Graph;
import graph.TraversalResult;
import search.SearchAlgorithms;
import search.SearchResult;
import util.ConsoleUI;
import util.ResultLog;

import java.util.Arrays;
import java.util.function.Supplier;

/**
 * PerformanceAnalyzer - measures steps (operation counts) and execution time for
 * the main algorithms and prints a comparison table together with a
 * Big-O explanation. Steps are deterministic; time (ns) varies with the machine.
 * Each timed operation is run 3 times and the fastest run is reported.
 *
 * @author MS Faathima Hasna (23da2-1093)
 */
public final class PerformanceAnalyzer {
    private static final String HEADER_FORMAT = "%-16s %-26s %9s %11s %12s  %s%n";
    private static final String ROW_FORMAT = "%-16s %-26s %9d %11d %12d  %s%n";

    private PerformanceAnalyzer() { }

    // ------------------------------------------------------------------ automated benchmark

    public static void runAutomatedBenchmark(int[] sizes) {
        ConsoleUI.header("PERFORMANCE COMPARISON");
        System.out.println("Worst-case style tests: the search target is the LAST element.");
        System.out.println();
        printHeader();
        for (int n : sizes) {
            benchmarkSize(n);
        }
        System.out.println(ConsoleUI.repeat('-', 80));
        printExplanation();
    }

    private static void benchmarkSize(int n) {
        // --- searching on an array and on a linked list holding the same sorted data
        DynamicArray array = new DynamicArray(Math.max(n, 1));
        array.fillSequential(n, 2);
        int target = 2 * (n - 1);

        SearchResult linear = bestSearch(() -> SearchAlgorithms.linearSearch(array.rawData(), array.size(), target));
        SearchResult binary = bestSearch(() -> SearchAlgorithms.binarySearch(array.rawData(), array.size(), target));
        printRow("Search", "Linear Search (Array)", n, linear.getSteps(), linear.getNanoTime(), "O(n)");
        printRow("Search", "Binary Search (Array)", n, binary.getSteps(), binary.getNanoTime(), "O(log n)");

        SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
        for (int i = 0; i < n; i++) list.insertAtTail(i * 2);
        SearchResult listSearch = bestSearch(() -> list.search(target));
        printRow("Search", "Linear Search (Linked List)", n, listSearch.getSteps(), listSearch.getNanoTime(), "O(n)");

        // --- inserting at the front: array must shift everything, linked list just re-points head
        long t0 = System.nanoTime();
        int shifts = array.insertAt(0, -1);
        long arrayInsertTime = System.nanoTime() - t0;
        t0 = System.nanoTime();
        list.insertAtHead(-1);
        long listInsertTime = System.nanoTime() - t0;
        printRow("Insert at front", "Array (shift elements)", n, shifts, arrayInsertTime, "O(n)");
        printRow("Insert at front", "Linked List (new head)", n, 1, listInsertTime, "O(1)");

        // --- graph traversal on a connected random graph with V = n and E = 2n
        Graph graph = Graph.generateRandom(n, 2 * n, 42);
        String start = "V0";
        String goal = "V" + (n - 1);
        TraversalResult bfsFull = bestTraversal(() -> graph.bfs(start));
        TraversalResult dfsFull = bestTraversal(() -> graph.dfs(start));
        TraversalResult bfsFind = bestTraversal(() -> graph.bfs(start, goal));
        TraversalResult dfsFind = bestTraversal(() -> graph.dfs(start, goal));
        int v = graph.vertexCount();
        printRow("Graph Traversal", "BFS (full, V=" + v + ")", n, bfsFull.getSteps(), bfsFull.getNanoTime(), "O(V+E)");
        printRow("Graph Traversal", "DFS (full, V=" + v + ")", n, dfsFull.getSteps(), dfsFull.getNanoTime(), "O(V+E)");
        printRow("Graph Search", "BFS to " + goal, n, bfsFind.getSteps(), bfsFind.getNanoTime(), "O(V+E)");
        printRow("Graph Search", "DFS to " + goal, n, dfsFind.getSteps(), dfsFind.getNanoTime(), "O(V+E)");
        System.out.println();
    }

    // ------------------------------------------------------------------ analysis of the user's own data

    public static void analyzeArray(DynamicArray array, int target) {
        ConsoleUI.header("PERFORMANCE COMPARISON (your array, n = " + array.size() + ")");
        int[] sorted = array.toArray();
        Arrays.sort(sorted);        // binary search needs sorted data; the original array is untouched
        SearchResult linear = bestSearch(() -> SearchAlgorithms.linearSearch(array.rawData(), array.size(), target));
        SearchResult binary = bestSearch(() -> SearchAlgorithms.binarySearch(sorted, sorted.length, target));
        printHeader();
        printRow("Search", "Linear Search", array.size(), linear.getSteps(), linear.getNanoTime(), "O(n)");
        printRow("Search", "Binary Search (sorted copy)", array.size(), binary.getSteps(), binary.getNanoTime(), "O(log n)");
    }

    public static void analyzeGraph(Graph graph, String start) {
        ConsoleUI.header("PERFORMANCE COMPARISON (your graph, V = " + graph.vertexCount() + ")");
        TraversalResult bfs = bestTraversal(() -> graph.bfs(start));
        TraversalResult dfs = bestTraversal(() -> graph.dfs(start));
        printHeader();
        printRow("Graph Traversal", "BFS", graph.vertexCount(), bfs.getSteps(), bfs.getNanoTime(), "O(V+E)");
        printRow("Graph Traversal", "DFS", graph.vertexCount(), dfs.getSteps(), dfs.getNanoTime(), "O(V+E)");
        System.out.println();
        System.out.println("Peak frontier size - BFS queue: " + bfs.getPeakFrontier()
                + " | DFS stack: " + dfs.getPeakFrontier());
        System.out.println("Both visit every reachable vertex and examine every adjacency entry once, so their");
        System.out.println("step counts match (V + sum of degrees); they differ in ORDER and in memory used by the frontier.");
    }

    public static void printComplexityCheatSheet() {
        ConsoleUI.header("COMPLEXITY CHEAT SHEET");
        System.out.printf("%-34s %-14s %-14s%n", "Operation", "Average", "Worst");
        ConsoleUI.line();
        String[][] rows = {
                {"Array: access by index", "O(1)", "O(1)"},
                {"Array: insert/delete at index", "O(n)", "O(n)"},
                {"Array: append (dynamic)", "O(1) amortised", "O(n) on resize"},
                {"Stack: push / pop / peek", "O(1)", "O(1)"},
                {"Queue: enqueue / dequeue / peek", "O(1)", "O(1)"},
                {"Linked list: insert at head/tail", "O(1)", "O(1)"},
                {"Linked list: insert/delete by pos", "O(n)", "O(n)"},
                {"Linked list: search", "O(n)", "O(n)"},
                {"Linear search", "O(n)", "O(n)"},
                {"Binary search (sorted)", "O(log n)", "O(log n)"},
                {"Graph BFS / DFS (adj. list)", "O(V + E)", "O(V + E)"},
        };
        for (String[] row : rows) System.out.printf("%-34s %-14s %-14s%n", row[0], row[1], row[2]);
    }

    // ------------------------------------------------------------------ helpers

    private static void printExplanation() {
        System.out.println("WHY THE RESULTS DIFFER");
        System.out.println("- Linear search steps grow in direct proportion to n (O(n)); binary search only");
        System.out.println("  halves the range each step, so it needs about log2(n) steps (O(log n)).");
        System.out.println("- A linked list search is O(n) like a linear array search, but each step follows a");
        System.out.println("  pointer, so it is usually slower in time than scanning a contiguous array.");
        System.out.println("- Inserting at the front of an array shifts all n elements (O(n)); a linked list just");
        System.out.println("  re-points the head (O(1)).");
        System.out.println("- A full BFS and DFS do the same amount of work, O(V+E); they differ in visit order and");
        System.out.println("  in memory. When searching for one target the step counts differ, because BFS stops");
        System.out.println("  after exploring nearby levels while DFS may dive down a long branch first.");
        System.out.println("- Steps are exact and repeatable; time (ns) depends on the machine and JVM warm-up.");
    }

    private static void printHeader() {
        System.out.printf(HEADER_FORMAT, "Operation", "Algorithm", "Input n", "Steps", "Time (ns)", "Complexity");
        System.out.println(ConsoleUI.repeat('-', 80));
    }

    private static void printRow(String operation, String algorithm, int n, long steps, long nanos, String bigO) {
        System.out.printf(ROW_FORMAT, operation, algorithm, n, steps, nanos, bigO);
        ResultLog.add("Performance", operation + " | " + algorithm + " | n=" + n + " | steps=" + steps
                + " | " + nanos + " ns | " + bigO);
    }

    private static SearchResult bestSearch(Supplier<SearchResult> run) {
        SearchResult best = null;
        for (int i = 0; i < 3; i++) {
            SearchResult result = run.get();
            if (best == null || result.getNanoTime() < best.getNanoTime()) best = result;
        }
        return best;
    }

    private static TraversalResult bestTraversal(Supplier<TraversalResult> run) {
        TraversalResult best = null;
        for (int i = 0; i < 3; i++) {
            TraversalResult result = run.get();
            if (best == null || result.getNanoTime() < best.getNanoTime()) best = result;
        }
        return best;
    }
}
