package algorithms;

import data_structures.MinHeap;
import data_structures.graphs.*;

import java.util.ArrayList;
import java.util.Stack;

public class Graphs {
    public static Path DFS(Graph graph, int fromId, int toId) {
        Node to = graph.getNodeById(toId);
        Node from = graph.getNodeById(fromId);
        Path ret = new Path(from,to);

        Stack<Node> stack = new Stack<>();
        ArrayList<Node> visited = new ArrayList<>();

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
        Node to = graph.getNodeById(toId);
        Node from = graph.getNodeById(fromId);

        ArrayList<Node> queue = new ArrayList<>();
        ArrayList<Node> visited = new ArrayList<>();

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
        Node to = graph.getNodeById(toId);
        Node from = graph.getNodeById(fromId);
        Path ret = new Path(from,to);
        ArrayList<Node> queue = new ArrayList<>();
        ArrayList<Node> visited = new ArrayList<>();

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

        Node to = graph.getNodeById(toId);
        Node from = graph.getNodeById(fromId);
        Path ret = new Path(from,to);

        ArrayList<Node> visited = new ArrayList<>();
        from.setDistanceValue(0);
        MinHeap<Node> unvisited= new MinHeap<>(graph.getNodes());

        while (!unvisited.isEmpty()){
            Node current = unvisited.pop();
            if(current.getDistanceValue()==Integer.MAX_VALUE) // unreachable
                return ret;
            visited.add(current);
            var neighbours = current.getAllNeighbours();
            for (Node neighbour : neighbours) {
                int val =neighbour.getDistanceValue();
                Edge smallestEdgeTo = current.getSmallestEdgeTo(neighbour);
                double newVal = smallestEdgeTo.getValue() + current.getDistanceValue();
                if(val > newVal){
                    neighbour.setDistanceValue((int) newVal);
                    ret.addStep(current,neighbour,smallestEdgeTo);
                }
                int index = unvisited.indexOf(neighbour);
                unvisited.swim(index);
            }
        }
        return ret;
    }

    public static Path aStar(Graph graph, int fromId, int toId) {
        Node to = graph.getNodeById(toId);
        Node from = graph.getNodeById(fromId);
        Path ret = new Path(from,to);


        return ret;
    }
}
