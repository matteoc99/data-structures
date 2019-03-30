package data_structures.graphs;

import java.util.ArrayList;

public class Path {

    private ArrayList<Step> steps;
    private int index = 0;


    public Path() {
        this.steps = new ArrayList<>();
    }

    public void addStep(Step step) {
        steps.add(step);
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
        if (steps.size() >= 2) {
            ArrayList<Step> cleaned = new ArrayList<>();
            ArrayList<Step> clone = new ArrayList<>(steps);

            Node from = clone.get(0).getFrom();
            Step last= clone.remove(clone.size()-1);
            cleaned.add(0,last);

            Node current = last.getFrom();
            while (!clone.isEmpty()|| current.equals(from)){
                Step step = getStepByTo(current);
                if(step==null)
                    break;
                clone.remove(step);
                cleaned.add(0,step);
                current=step.getFrom();
            }
            return cleaned;
        }
        return steps;
    }

    private Step getStepByTo(Node to) {
        for (Step step : steps) {
            if(step.getTo().equals(to))
                return step;
        }

        return null;
    }

    private Step getStepByFrom(Node from) {
        for (Step step : steps) {
            if(step.getFrom().equals(from))
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
}
