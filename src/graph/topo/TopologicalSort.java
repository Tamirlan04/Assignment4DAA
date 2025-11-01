package graph.topo;

import metrics.SimpleMetrics;
import java.util.*;

public class TopologicalSort {
    public static List<Integer> sort(Map<Integer,List<Integer>> adj, int n, SimpleMetrics metrics) {
        int[] indeg = new int[n];
        for (List<Integer> nbrs : adj.values())
            for (int v : nbrs) indeg[v]++;

        Queue<Integer> q = new ArrayDeque<>();
        for (int i = 0; i < n; i++) if (indeg[i] == 0) q.add(i);

        List<Integer> order = new ArrayList<>();
        metrics.start();
        while (!q.isEmpty()) {
            int u = q.poll();
            order.add(u);
            metrics.increment("Kahn_pop");
            for (int v : adj.getOrDefault(u, new ArrayList<>())) {
                indeg[v]--;
                metrics.increment("Kahn_relax");
                if (indeg[v] == 0) q.add(v);
            }
        }
        metrics.stop();
        return order;
    }
}
