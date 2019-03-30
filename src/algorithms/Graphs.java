package algorithms;

import data_structures.graphs.Graph;
import data_structures.graphs.Node;
import data_structures.graphs.Path;
import data_structures.graphs.Step;

import java.util.ArrayList;

public class Graphs {
    public static Path reachableDFS(Graph graph, int fromId, int toId) {
        Path ret = new Path();

        return ret;
    }

    public static boolean isReachableBFS(Graph graph, int fromId, int toId) {
        Path ret = new Path();
        ArrayList<Node> queue = new ArrayList<>();
        ArrayList<Node> visited = new ArrayList<>();
        Node to = graph.getNodeById(toId);
        Node from = graph.getNodeById(fromId);
        queue.add(from);

        while (!queue.isEmpty()) {
            Node current = queue.remove(0);
            if(current.equals(to)) {
                return true;
            }
            visited.add(current);
            ArrayList<Node> allNeighbours = current.getAllNeighbours();
            for (Node neighbour : allNeighbours) {
                if(!visited.contains(neighbour)){
                    queue.add(neighbour);
                }
            }

        }
        return false;
    }


    public static Path BFS(Graph graph, int fromId, int toId) {
        Path ret = new Path();
        ArrayList<Node> queue = new ArrayList<>();
        ArrayList<Node> visited = new ArrayList<>();
        Node to = graph.getNodeById(toId);
        Node from = graph.getNodeById(fromId);
        queue.add(from);

        while (!queue.isEmpty()) {
            Node current = queue.remove(0);
            if(current.equals(to)) {
                return ret;
            }
            visited.add(current);
            ArrayList<Node> allNeighbours = current.getAllNeighbours();
            for (Node neighbour : allNeighbours) {
                if(!visited.contains(neighbour)){
                    queue.add(neighbour);
                    ret.addStep(new Step(current, neighbour,current.getEdgeTo(neighbour)));
                    if(neighbour.equals(to)) {
                        return ret;
                    }
                }
            }
        }
        return ret;
    }
    public static Path djikstra(Graph graph, int fromId, int toId) {
        Path ret = new Path();


        return ret;
    }

    public static Path aStar(Graph graph, int fromId, int toId) {
        Path ret = new Path();

        return ret;
    }
}
