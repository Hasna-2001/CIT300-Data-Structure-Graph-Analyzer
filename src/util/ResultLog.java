package util;

import java.util.ArrayList;
import java.util.List;

/**
 * ResultLog - collects every search, traversal and performance result produced
 * during the session so main-menu option 8 ("Display All Results") can show them.
 *
 * @author M Hathiqu Ahamath (23da2-0526)
 */
public final class ResultLog {
    private static final List<String> ENTRIES = new ArrayList<>();

    private ResultLog() { }

    public static void add(String category, String details) {
        ENTRIES.add(String.format("[%03d] %-12s %s", ENTRIES.size() + 1, category, details));
    }

    public static int count() { return ENTRIES.size(); }

    public static void clear() { ENTRIES.clear(); }

    public static void display() {
        ConsoleUI.header("ALL RECORDED RESULTS");
        if (ENTRIES.isEmpty()) {
            System.out.println("No results recorded yet. Run a search, traversal or performance test first.");
            return;
        }
        for (String entry : ENTRIES) {
            System.out.println(entry);
        }
        System.out.println("---------------------------------------------");
        System.out.println("Total recorded results: " + ENTRIES.size());
    }
}
