package data_structures.graphs;

import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class Node {

    private Graph parent;
    private ArrayList<Edge> edges;
    private double x;
    private double y;
    private int id;

    Node(Graph parent, int id) {
        this(parent, Math.random() * 100, Math.random() * 100, id);
    }

    Node(Graph parent, double x, double y, int id) {
        this.parent = parent;
        this.x = x;
        this.y = y;
        this.id = id;
        edges = new ArrayList<>();
        parent.registerNode(this);
    }


    public Graph getParent() {
        return parent;
    }

    public void setParent(Graph parent) {
        this.parent = parent;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public int getId() {
        return id;
    }

    public Point getLocation() {
        return new Point((int) x, (int) y);
    }


    public void connectTo(Node to, boolean directed) {
        new Edge(this, to, directed);
    }

    public void connectTo(Node to) {
        new Edge(this, to);
    }

    public ArrayList<Node> getAllNeighbours() {
        ArrayList<Node> ret = new ArrayList<>();
        for (Edge edge : edges) {
            Node to = edge.getOtherNode(this);
            if (!ret.contains(to))
                ret.add(to);
        }
        return ret;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return this.id == node.id &&
                this.parent.equals(node.parent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parent);
    }

    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder();
        ret.append("Node-").append(getId()).append(" :");
        ret.append("\n");
        for (Edge edge : edges) {
            ret.append("\t").append("\t").append("To: ").append(edge.getOtherNode(this).getId());
            ret.append("\n");
        }
        return ret.toString();
    }

    void registerEdge(Edge edge) {
        edges.add(edge);
    }

    public Edge getEdgeTo(Node neighbour) {
        for (Edge edge : edges) {
            if(edge.getOtherNode(this).equals(neighbour))
                return edge;
        }
        return null;
    }
}
