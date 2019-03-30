package Test;

import data_structures.MinHeap;
import data_structures.graphs.Graph;
import data_structures.graphs.Node;

import java.util.ArrayList;

public class HeapTest {
    public static void main(String[] args) {
        int[] vals = {12, 3, 24, 1, 33, 2, 12, 3, 46, 35};
        ArrayList<Integer> ints = new ArrayList<>();

        for (int val : vals) {
            Integer i = val;
            ints.add(i);
        }

        Integer i1 = 4;
        Integer i2 = 44;

        MinHeap<Integer> heap = new MinHeap<>();
        heap.init(ints);
        System.out.println(heap);
        System.out.println(heap.pop());
        System.out.println(heap);
        System.out.println(heap.pop());
        System.out.println(heap);
        heap.push(i1);
        System.out.println(heap);
        heap.push(i2);
        System.out.println(heap);
        System.out.println(heap.pop());
        System.out.println(heap);
        System.out.println(heap.pop());
        System.out.println(heap);
        System.out.println(heap.pop());
        System.out.println(heap);
        System.out.println(heap.pop());
        System.out.println(heap);
        heap.push(54);
        System.out.println(heap);
        System.out.println(heap.pop());
        System.out.println(heap);
        System.out.println(heap.pop());
        System.out.println(heap);
        System.out.println(heap.pop());
        System.out.println(heap);
        System.out.println(heap.pop());
        System.out.println(heap);
        System.out.println(heap.pop());
        System.out.println(heap);
        System.out.println(heap.pop());
        System.out.println(heap);
        System.out.println(heap.pop());
        System.out.println(heap);
        System.out.println(heap.pop());
        System.out.println(heap);
        System.out.println(heap.pop());

    }
}
