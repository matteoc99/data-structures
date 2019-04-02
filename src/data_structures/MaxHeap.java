package data_structures;

import java.util.ArrayList;
import java.util.Collections;

public class MaxHeap<T extends Comparable<T>> {

    private ArrayList<T> comparables = new ArrayList<>();

    public MaxHeap() {
    }

    public MaxHeap(ArrayList<T> comparables) {
        init(comparables);
    }

    public T pop() {
        if (comparables.size() == 0) return null;
        swap(0, comparables.size() - 1);
        T ret = deleteLast();
        sink(0);

        return ret;
    }

    public T peek() {
        if (comparables.size() == 0) return null;
        return comparables.get(0);
    }

    public void push(T comparable) {
        comparables.add(comparable);
        swim(comparables.size() - 1);
    }

    public void init(ArrayList<T> comparables) {
        this.comparables.clear();
        this.comparables.addAll(comparables);

        for (int i = Math.max(0, (comparables.size() / 2) - 1); i >= 0; i--)
            sink(i);

        /*for (T comparable : comparables) {
            push(comparable);
        }*///old method
    }

    public boolean isEmpty() {
        return comparables.isEmpty();
    }

    public int indexOf(T t) {
        return comparables.indexOf(t);
    }

    public void swim(int child) {
        if (child <= 0)
            return;
        int parent = (child - 1) / 2;

        if (comparables.get(child).compareTo(comparables.get(parent)) > 0) {
            swap(child, parent);
        }
        swim(parent);
    }

    public void sink(int root) {
        if (hasleftChild(root) && hasrightChild(root)) {
            int left = root * 2 + 1;
            int right = root * 2 + 2;
            var leftComp = comparables.get(left);
            var rightComp = comparables.get(right);
            var rootComp = comparables.get(root);
            var maxComp = max(leftComp, rightComp);
            if (rootComp.compareTo(maxComp) < 0) {
                if (leftComp.compareTo(maxComp) == 0) {
                    swap(root, left);
                    sink(left);
                }
                if (rightComp.compareTo(maxComp) == 0) {
                    swap(root, right);
                    sink(right);
                }
            }

        } else if (hasleftChild(root)) {
            int left = root * 2 + 1;
            var leftComp = comparables.get(left);
            var rootComp = comparables.get(root);
            if (rootComp.compareTo(leftComp) < 0) {
                swap(root, left);
            }
            sink(left);
        }
    }

    private T max(T c1, T c2) {
        if (c1.compareTo(c2) >= 0)
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

    /*  @Override
    public String toString() {

        StringBuilder ret = new StringBuilder();
        int levels = (int) Math.ceil(Math.sqrt(comparables.size()));

        for (int i = 0; i < levels; i++) {
            for (int j = (int) Math.pow(i, 2); j < comparables.size(); j++) {
                if (j >= Math.pow(i + 1, 2)) {
                    break;
                }
                System.out.print(comparables.get(j)+" ");
            }
            System.out.println();
        }

        return ret.toString();
    }*/
}
