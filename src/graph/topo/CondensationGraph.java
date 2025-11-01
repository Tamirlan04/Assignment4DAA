package graph.topo;

import graph.scc.Graph;
import java.util.*;

public class CondensationGraph {
    private final List<List<Integer>> sccs;
    private final Graph original;
    private final Map<Integer,Integer> compIndex;
    private final Map<Integer,List<Integer>> dagAdj;

    public CondensationGraph(Graph original, List<List<Integer>> sccs) {
        this.original = original;
        this.sccs = sccs;
        this.compIndex = new HashMap<>();
        this.dagAdj = new HashMap<>();
        build();
    }

    private void build() {
        for (int i = 0; i < sccs.size(); i++) {
            for (int v : sccs.get(i)) compIndex.put(v, i);
            dagAdj.put(i, new ArrayList<>());
        }
        for (int u = 0; u < original.size(); u++) {
            int cu = compIndex.get(u);
            for (int v : original.getNeighbors(u)) {
                int cv = compIndex.get(v);
                if (cu != cv && !dagAdj.get(cu).contains(cv))
                    dagAdj.get(cu).add(cv);
            }
        }
    }

    public Map<Integer,List<Integer>> getDagAdj(){ return dagAdj; }
    public int size(){ return sccs.size(); }
}
