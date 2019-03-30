package data_structures.graphs;

public class Step {
    private Node from;
    private Node to;
    private Edge over;

    public Step(Node from, Node to, Edge over) {
        this.from = from;
        this.to = to;
        this.over = over;
    }

    public Node getFrom() {
        return from;
    }

    public void setFrom(Node from) {
        this.from = from;
    }

    public Node getTo() {
        return to;
    }

    public void setTo(Node to) {
        this.to = to;
    }

    public Edge getOver() {
        return over;
    }

    public void setOver(Edge over) {
        this.over = over;
    }

    @Override
    public String toString() {
        return "Step: " + from.getId() +" --> "+ to.getId();
    }
}
