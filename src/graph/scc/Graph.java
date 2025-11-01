package graph.scc;
import java.util.*;

public class Graph {
    private final int n;
    private final Map<Integer, List<Integer>> adj;

    public Graph(int n) {
        this.n = n;
        adj = new HashMap<>();
        for (int i = 0; i < n; i++) adj.put(i, new ArrayList<>());
    }

    public void addEdge(int u, int v) { adj.get(u).add(v); }
    public List<Integer> getNeighbors(int u) { return adj.get(u); }
    public int size() { return n; }
}
