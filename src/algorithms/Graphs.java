package algorithms;

import data_structures.graphs.Graph;
import data_structures.graphs.Node;
import data_structures.graphs.Path;
import data_structures.graphs.Step;

import java.util.ArrayList;
import java.util.Stack;

public class Graphs {
    public static Path DFS(Graph graph, int fromId, int toId) {
        Path ret = new Path();
        Stack<Node> stack = new Stack<>();
        ArrayList<Node> visited = new ArrayList<>();
        Node to = graph.getNodeById(toId);
        Node from = graph.getNodeById(fromId);
        stack.add(from);

        while (!stack.empty()) {
            Node current = stack.pop();
            if (!visited.contains(current)) {
                visited.add(current);
                var neighbours = current.getAllNeighbours();
                for (Node neighbour : neighbours) {
                    if (!visited.contains(neighbour)) {
                        stack.push(neighbour);
                        ret.addStep(new Step(current, neighbour, current.getEdgeTo(neighbour)));
                        if (neighbour.equals(to)) {
                            return ret;
                        }
                    }
                }
            }
        }

        return ret;
    }

    public static boolean isReachableWithBFS(Graph graph, int fromId, int toId) {
        Path ret = new Path();
        ArrayList<Node> queue = new ArrayList<>();
        ArrayList<Node> visited = new ArrayList<>();
        Node to = graph.getNodeById(toId);
        Node from = graph.getNodeById(fromId);
        queue.add(from);

        while (!queue.isEmpty()) {
            Node current = queue.remove(0);

            visited.add(current);
            ArrayList<Node> allNeighbours = current.getAllNeighbours();
            for (Node neighbour : allNeighbours) {
                if (!visited.contains(neighbour)) {
                    queue.add(neighbour);
                    if (neighbour.equals(to)) {
                        return true;
                    }
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

            visited.add(current);
            ArrayList<Node> allNeighbours = current.getAllNeighbours();
            for (Node neighbour : allNeighbours) {
                if (!visited.contains(neighbour)) {
                    queue.add(neighbour);
                    ret.addStep(new Step(current, neighbour, current.getEdgeTo(neighbour)));
                    if (neighbour.equals(to)) {
                        return ret;
                    }
                }
            }
        }
        return ret;
    }

    public static Path dijkstra(Graph graph, int fromId, int toId) {
        Path ret = new Path();
        ArrayList<Node> visited = new ArrayList<>();
        ArrayList<Node> unvisited= new ArrayList<>(graph.getNodes());

        Node to = graph.getNodeById(toId);
        Node from = graph.getNodeById(fromId);

        from.setDistanceValue(0);
        visited.add(from);


        return ret;
    }

    public static Path aStar(Graph graph, int fromId, int toId) {
        Path ret = new Path();

        return ret;
    }
}
