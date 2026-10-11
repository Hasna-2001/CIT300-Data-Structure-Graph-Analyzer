package graph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * Graph - adjacency-list representation (Map of vertex -> list of neighbours).
 * Supports undirected (default) and directed graphs.
 *
 * Why an adjacency list? Memory is O(V + E) and a traversal only touches real
 * edges, giving BFS / DFS a running time of O(V + E). An adjacency matrix would
 * need O(V^2) memory (it is still available for display).
 *
 * @author MS Faathima Hasna (23da2-1093)
 */
public class Graph {
    private final Map<String, List<String>> adjacency = new LinkedHashMap<>();
    private final boolean directed;
    private int edgeCount;

    public Graph() { this(false); }

    public Graph(boolean directed) { this.directed = directed; }

    // ------------------------------------------------------------------ basic operations

    /** @return true if added, false if the vertex already existed */
    public boolean addVertex(String vertex) {
        if (adjacency.containsKey(vertex)) return false;
        adjacency.put(vertex, new ArrayList<>());
        return true;
    }

    /**
     * Adds an edge between two existing vertices.
     * @return true if added, false if the edge already existed
     * @throws IllegalArgumentException for unknown vertices or self-loops
     */
    public boolean addEdge(String from, String to) {
        requireVertex(from);
        requireVertex(to);
        if (from.equals(to)) {
            throw new IllegalArgumentException("Self-loops are not allowed (" + from + " -> " + to + ").");
        }
        if (adjacency.get(from).contains(to)) return false;
        adjacency.get(from).add(to);
        if (!directed) adjacency.get(to).add(from);
        edgeCount++;
        return true;
    }

    /** @return true if the edge existed and was removed */
    public boolean removeEdge(String from, String to) {
        requireVertex(from);
        requireVertex(to);
        if (!adjacency.get(from).remove(to)) return false;
        if (!directed) adjacency.get(to).remove(from);
        edgeCount--;
        return true;
    }

    /** Removes a vertex and every edge touching it. */
    public void removeVertex(String vertex) {
        requireVertex(vertex);
        int removedEdges = adjacency.get(vertex).size();
        for (Map.Entry<String, List<String>> entry : adjacency.entrySet()) {
            if (!entry.getKey().equals(vertex) && entry.getValue().remove(vertex) && directed) {
                removedEdges++;     // incoming edge of a directed graph
            }
        }
        adjacency.remove(vertex);
        edgeCount -= removedEdges;
    }

    public void clear() {
        adjacency.clear();
        edgeCount = 0;
    }

    public boolean hasVertex(String vertex) { return adjacency.containsKey(vertex); }
    public int vertexCount() { return adjacency.size(); }
    public int edgeCount() { return edgeCount; }
    public boolean isEmpty() { return adjacency.isEmpty(); }
    public boolean isDirected() { return directed; }

    public List<String> vertices() {
        return Collections.unmodifiableList(new ArrayList<>(adjacency.keySet()));
    }

    public List<String> neighbors(String vertex) {
        requireVertex(vertex);
        return Collections.unmodifiableList(adjacency.get(vertex));
    }

    // ------------------------------------------------------------------ display

