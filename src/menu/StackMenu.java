package menu;

import ds.ArrayStack;
import ds.EmptyStructureException;
import util.ConsoleUI;
import util.InputValidator;

import java.util.Scanner;

/**
 * Console submenu for the Stack component (plus a stack application:
 * balanced-bracket checking).
 *
 * @author R Halidha Nashath (23da2-0535)
 */
public class StackMenu {
    private final ArrayStack<Integer> stack = new ArrayStack<>(8);
    private final Scanner scanner;

    public StackMenu(Scanner scanner) {
        this.scanner = scanner;
    }

    public void run() {
        boolean back = false;
        while (!back) {
            ConsoleUI.subHeader("STACK OPERATIONS");
            System.out.println("1. Push");
            System.out.println("2. Pop");
            System.out.println("3. Peek");
            System.out.println("4. Display Stack");
            System.out.println("5. Stack Status");
            System.out.println("6. Balanced Brackets Checker (stack application)");
            System.out.println("7. Return to Main Menu");
            int choice = InputValidator.readInt(scanner, "Enter your choice: ", 1, 7);
            try {
                switch (choice) {
                    case 1: push(); break;
                    case 2: ConsoleUI.success("Popped: " + stack.pop()); break;
                    case 3: ConsoleUI.success("Top element: " + stack.peek()); break;
                    case 4: System.out.println("  " + stack.display()); break;
                    case 5: status(); break;
                    case 6: checkBrackets(); break;
                    default: back = true;
                }
            } catch (EmptyStructureException e) {
                ConsoleUI.error(e.getMessage());
            }
        }
    }

    private void push() {
        int value = InputValidator.readInt(scanner, "Enter value to push: ");
        if (stack.push(value)) {
            ConsoleUI.success(value + " pushed.");
        } else {
            ConsoleUI.error("Stack Overflow: the stack is full (capacity " + stack.capacity() + ").");
        }
    }

    private void status() {
        System.out.println("  Size: " + stack.size() + " / " + stack.capacity()
                + " | Empty: " + stack.isEmpty() + " | Full: " + stack.isFull());
    }

    /** Classic stack application: every closing bracket must match the latest unmatched opener. */
    private void checkBrackets() {
        String text = InputValidator.readNonEmptyLine(scanner, "Enter an expression, e.g. {[a+b]*(c-d)}: ");
        ArrayStack<Character> openers = new ArrayStack<>(text.length());
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c == '(' || c == '[' || c == '{') {
                openers.push(c);
            } else if (c == ')' || c == ']' || c == '}') {
                if (openers.isEmpty() || !matches(openers.pop(), c)) {
                    ConsoleUI.error("NOT balanced: unexpected '" + c + "' at position " + i + ".");
                    return;
                }
            }
        }
        if (openers.isEmpty()) ConsoleUI.success("The expression is balanced.");
        else ConsoleUI.error("NOT balanced: " + openers.size() + " opening bracket(s) never closed.");
    }

    private boolean matches(char open, char close) {
        return (open == '(' && close == ')') || (open == '[' && close == ']') || (open == '{' && close == '}');
    }
}
