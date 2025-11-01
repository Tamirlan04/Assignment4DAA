package graph.utils;

import com.google.gson.Gson;
import java.io.FileWriter;
import java.util.*;

public class DatasetGenerator {

    static class Edge {
        int u, v, w;
        Edge(int u, int v, int w) { this.u = u; this.v = v; this.w = w; }
    }

    static class GraphData {
        boolean directed = true;
        int n;
        List<Edge> edges;
        int source = 0;
        String weight_model = "edge";
    }

    public static void main(String[] args) throws Exception {
        generateAllDatasets();
    }

    public static void generateAllDatasets() throws Exception {
        Random rand = new Random();
        Gson gson = new Gson();

        generateCategory("small", 6, 10, 3, rand, gson);
        generateCategory("medium", 10, 20, 3, rand, gson);
        generateCategory("large", 20, 50, 3, rand, gson);

        System.out.println("✅ 9 datasets generated successfully in /data/");
    }

    private static void generateCategory(String name, int minNodes, int maxNodes, int count,
                                         Random rand, Gson gson) throws Exception {
        for (int i = 1; i <= count; i++) {
            GraphData g = new GraphData();
            g.n = rand.nextInt(maxNodes - minNodes + 1) + minNodes;
            g.edges = new ArrayList<>();

            int maxEdges = g.n * 2;
            for (int j = 0; j < maxEdges; j++) {
                int u = rand.nextInt(g.n);
                int v = rand.nextInt(g.n);
                if (u != v) {
                    int w = rand.nextInt(9) + 1;
                    g.edges.add(new Edge(u, v, w));
                }
            }


            if (g.n >= 6) {
                for (int c = 0; c < rand.nextInt(2) + 1; c++) {
                    int a = rand.nextInt(g.n);
                    int b = rand.nextInt(g.n);
                    if (a != b) {
                        g.edges.add(new Edge(a, b, rand.nextInt(9) + 1));
                        g.edges.add(new Edge(b, a, rand.nextInt(9) + 1));
                    }
                }
            }

            String filename = "data/" + name + "_" + i + ".json";
            try (FileWriter w = new FileWriter(filename)) {
                gson.toJson(g, w);
            }
            System.out.println("Generated " + filename + " with " + g.n + " nodes and " + g.edges.size() + " edges");
        }
    }
}
