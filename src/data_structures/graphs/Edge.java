package data_structures.graphs;

import utils.Geometry;

public class Edge {
    private Node to;
    private Node from;
    private boolean directed;
    private double value; // default: 1

    Edge(Node from, Node to) {
        this(from, to, true);
    }

    Edge(Node from, Node to, boolean directed) {
        this(from,to,directed,1);
    }
    Edge(Node from, Node to, boolean directed,double value) {
        this.to = to;
        this.from = from;
        this.directed = directed;
        this.value = value;
        if (!directed)
            to.registerEdge(this);
        from.registerEdge(this);
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public Node getTo() {
        return to;
    }

    public void setTo(Node to) {
        this.to = to;
    }

    public Node getFrom() {
        return from;
    }

    public void setFrom(Node from) {
        this.from = from;
    }

    public boolean isDirected() {
        return directed;
    }

    public void setDirected(boolean directed) {
        this.directed = directed;
    }

    Node getOtherNode(Node node) {
        if (node.equals(to)) {
            return from;
        } else {
            return to;
        }
    }

    public double getCost() {

        return Geometry.getDistance(from.getLocation(), to.getLocation());
    }


}
