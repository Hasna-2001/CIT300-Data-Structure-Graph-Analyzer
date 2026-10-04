package util;

/**
 * ConsoleUI - consistent headers, separators and messages for every menu.
 *
 * @author MS Faathima Hasna (23da2-1093)
 */
public final class ConsoleUI {
    public static final int WIDTH = 45;

    private ConsoleUI() { }

    public static String repeat(char c, int count) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) sb.append(c);
        return sb.toString();
    }

    /** Big banner header: ===== / title / ===== */
    public static void header(String title) {
        System.out.println();
        System.out.println(repeat('=', WIDTH));
        System.out.println(" " + title);
        System.out.println(repeat('=', WIDTH));
    }

    /** Sub-menu header: --------------- TITLE ------------ */
    public static void subHeader(String title) {
        System.out.println();
        System.out.println("--------------- " + title + " ------------");
    }

    public static void line() { System.out.println(repeat('-', 48)); }

    public static void error(String message) { System.out.println("  [!] " + message); }

    public static void success(String message) { System.out.println("  [OK] " + message); }

    public static void info(String message) { System.out.println("  [i] " + message); }
}
