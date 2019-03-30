package Test;

import algorithms.Graphs;
import data_structures.graphs.Graph;
import data_structures.graphs.Path;

public class Test {
    public static void main(String[] args) {
        Graph g = Graph.generateDefault(10,22);
        System.out.print(g);
        System.out.println(Graphs.isReachableBFS(g,0,9));
        Path p = Graphs.BFS(g,0,9);
        System.out.println(p);
        p.applyCleanUp();
        System.out.println(p);
    }
}
