package graph.dagsp;
import metrics.SimpleMetrics;
import java.util.*;

public class DAGPaths {

    public static Map<Integer,Integer> shortest(Map<Integer,List<int[]>> adj,
                                                List<Integer> topo, int src,
                                                SimpleMetrics m) {
        Map<Integer,Integer> dist = new HashMap<>();
        for (int u : topo) dist.put(u, Integer.MAX_VALUE);
        dist.put(src, 0);
        m.start();
        for (int u : topo) {
            if (dist.get(u) != Integer.MAX_VALUE)
                for (int[] e : adj.getOrDefault(u, List.of())) {
                    m.increment("Relax_shortest");
                    int v=e[0],w=e[1];
                    if (dist.get(u)+w<dist.get(v))
                        dist.put(v, dist.get(u)+w);
                }
        }
        m.stop();
        return dist;
    }

    public static Map<Integer,Integer> longest(Map<Integer,List<int[]>> adj,
                                               List<Integer> topo, int src,
                                               SimpleMetrics m) {
        Map<Integer,Integer> dist = new HashMap<>();
        for (int u : topo) dist.put(u, Integer.MIN_VALUE);
        dist.put(src,0);
        m.start();
        for (int u : topo) {
            if (dist.get(u)!=Integer.MIN_VALUE)
                for (int[] e: adj.getOrDefault(u,List.of())){
                    m.increment("Relax_longest");
                    int v=e[0],w=e[1];
                    if (dist.get(u)+w>dist.get(v))
                        dist.put(v,dist.get(u)+w);
                }
        }
        m.stop();
        return dist;
    }
}
