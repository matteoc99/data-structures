package Test;

import algorithms.Sort;
import data_structures.MinHeap;
import data_structures.graphs.Graph;
import data_structures.graphs.Node;

import java.util.ArrayList;
import java.util.Arrays;

public class HeapTest {
    public static void main(String[] args) {
        int[] vals = {1,4,3,510,12,11,1,2,13,3,14,5,23};
        ArrayList<Comparable> ints = new ArrayList<>();

        for (int val : vals) {
            Integer i = val;
            ints.add(i);
        }

        Integer i1 = 4;
        Integer i2 = 44;
        Sort.quickSort(ints);
        System.out.println(ints);

    }

}
