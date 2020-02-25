package practice;

import java.util.Arrays;

public class Quick extends Sorts {

    public Quick(int size) {
        super(size, "Quick");
    }

    @Override
    public double sort() {
        //get the time at the start
        double start = System.currentTimeMillis();

        //create a copy, and sort the array for comparing at the end to make sure it's correct
        doSort(array, 0, array.length - 1);
        return (System.currentTimeMillis() - start) / 1000;
    }

    private void doSort(int[] data, int start, int end) {

        if (start >= end) return;

        var boundary = partition(data, start, end);

        doSort(data, start, boundary - 1);
        doSort(data, boundary + 1, end);
    }

    private int partition(int[] array, int start, int end) {
        int pivot = array[end];
        int boundary = start - 1;
        for (int i = start; i <= end; i++) {
            if (array[i] <= pivot) {
                compares++;
                swap(array, i, ++boundary);
            }
        }
        return boundary;
    }
}
