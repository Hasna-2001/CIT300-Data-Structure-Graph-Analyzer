package test;

import ds.ArrayStack;
import ds.CircularQueue;
import ds.DynamicArray;
import ds.EmptyStructureException;
import ds.SinglyLinkedList;
import graph.Graph;
import graph.TraversalResult;
import search.SearchAlgorithms;
import search.SearchResult;

import java.util.Arrays;

/**
 * Lightweight self-test (no external libraries). Run with: java -cp out test.TestRunner
 * Each section is labelled with the member who owns that component.
 *
 * @author MS Faathima Hasna (23da2-1093) - testing and integration
 */
public class TestRunner {
    private static int passed = 0;
    private static int failed = 0;

    public static void main(String[] args) {
        testArrayAndSearch();
        testStackAndQueue();
        testLinkedList();
        testGraph();
        System.out.println();
        System.out.println("Tests passed: " + passed + " | failed: " + failed);
        if (failed > 0) System.exit(1);
    }

    // ---- Sheeraz: Array + Searching
    private static void testArrayAndSearch() {
        DynamicArray a = new DynamicArray(2);
        for (int i = 1; i <= 5; i++) a.insertAtEnd(i * 10);   // forces a resize
        check("array grows past initial capacity", a.size() == 5 && a.capacity() >= 5);
        a.insertAt(0, 5);
        check("insertAt shifts elements", a.get(0) == 5 && a.get(1) == 10 && a.size() == 6);
        check("deleteAt returns removed value", a.deleteAt(0) == 5 && a.get(0) == 10);
        check("deleteByValue finds value", a.deleteByValue(30) == 2 && a.size() == 4);
        check("deleteByValue missing returns -1", a.deleteByValue(999) == -1);
        boolean threw = false;
        try { a.deleteAt(50); } catch (IndexOutOfBoundsException e) { threw = true; }
        check("invalid index rejected", threw);

        a.fillSequential(1000, 2);
        SearchResult lin = SearchAlgorithms.linearSearch(a.rawData(), a.size(), 1998);
        SearchResult bin = SearchAlgorithms.binarySearch(a.rawData(), a.size(), 1998);
        check("linear search finds last element in n steps", lin.isFound() && lin.getIndex() == 999 && lin.getSteps() == 1000);
        check("binary search finds same element", bin.isFound() && bin.getIndex() == 999);
        check("binary search uses <= log2(n)+1 steps", bin.getSteps() <= 11);
        check("search miss reported", !SearchAlgorithms.binarySearch(a.rawData(), a.size(), 7).isFound());
        threw = false;
        try { SearchAlgorithms.binarySearch(new int[]{3, 1, 2}, 3, 1); } catch (IllegalArgumentException e) { threw = true; }
        check("binary search rejects unsorted data", threw);
    }

    // ---- Halidha: Stack + Queue
    private static void testStackAndQueue() {
        ArrayStack<Integer> s = new ArrayStack<>(2);
        check("push returns true", s.push(1) && s.push(2));
        check("push on full stack returns false", !s.push(3));
        check("LIFO order", s.peek() == 2 && s.pop() == 2 && s.pop() == 1);
        boolean threw = false;
        try { s.pop(); } catch (EmptyStructureException e) { threw = true; }
        check("pop on empty stack throws", threw);

        CircularQueue<Integer> q = new CircularQueue<>(3);
        q.enqueue(1); q.enqueue(2); q.enqueue(3);
        check("enqueue on full queue returns false", !q.enqueue(4));
        check("FIFO order", q.dequeue() == 1 && q.peek() == 2);
        q.enqueue(4);   // wraps around
        check("circular wrap-around keeps order", q.display().contains("2 3 4") && q.rearIndex() == 0);
        q.dequeue(); q.dequeue(); q.dequeue();
        threw = false;
        try { q.dequeue(); } catch (EmptyStructureException e) { threw = true; }
        check("dequeue on empty queue throws", threw);
    }

    // ---- Hathiqu: Linked List
    private static void testLinkedList() {
        SinglyLinkedList<Integer> l = new SinglyLinkedList<>();
        l.insertAtTail(2); l.insertAtTail(3); l.insertAtHead(1); l.insertAt(3, 4);
        check("insert head/tail/position", l.display().equals("HEAD -> 1 -> 2 -> 3 -> 4 -> null") && l.size() == 4);
        check("search counts steps", l.search(3).getSteps() == 3 && l.search(3).getIndex() == 2);
        check("search miss", !l.search(99).isFound());
        check("deleteByValue", l.deleteByValue(2) && !l.deleteByValue(2));
        check("deleteAt tail updates tail", l.deleteAt(l.size() - 1) == 4);
        l.insertAtTail(9);
        check("tail pointer still valid after delete", l.display().equals("HEAD -> 1 -> 3 -> 9 -> null"));
        l.reverse();
        check("reverse", l.display().equals("HEAD -> 9 -> 3 -> 1 -> null"));
        l.insertAtTail(0);
        check("tail correct after reverse", l.display().equals("HEAD -> 9 -> 3 -> 1 -> 0 -> null"));
        boolean threw = false;
        try { new SinglyLinkedList<Integer>().deleteAt(0); } catch (EmptyStructureException e) { threw = true; }
        check("delete on empty list throws", threw);
    }

    // ---- Hasna: Graph
    private static void testGraph() {
        Graph g = new Graph();
        for (String v : new String[]{"A", "B", "C", "D", "E"}) g.addVertex(v);
        g.addEdge("A", "B"); g.addEdge("A", "C"); g.addEdge("B", "D"); g.addEdge("C", "D");
        check("duplicate vertex rejected", !g.addVertex("A"));
        check("duplicate edge rejected", !g.addEdge("B", "A"));
        check("edge count", g.edgeCount() == 4);
        check("BFS order", g.bfs("A").getOrder().equals(Arrays.asList("A", "B", "C", "D")));
        check("DFS order", g.dfs("A").getOrder().equals(Arrays.asList("A", "B", "D", "C")));
        check("isolated vertex not reached", !g.bfs("A").getOrder().contains("E"));
        check("components counted", g.connectedComponents() == 2);
        check("shortest path", g.shortestPath("A", "D").size() == 3);
        check("no path to isolated vertex", g.shortestPath("A", "E").isEmpty());
        TraversalResult found = g.bfs("A", "C");
        check("targeted BFS stops early", found.isTargetFound() && found.getVisitedCount() == 3);
        boolean threw = false;
        try { g.addEdge("A", "Z"); } catch (IllegalArgumentException e) { threw = true; }
        check("edge to unknown vertex rejected", threw);
        g.removeVertex("D");
        check("removeVertex removes its edges", g.edgeCount() == 2 && !g.neighbors("B").contains("D"));
        Graph big = Graph.generateRandom(2000, 4000, 1);
        check("generated graph is connected", big.connectedComponents() == 1);
        check("BFS and DFS reach every vertex", big.bfs("V0").getVisitedCount() == 2000
                && big.dfs("V0").getVisitedCount() == 2000);
    }

    private static void check(String name, boolean condition) {
        if (condition) {
            passed++;
            System.out.println("  PASS  " + name);
        } else {
            failed++;
            System.out.println("  FAIL  " + name);
        }
    }
}
