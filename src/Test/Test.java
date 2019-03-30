package Test;

import algorithms.Graphs;
import data_structures.graphs.Graph;
import data_structures.graphs.Path;

import java.util.Queue;
import java.util.Stack;

public class Test {
    public static void main(String[] args) {
        Graph g = Graph.generateDefault(1000,20002);
        System.out.print(g);
        System.out.println(Graphs.isReachableWithBFS(g,0,9));
        Path p = Graphs.DFS(g,0,9);
        System.out.println(p);
        p.applyCleanUp();
        System.out.println(p);
    }
}
