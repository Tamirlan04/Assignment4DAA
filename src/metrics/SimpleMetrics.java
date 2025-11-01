package metrics;

import java.util.HashMap;
import java.util.Map;

public class SimpleMetrics implements metrics.Metrics {
    private long startTime;
    private long endTime;
    private final Map<String, Integer> counters = new HashMap<>();

    @Override
    public void start() {
        startTime = System.nanoTime();
    }

    @Override
    public void stop() {
        endTime = System.nanoTime();
    }

    @Override
    public void increment(String operation) {
        counters.put(operation, counters.getOrDefault(operation, 0) + 1);
    }

    @Override
    public void print() {
        System.out.println("─── Metrics ───");
        counters.forEach((k, v) -> System.out.println(k + ": " + v));
        System.out.printf("Execution time: %.3f ms%n", (endTime - startTime) / 1e6);
    }
}
