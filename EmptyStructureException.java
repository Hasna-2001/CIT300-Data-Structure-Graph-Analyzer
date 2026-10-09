package ds;

/**
 * Thrown when an operation (pop, dequeue, peek ...) is attempted on an empty
 * data structure. Lets the menus show a friendly message instead of crashing.
 *
 * @author R Halidha Nashath (23da2-0535)
 */
public class EmptyStructureException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public EmptyStructureException(String message) {
        super(message);
    }
}
