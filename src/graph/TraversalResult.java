package graph;

import java.util.List;

/**
 * Result of one BFS/DFS run: visit order, whether an optional target was found,
 * steps (vertices visited + adjacency entries examined), peak size of the
 * queue/stack frontier, and elapsed time.
 *
 * @author MS Faathima Hasna (23da2-1093)
 */
public class TraversalResult {
    private final String algorithm;
    private final String start;
    private final String target;      // null for a full traversal
    private final boolean targetFound;
    private final List<String> order;
    private final long steps;
    private final int peakFrontier;
    private final long nanoTime;

    public TraversalResult(String algorithm, String start, String target, boolean targetFound,
                           List<String> order, long steps, int peakFrontier, long nanoTime) {
        this.algorithm = algorithm;
        this.start = start;
        this.target = target;
        this.targetFound = targetFound;
        this.order = order;
        this.steps = steps;
        this.peakFrontier = peakFrontier;
        this.nanoTime = nanoTime;
    }

    public String getAlgorithm() { return algorithm; }
    public String getStart() { return start; }
    public String getTarget() { return target; }
    public boolean isTargetFound() { return targetFound; }
    public List<String> getOrder() { return order; }
    public long getSteps() { return steps; }
    public int getPeakFrontier() { return peakFrontier; }
    public long getNanoTime() { return nanoTime; }
    public int getVisitedCount() { return order.size(); }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(algorithm).append(" from ").append(start);
        if (target != null) {
            sb.append(" searching for ").append(target)
              .append(targetFound ? " -> FOUND" : " -> NOT FOUND");
        }
        sb.append("\n  Visit order : ").append(String.join(" -> ", order));
        sb.append("\n  Visited     : ").append(order.size()).append(" vertices");
        sb.append("\n  Steps       : ").append(steps);
        sb.append("\n  Peak frontier size : ").append(peakFrontier);
        sb.append("\n  Time        : ").append(nanoTime).append(" ns");
        return sb.toString();
    }
}
