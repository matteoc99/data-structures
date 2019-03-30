package data_structures.graphs;

import java.util.ArrayList;

public class Path {

    private ArrayList<Step> steps;
    private int index = 0;
    private Node to;
    private Node from;

    public Path( Node from,Node to) {
        this();
        this.to = to;
        this.from = from;
    }

    Path() {
        this.steps = new ArrayList<>();
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

    public void addStep(Step step) {
        steps.add(step);
    }

    public void addStep(Node current, Node neighbour, Edge smallestEdgeTo) {
        steps.add(new Step(current, neighbour, smallestEdgeTo));
    }

    public void reset() {
        index = 0;
    }

    public Step getNext() {
        index++;
        try {
            return steps.get(index);
        } catch (IndexOutOfBoundsException ignored) {
            return null;
        }
    }

    public ArrayList<Step> cleanUp() {
        ArrayList<Step> clone = new ArrayList<>(steps);
        if (steps.size() >= 2) {
            ArrayList<Step> cleaned = new ArrayList<>();

            Step last = getStepByTo(to);

            if (last != null) {
                cleaned.add(0, last);
                Node current = last.getFrom();
                while (!clone.isEmpty() || current.equals(from)) {
                    Step step = getStepByTo(current);
                    if (step == null)
                        break;
                    clone.remove(step);
                    cleaned.add(0, step);
                    current = step.getFrom();
                }
                return cleaned;
            }else{
                clone.clear();
            }
        }
        return clone;
    }

    private Step getStepByTo(Node to) {
        for (int i = steps.size() - 1; i >= 0; i--) {
            if (steps.get(i).getTo().equals(to))
                return steps.get(i);
        }

        return null;
    }

    private Step getStepByFrom(Node from) {
        for (Step step : steps) {
            if (step.getFrom().equals(from))
                return step;
        }
        return null;
    }

    public void applyCleanUp() {
        steps = cleanUp();
    }

    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder();
        ret.append("Path:");
        ret.append("\n");
        for (Step step : steps) {
            ret.append("\t").append(step.toString());
            ret.append("\n");
        }
        return ret.toString();

    }

    public boolean isEmpty() {
        return steps.size() == 0;
    }


}