    public String adjacencyListString() {
        if (isEmpty()) return "(graph is empty)";
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, List<String>> entry : adjacency.entrySet()) {
            sb.append(String.format("%-10s -> ", entry.getKey()));
            sb.append(entry.getValue().isEmpty() ? "(no neighbours)" : entry.getValue().toString());
            sb.append("\n");
        }
        return sb.toString().trim();
    }

    public String adjacencyMatrixString() {
        if (isEmpty()) return "(graph is empty)";
        List<String> names = new ArrayList<>(adjacency.keySet());
        int width = 3;
        for (String name : names) width = Math.max(width, name.length() + 1);
        StringBuilder sb = new StringBuilder(pad("", width));
        for (String name : names) sb.append(pad(name, width));
        sb.append("\n");
        for (String row : names) {
            sb.append(pad(row, width));
            for (String col : names) {
                sb.append(pad(adjacency.get(row).contains(col) ? "1" : "0", width));
            }
            sb.append("\n");
        }
        return sb.toString().trim();
    }

    // ------------------------------------------------------------------ traversal / search

    /** Breadth-First Search over the whole reachable graph. */
    public TraversalResult bfs(String start) { return bfs(start, null); }

    /**
     * Breadth-First Search using a queue. Explores level by level, so the first
     * time a target is reached it is by a path with the fewest edges.
     * If target is not null the search stops as soon as the target is visited.
     */
    public TraversalResult bfs(String start, String target) {
        requireVertex(start);
        long begin = System.nanoTime();
        long steps = 0;
        int peak = 1;
        boolean found = false;
        Set<String> visited = new HashSet<>();
        Deque<String> queue = new ArrayDeque<>();
        List<String> order = new ArrayList<>();
        visited.add(start);
        queue.add(start);
        while (!queue.isEmpty()) {
            String current = queue.poll();
            order.add(current);
            steps++;
            if (current.equals(target)) {
                found = true;
                break;
            }
            for (String neighbor : adjacency.get(current)) {
                steps++;
                if (visited.add(neighbor)) queue.add(neighbor);
            }
            peak = Math.max(peak, queue.size());
        }
        return new TraversalResult("BFS", start, target, found, order, steps, peak,
                System.nanoTime() - begin);
    }

    /** Depth-First Search over the whole reachable graph. */
    public TraversalResult dfs(String start) { return dfs(start, null); }

    /**
     * Depth-First Search using an explicit stack (iterative, so very deep graphs
     * cannot overflow the Java call stack). Goes as deep as possible before
     * backtracking. Neighbours are pushed in reverse so they are visited in
     * insertion order, matching the recursive version.
     */
    public TraversalResult dfs(String start, String target) {
        requireVertex(start);
        long begin = System.nanoTime();
        long steps = 0;
        int peak = 1;
        boolean found = false;
        Set<String> visited = new HashSet<>();
        Deque<String> stack = new ArrayDeque<>();
        List<String> order = new ArrayList<>();
        stack.push(start);
        while (!stack.isEmpty()) {
            String current = stack.pop();
            if (!visited.add(current)) continue;      // already visited via another path
            order.add(current);
            steps++;
            if (current.equals(target)) {
                found = true;
                break;
            }
            List<String> neighbours = adjacency.get(current);
            for (int i = neighbours.size() - 1; i >= 0; i--) {
                steps++;
                if (!visited.contains(neighbours.get(i))) stack.push(neighbours.get(i));
            }
            peak = Math.max(peak, stack.size());
        }
        return new TraversalResult("DFS", start, target, found, order, steps, peak,
                System.nanoTime() - begin);
    }

    /** Shortest path (fewest edges) from start to goal using BFS parents; empty if unreachable. */
    public List<String> shortestPath(String start, String goal) {
        requireVertex(start);
        requireVertex(goal);
        Map<String, String> parent = new HashMap<>();
        Deque<String> queue = new ArrayDeque<>();
        parent.put(start, null);
        queue.add(start);
        while (!queue.isEmpty()) {
            String current = queue.poll();
            if (current.equals(goal)) break;
            for (String neighbor : adjacency.get(current)) {
                if (!parent.containsKey(neighbor)) {
                    parent.put(neighbor, current);
                    queue.add(neighbor);
                }
            }
        }
        if (!parent.containsKey(goal)) return new ArrayList<>();
        LinkedList<String> path = new LinkedList<>();
        for (String at = goal; at != null; at = parent.get(at)) path.addFirst(at);
        return path;
    }

    /** Number of connected components (meaningful for undirected graphs). */
    public int connectedComponents() {
        Set<String> seen = new HashSet<>();
        int components = 0;
        for (String vertex : adjacency.keySet()) {
            if (seen.contains(vertex)) continue;
            components++;
            seen.addAll(bfs(vertex).getOrder());
        }
        return components;
    }

    // ------------------------------------------------------------------ factories

    /**
     * Builds a connected random undirected graph V0..V(n-1). A random spanning tree
     * guarantees connectivity, then extra random edges are added up to edgeTarget.
     */
    public static Graph generateRandom(int vertexCount, int edgeTarget, long seed) {
        Graph graph = new Graph(false);
        Random random = new Random(seed);
        for (int i = 0; i < vertexCount; i++) graph.addVertex("V" + i);
        for (int i = 1; i < vertexCount; i++) graph.addEdge("V" + i, "V" + random.nextInt(i));
        long maxEdges = (long) vertexCount * (vertexCount - 1) / 2;
        long goal = Math.min(edgeTarget, maxEdges);
        long attempts = 0;
        while (graph.edgeCount < goal && attempts < goal * 20) {
            int a = random.nextInt(vertexCount);
            int b = random.nextInt(vertexCount);
            if (a != b) graph.addEdge("V" + a, "V" + b);
            attempts++;
        }
        return graph;
    }

    // ------------------------------------------------------------------ helpers

    private void requireVertex(String vertex) {
        if (!adjacency.containsKey(vertex)) {
            throw new IllegalArgumentException("Vertex '" + vertex + "' does not exist in the graph.");
        }
    }

    private static String pad(String text, int width) {
        StringBuilder sb = new StringBuilder(text);
        while (sb.length() < width) sb.append(' ');
        return sb.toString();
    }
}
