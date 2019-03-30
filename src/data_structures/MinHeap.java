package data_structures;

import java.util.ArrayList;
import java.util.Collections;

public class MinHeap<T extends Comparable<T>> {

    private ArrayList<T> comparables = new ArrayList<>();

    public T pop() {
        if(comparables.size()==0)return null;
        swap(0, comparables.size() - 1);
        T ret = deleteLast();
        resortAfterFromAbove(0);

        return ret;
    }

    public void push(T comparable) {
        comparables.add(comparable);
        resortAfterFromBelow(comparables.size() - 1);
    }

    public void init(ArrayList<T> comparables) {

        this.comparables.clear();
        for (T comparable : comparables) {
            push(comparable);
        }
    }

    private void resortAfterFromBelow(int child) {
        if (child == 0)
            return;
        int parent = (child - 1) / 2;

        if (comparables.get(child).compareTo(comparables.get(parent)) < 0) {
            swap(child, parent);
        }
        resortAfterFromBelow(parent);
    }

    private void resortAfterFromAbove(int root) {
        if (hasleftChild(root) && hasrightChild(root)) {
            int left = root * 2 + 1;
            int right = root * 2 + 2;
            var leftComp = comparables.get(left);
            var rightComp = comparables.get(right);
            var rootComp = comparables.get(root);
            var minComp = min(leftComp, rightComp);
            if (rootComp.compareTo(minComp) > 0) {
                if (leftComp.compareTo(minComp) == 0) {
                    swap(root, left);
                    resortAfterFromAbove(left);
                }
                if (rightComp.compareTo(minComp) == 0) {
                    swap(root, right);
                    resortAfterFromAbove(right);
                }
            }

        } else if (hasleftChild(root)) {
            int left = root * 2 + 1;
            var leftComp = comparables.get(left);
            var rootComp = comparables.get(root);
            if (rootComp.compareTo(leftComp) > 0) {
                swap(root, left);
            }
            resortAfterFromAbove(left);
        }
    }

    private T min(T c1, T c2) {
        if (c1.compareTo(c2) <= 0)
            return c1;
        return c2;

    }

    private boolean hasleftChild(int root) {
        return root * 2 + 1 < comparables.size();
    }

    private boolean hasrightChild(int root) {
        return root * 2 + 2 < comparables.size();
    }


    private void swap(int indexFrom, int indexTo) {
        Collections.swap(comparables, indexFrom, indexTo);
    }

    private T deleteLast() {
        return comparables.remove(comparables.size() - 1);
    }

    @Override
    public String toString() {
        return "MinHeap{" +
                "comparables=" + comparables +
                '}';
    }
}
