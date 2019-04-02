package algorithms;

import data_structures.MinHeap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


public class Sort {


    public static void bubbleSort(ArrayList<Comparable> elems) {
        for (int i = 0; i < elems.size(); i++) {
            for (int j = elems.size() - 1; j > i; j--) {
                if (elems.get(j).compareTo(elems.get(j - 1)) < 0) {
                    Collections.swap(elems, j, j - 1);
                }
            }
        }
    }

    public static void selectionSort(ArrayList<Comparable> elems) {
        ArrayList<Comparable> clone = new ArrayList<>(elems);
        elems.clear();
        while (!clone.isEmpty()) {
            Comparable min = clone.get(0);
            for (Comparable comparable : clone) {
                if (comparable.compareTo(min) < 0) {
                    min = comparable;
                }
            }
            elems.add(min);
            clone.remove(min);
        }
    }

    public static void insertionSort(ArrayList<Comparable> elems) {
        for (int i = 1; i < elems.size(); i++) {
            Comparable elem = elems.get(i);
            for (int j = i - 1; j >= 0; j--) {
                if (elem.compareTo(elems.get(j)) < 0)
                    Collections.swap(elems, j, j + 1);
                else
                    break;
            }
        }
    }

    public static void heapSort(ArrayList<Comparable> elems) {
        var heap = new MinHeap<>(elems);
        elems.clear();
        while (!heap.isEmpty()) {
            elems.add(heap.pop());
        }
    }

    public static void mergeSort(ArrayList<Comparable> elems) {
        Comparable[] array = new Comparable[elems.size()];
        mergeSort(elems.toArray(array));
        for (int i = 0; i < elems.size(); i++) {
            elems.set(i, array[i]);
        }
    }

    public static void mergeSort(Comparable[] elems) {
        merge(elems, elems.length);
    }

    private static void merge(Comparable[] elems, int n) {
        if (n < 2) {
            return;
        }
        int mid = n / 2;
        Comparable[] l = new Comparable[mid];
        Comparable[] r = new Comparable[n - mid];

        System.arraycopy(elems, 0, l, 0, mid);
        System.arraycopy(elems, mid, r, 0, n - mid);
        merge(l, mid);
        merge(r, n - mid);

        mergeSublist(elems, l, r, mid, n - mid);
    }

    private static void mergeSublist(Comparable[] elems, Comparable[] l, Comparable[] r, int left, int right) {
        int i = 0, j = 0, k = 0;
        while (i < left && j < right) {
            if (l[i].compareTo(r[j]) <= 0) {
                elems[k++] = l[i++];
            } else {
                elems[k++] = r[j++];
            }
        }
        while (i < left) {
            elems[k++] = l[i++];
        }
        while (j < right) {
            elems[k++] = r[j++];
        }
    }

    public static void quickSort(ArrayList<Comparable> elems) {
        Comparable[] array = new Comparable[elems.size()];
        quickSort(elems.toArray(array));
        for (int i = 0; i < elems.size(); i++) {
            elems.set(i, array[i]);
        }
    }

    public static void quickSort(Comparable[] elems) {
        quickSort(elems, 0, elems.length - 1);
    }

    private static void quickSort(Comparable arr[], int begin, int end) {
        if (begin < end) {
            int partitionIndex = partition(arr, begin, end);

            quickSort(arr, begin, partitionIndex - 1);
            quickSort(arr, partitionIndex + 1, end);
        }
    }

    private static int partition(Comparable arr[], int begin, int end) {
        Comparable pivot = arr[end];
        int i = (begin - 1);

        for (int j = begin; j < end; j++) {
            if (arr[j].compareTo(pivot) <= 0) {
                i++;
                swap(arr, i, j);
            }
        }

        swap(arr, end, i + 1);

        return i + 1;
    }

    private static void swap(Comparable[] elems, int from, int to) {
        Comparable temp = elems[from];
        elems[from] = elems[to];
        elems[to] = temp;
    }

}
