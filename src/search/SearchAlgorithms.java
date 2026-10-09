package search;

/**
 * Searching algorithms over an int array. Every algorithm counts its comparisons
 * ("steps") so the Performance module can compare them objectively.
 *
 * Linear search : O(n)       - works on any array
 * Binary search : O(log n)   - requires a SORTED array
 *
 * @author MF Sheeraz Gulzum (23da2-0589)
 */
public final class SearchAlgorithms {

    private SearchAlgorithms() { }

    public static SearchResult linearSearch(int[] data, int size, int target) {
        long start = System.nanoTime();
        long steps = 0;
        int foundAt = -1;
        for (int i = 0; i < size; i++) {
            steps++;
            if (data[i] == target) {
                foundAt = i;
                break;
            }
        }
        return new SearchResult("Linear Search", foundAt >= 0, foundAt, steps, System.nanoTime() - start);
    }

    public static SearchResult binarySearch(int[] data, int size, int target) {
        if (!isSorted(data, size)) {
            throw new IllegalArgumentException("Binary search requires a sorted array.");
        }
        long start = System.nanoTime();
        long steps = 0;
        int low = 0;
        int high = size - 1;
        int foundAt = -1;
        while (low <= high) {
            steps++;
            int mid = low + (high - low) / 2;   // avoids int overflow
            if (data[mid] == target) {
                foundAt = mid;
                break;
            } else if (data[mid] < target) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return new SearchResult("Binary Search", foundAt >= 0, foundAt, steps, System.nanoTime() - start);
    }

    public static boolean isSorted(int[] data, int size) {
        for (int i = 1; i < size; i++) {
            if (data[i - 1] > data[i]) return false;
        }
        return true;
    }
}
