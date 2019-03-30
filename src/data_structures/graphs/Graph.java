package data_structures.graphs;

import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class Graph {

    private ArrayList<Node> nodes;
    private AtomicInteger idCounter = new AtomicInteger(0);


    public Graph() {
        this(new ArrayList<>());
    }


    public Graph(ArrayList<Node> nodes) {
        this.nodes = nodes;
    }


    public void setNodes(ArrayList<Node> nodes) {
        this.nodes = nodes;
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }

    public Node getNodeById(int id) {

        for (Node node : nodes)
            if (node.getId() == id)
                return node;
        return null;
    }


    public Node getRandomNode() {
        return nodes.get((int) (Math.random() * nodes.size()));
    }

    public boolean removeNode(Node node) {
        return nodes.remove(node);
    }

    public Node addNode() {
        return new Node(this, idCounter.getAndIncrement());
    }

    public Node addNodeAt(Point point) {
        return new Node(this,point.getX(),point.getY(), idCounter.getAndIncrement());
    }

    public static Graph generateDefault(int numOfNodes, int numOfEdges) {
        Graph ret = new Graph();
        for (int i = 0; i < numOfNodes; i++) {
            ret.addNode();
        }
        System.out.println("Nodes added");
        for (int i = 0; i < numOfEdges; i++) {
            ret.getRandomNode().connectTo(ret.getRandomNode(),false);
        }
        System.out.println("Edges added");
        return ret;
    }

    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder();
        ret.append("Graph:");
        ret.append("\n");
        for (Node node : nodes) {
            ret.append("\t").append(node.toString());
            ret.append("\n");
        }
        return ret.toString();
    }

    void registerNode(Node node) {
        nodes.add(node);
    }
}
