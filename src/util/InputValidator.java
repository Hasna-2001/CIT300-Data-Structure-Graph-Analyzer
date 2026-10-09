package util;

import java.util.Scanner;

/**
 * InputValidator - re-prompts until the user enters valid input, so a wrong
 * keystroke never crashes the program. If the input stream closes, the
 * NoSuchElementException from Scanner propagates to Main, which exits cleanly.
 *
 * @author M Hathiqu Ahamath (23da2-0526)
 */
public final class InputValidator {

    private InputValidator() { }

    public static int readInt(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                ConsoleUI.error("Invalid input. Please enter a whole number.");
            }
        }
    }

    public static int readInt(Scanner scanner, String prompt, int min, int max) {
        while (true) {
            int value = readInt(scanner, prompt);
            if (value >= min && value <= max) return value;
            ConsoleUI.error("Please enter a number between " + min + " and " + max + ".");
        }
    }

    /** Reads a non-empty label (no spaces, at most 20 characters) - used for graph vertices. */
    public static String readLabel(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) {
                ConsoleUI.error("Input cannot be empty.");
            } else if (line.contains(" ")) {
                ConsoleUI.error("Labels cannot contain spaces (try " + line.replace(' ', '_') + ").");
            } else if (line.length() > 20) {
                ConsoleUI.error("Labels can be at most 20 characters.");
            } else {
                return line;
            }
        }
    }

    /** Like readLabel but an empty line is allowed and returned as null. */
    public static String readOptionalLabel(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) return null;
            if (line.contains(" ") || line.length() > 20) {
                ConsoleUI.error("Labels cannot contain spaces and can be at most 20 characters.");
            } else {
                return line;
            }
        }
    }

    public static boolean readYesNo(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt + " (y/n): ");
            String line = scanner.nextLine().trim().toLowerCase();
            if (line.equals("y") || line.equals("yes")) return true;
            if (line.equals("n") || line.equals("no")) return false;
            ConsoleUI.error("Please answer y or n.");
        }
    }

    public static String readNonEmptyLine(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            if (!line.isEmpty()) return line;
            ConsoleUI.error("Input cannot be empty.");
        }
    }
}
