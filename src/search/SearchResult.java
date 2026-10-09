package search;

/**
 * Immutable record of one search: what was used, whether it succeeded,
 * where the value was found, how many comparisons were made and how long it took.
 *
 * @author MF Sheeraz Gulzum (23da2-0589)
 */
public class SearchResult {
    private final String algorithm;
    private final boolean found;
    private final int index;
    private final long steps;
    private final long nanoTime;

    public SearchResult(String algorithm, boolean found, int index, long steps, long nanoTime) {
        this.algorithm = algorithm;
        this.found = found;
        this.index = index;
        this.steps = steps;
        this.nanoTime = nanoTime;
    }

    public String getAlgorithm() { return algorithm; }
    public boolean isFound() { return found; }
    public int getIndex() { return index; }
    public long getSteps() { return steps; }
    public long getNanoTime() { return nanoTime; }

    @Override
    public String toString() {
        String where = found ? "FOUND at index " + index : "NOT FOUND";
        return String.format("%-22s -> %-20s | steps: %d | time: %d ns", algorithm, where, steps, nanoTime);
    }
}
