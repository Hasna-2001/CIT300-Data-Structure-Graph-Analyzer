package menu;

import graph.Graph;
import graph.TraversalResult;
import util.ConsoleUI;
import util.InputValidator;
import util.ResultLog;

import java.util.List;
import java.util.Scanner;

/**
 * Console submenu for the Graph component.
 *
 * @author MS Faathima Hasna (23da2-1093)
 */
public class GraphMenu {
    private final Graph graph;
    private final Scanner scanner;

    public GraphMenu(Graph graph, Scanner scanner) {
        this.graph = graph;
        this.scanner = scanner;
    }

    public void run() {
        boolean back = false;
        while (!back) {
            ConsoleUI.subHeader("GRAPH OPERATIONS");
            System.out.println("1. Add Vertex");
            System.out.println("2. Add Edge");
            System.out.println("3. Display Graph (Adjacency List)");
            System.out.println("4. Display Adjacency Matrix");
            System.out.println("5. BFS Traversal");
            System.out.println("6. DFS Traversal");
            System.out.println("7. Search for a Vertex (BFS vs DFS)");
            System.out.println("8. Shortest Path (BFS)");
            System.out.println("9. Remove Edge");
            System.out.println("10. Remove Vertex");
            System.out.println("11. Load Sample Campus Graph");
            System.out.println("12. Graph Statistics");
            System.out.println("13. Return to Main Menu");
            int choice = InputValidator.readInt(scanner, "Enter your choice: ", 1, 13);
            try {
                switch (choice) {
                    case 1: addVertex(); break;
                    case 2: addEdge(); break;
                    case 3: System.out.println(graph.adjacencyListString()); break;
                    case 4: System.out.println(graph.adjacencyMatrixString()); break;
                    case 5: traverse(true); break;
                    case 6: traverse(false); break;
                    case 7: searchVertex(); break;
                    case 8: shortestPath(); break;
                    case 9: removeEdge(); break;
                    case 10: removeVertex(); break;
                    case 11: loadSample(); break;
                    case 12: statistics(); break;
                    default: back = true;
                }
            } catch (IllegalArgumentException e) {
                ConsoleUI.error(e.getMessage());
            }
        }
    }

    private void addVertex() {
        String name = InputValidator.readLabel(scanner, "Enter vertex name: ");
        if (graph.addVertex(name)) ConsoleUI.success("Vertex '" + name + "' added.");
        else ConsoleUI.error("Vertex '" + name + "' already exists.");
    }

    private void addEdge() {
        if (graph.vertexCount() < 2) {
            ConsoleUI.error("Add at least two vertices first.");
            return;
        }
        String from = InputValidator.readLabel(scanner, "Enter first vertex: ");
        String to = InputValidator.readLabel(scanner, "Enter second vertex: ");
        if (graph.addEdge(from, to)) ConsoleUI.success("Edge " + from + " - " + to + " added.");
        else ConsoleUI.error("That edge already exists.");
    }

    private void traverse(boolean bfs) {
        if (emptyWarning()) return;
        String start = askExistingVertex("Enter start vertex: ");
        TraversalResult result = bfs ? graph.bfs(start) : graph.dfs(start);
        System.out.println(result);
        ResultLog.add("Graph", result.getAlgorithm() + " from " + start + " | visited "
                + result.getVisitedCount() + " | steps " + result.getSteps() + " | order "
                + String.join(",", result.getOrder()));
    }

    private void searchVertex() {
        if (emptyWarning()) return;
        String start = askExistingVertex("Enter start vertex: ");
        String target = askExistingVertex("Enter vertex to find: ");
        TraversalResult bfs = graph.bfs(start, target);
        TraversalResult dfs = graph.dfs(start, target);
        System.out.println(bfs);
        System.out.println();
        System.out.println(dfs);
        System.out.println();
        System.out.println("  BFS steps: " + bfs.getSteps() + " | DFS steps: " + dfs.getSteps()
                + "  (BFS finds the closest match first; DFS dives deep and may be faster or slower by luck of layout)");
        ResultLog.add("Graph", "search " + start + "->" + target + " | BFS steps " + bfs.getSteps()
                + " vs DFS steps " + dfs.getSteps());
    }

    private void shortestPath() {
        if (emptyWarning()) return;
        String start = askExistingVertex("Enter start vertex: ");
        String goal = askExistingVertex("Enter destination vertex: ");
        List<String> path = graph.shortestPath(start, goal);
        if (path.isEmpty()) {
            ConsoleUI.error("No path exists from " + start + " to " + goal + ".");
        } else {
            ConsoleUI.success("Shortest path (" + (path.size() - 1) + " edges): " + String.join(" -> ", path));
            ResultLog.add("Graph", "shortest path " + String.join("->", path));
        }
    }

    private void removeEdge() {
        if (emptyWarning()) return;
        String from = askExistingVertex("Enter first vertex: ");
        String to = askExistingVertex("Enter second vertex: ");
        if (graph.removeEdge(from, to)) ConsoleUI.success("Edge removed.");
        else ConsoleUI.error("There is no edge between " + from + " and " + to + ".");
    }

    private void removeVertex() {
        if (emptyWarning()) return;
        String name = askExistingVertex("Enter vertex to remove: ");
        graph.removeVertex(name);
        ConsoleUI.success("Vertex '" + name + "' and its edges removed.");
    }

    private void loadSample() {
        graph.clear();
        String[] places = {"Gate", "Admin", "Library", "LabA", "LabB", "Canteen", "Hostel", "Ground"};
        for (String place : places) graph.addVertex(place);
        String[][] edges = {{"Gate", "Admin"}, {"Gate", "Canteen"}, {"Admin", "Library"},
                {"Admin", "LabA"}, {"Library", "LabB"}, {"LabA", "LabB"}, {"Canteen", "Hostel"},
                {"Hostel", "Ground"}, {"Canteen", "Ground"}};
        for (String[] edge : edges) graph.addEdge(edge[0], edge[1]);
        ConsoleUI.success("Sample campus graph loaded (" + graph.vertexCount() + " vertices, "
                + graph.edgeCount() + " edges).");
    }

    private void statistics() {
        System.out.println("  Type        : " + (graph.isDirected() ? "Directed" : "Undirected"));
        System.out.println("  Vertices    : " + graph.vertexCount());
        System.out.println("  Edges       : " + graph.edgeCount());
        System.out.println("  Components  : " + graph.connectedComponents());
    }

    private String askExistingVertex(String prompt) {
        while (true) {
            String name = InputValidator.readLabel(scanner, prompt);
            if (graph.hasVertex(name)) return name;
            ConsoleUI.error("Vertex '" + name + "' does not exist. Existing: " + graph.vertices());
        }
    }

    private boolean emptyWarning() {
        if (graph.isEmpty()) {
            ConsoleUI.error("The graph is empty. Add vertices or load the sample graph (option 11).");
            return true;
        }
        return false;
    }
}
