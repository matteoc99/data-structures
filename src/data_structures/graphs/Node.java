package data_structures.graphs;

import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class Node implements Comparable{

    private Graph parent;
    private ArrayList<Edge> edges;
    private int x;
    private int y;
    private int id;
    /**
     * used by algorithms
     */
    private int distanceValue = Integer.MAX_VALUE;

    public Node(Graph parent, int id) {
        this(parent, (int)(Math.random() * 100), (int)(Math.random() * 100), id);
    }

    Node(Graph parent, int x, int y, int id) {
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

    public void setX(int x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getId() {
        return id;
    }

    public int getDistanceValue() {
        return distanceValue;
    }

    public void setDistanceValue(int distanceValue) {
        this.distanceValue = distanceValue;
    }

    public Point getLocation() {
        return new Point(x, y);
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

    @Override
    public int compareTo(Object o) {
        Node other= (Node)o;
        return this.getDistanceValue()-other.getDistanceValue();
    }
}
