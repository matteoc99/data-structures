package utils;

import java.util.ArrayList;

public class Converter {
    public static double[] toDoubleArray(ArrayList<Double> list) {
        double[] array = new double[list.size()];
        for (int i = 0; i < array.length; i++) {
            array[i]=list.get(i);
        }
        return array;
    }
}
