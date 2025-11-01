package graph.scc;

import metrics.SimpleMetrics;
import java.util.*;

public class SCCFinder {
    private final graph.scc.Graph graph;
    private final SimpleMetrics metrics;
    private int time;
    private final int[] disc, low;
    private final boolean[] inStack;
    private final Deque<Integer> stack;
    private final List<List<Integer>> sccList;

    public SCCFinder(graph.scc.Graph graph, SimpleMetrics metrics) {
        this.graph = graph;
        this.metrics = metrics;
        int n = graph.size();
        disc = new int[n];
        low = new int[n];
        inStack = new boolean[n];
        stack = new ArrayDeque<>();
        sccList = new ArrayList<>();
        Arrays.fill(disc, -1);
    }

    public List<List<Integer>> findSCCs() {
        metrics.start();
        for (int i = 0; i < graph.size(); i++)
            if (disc[i] == -1) dfs(i);
        metrics.stop();
        return sccList;
    }

    private void dfs(int u) {
        metrics.increment("DFS_visit");
        disc[u] = low[u] = ++time;
        stack.push(u);
        inStack[u] = true;

        for (int v : graph.getNeighbors(u)) {
            metrics.increment("DFS_edge");
            if (disc[v] == -1) {
                dfs(v);
                low[u] = Math.min(low[u], low[v]);
            } else if (inStack[v]) {
                low[u] = Math.min(low[u], disc[v]);
            }
        }

        if (low[u] == disc[u]) {
            List<Integer> comp = new ArrayList<>();
            int w;
            do {
                w = stack.pop();
                inStack[w] = false;
                comp.add(w);
            } while (w != u);
            sccList.add(comp);
        }
    }
}
