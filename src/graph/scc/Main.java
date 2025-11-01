import com.google.gson.Gson;
import graph.scc.Graph;
import graph.scc.SCCFinder;
import graph.topo.CondensationGraph;
import graph.topo.TopologicalSort;
import graph.dagsp.DAGPaths;
import metrics.SimpleMetrics;
import java.io.FileReader;
import java.util.*;

public class Main {
    static class EdgeInput { int u,v,w; }
    static class GraphInput {
        boolean directed; int n; List<EdgeInput> edges; int source; String weight_model;
    }

    public static void main(String[] args) throws Exception {
        Gson gson = new Gson();
        GraphInput input = gson.fromJson(new FileReader("data/tasks.json"), GraphInput.class);
        if (input == null) { System.err.println("Invalid JSON"); return; }

        System.out.println("=== Assignment 4: Smart City Scheduling ===");
        System.out.println("Weight model: " + input.weight_model + ", Source: " + input.source);

        Graph g = new Graph(input.n);
        for (EdgeInput e: input.edges) g.addEdge(e.u,e.v);

        SimpleMetrics mSCC = new SimpleMetrics();
        SCCFinder scc = new SCCFinder(g,mSCC);
        List<List<Integer>> comps = scc.findSCCs();
        System.out.println("\nStrongly Connected Components (SCC):");
        int i=0; for (List<Integer> c: comps)
            System.out.printf("Component %d (size=%d): %s%n",i++,c.size(),c);
        mSCC.print();

        SimpleMetrics mTopo = new SimpleMetrics();
        CondensationGraph cg = new CondensationGraph(g,comps);
        List<Integer> order = TopologicalSort.sort(cg.getDagAdj(), cg.size(), mTopo);
        System.out.println("\nCondensation DAG: "+cg.getDagAdj());
        System.out.println("Topological order of SCCs: "+order);
        mTopo.print();

        Map<Integer,List<int[]>> adj = new HashMap<>();
        for (EdgeInput e: input.edges)
            adj.computeIfAbsent(e.u,k->new ArrayList<>()).add(new int[]{e.v,e.w});

        List<Integer> topoFull = topologicalDFS(input.n,adj);
        SimpleMetrics mShort=new SimpleMetrics(), mLong=new SimpleMetrics();
        var shortest = DAGPaths.shortest(adj,topoFull,input.source,mShort);
        var longest  = DAGPaths.longest(adj,topoFull,input.source,mLong);

        System.out.println("\nShortest Distances:");
        shortest.forEach((v,d)->System.out.printf("%d → %s%n",v,(d==Integer.MAX_VALUE?"∞":d)));
        mShort.print();

        System.out.println("\nLongest (Critical) Distances:");
        longest.forEach((v,d)->System.out.printf("%d → %s%n",v,(d==Integer.MIN_VALUE?"−∞":d)));
        mLong.print();
    }

    private static List<Integer> topologicalDFS(int n,Map<Integer,List<int[]>> adj){
        boolean[]vis=new boolean[n]; List<Integer>ord=new ArrayList<>();
        for(int i=0;i<n;i++) if(!vis[i]) dfs(i,adj,vis,ord);
        Collections.reverse(ord); return ord;
    }
    private static void dfs(int u,Map<Integer,List<int[]>>adj,boolean[]vis,List<Integer>ord){
        vis[u]=true;
        for(int[]e:adj.getOrDefault(u,List.of()))
            if(!vis[e[0]]) dfs(e[0],adj,vis,ord);
        ord.add(u);
    }
}
