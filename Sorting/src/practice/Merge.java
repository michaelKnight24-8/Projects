package practice;

import java.util.Arrays;

public class Merge extends Sorts {

    public Merge(int size) {
        super(size, "Merge");
    }

    @Override
    public double sort() {
        //get the time at the start
        double start = System.currentTimeMillis();
        doSort(array);
        return (System.currentTimeMillis() - start) / 1000;
    }
    private void doSort(int[] data) {

        if (data.length < 2) return;

        //now sort!!
        var middle = data.length / 2;

        int[] leftSide = new int[middle];

        for (int i = 0; i < middle; i++)
            leftSide[i] = data[i];

        int[] rightSide = new int [data.length - middle];

        for (int i = middle; i < data.length; i++)
            rightSide[i - middle] = data[i];

        doSort(leftSide);
        doSort(rightSide);

        merge(leftSide, rightSide, data);
    }

    private void merge(int[] left, int[] right, int[] result) {

        int i = 0, j = 0, k = 0;

        while (i < left.length && j < right.length) {
            if (left[i] <= right[j]) {
                compares++;
                result[k++] = left[i++];
            } else {
                compares++;
                result[k++] = right[j++];
            }
        }

        while (i < left.length)
            result[k++] = left[i++];

        while (j < right.length)
            result[k++] = right[j++];
    }
}
