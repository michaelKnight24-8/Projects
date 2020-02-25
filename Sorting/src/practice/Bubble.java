package practice;

import java.util.Arrays;

public class Bubble extends Sorts {

    public Bubble(int size) {
        super(size, "Bubble");
    }

    @Override
    public double sort() {

        //get time at start, then sort!
        double start = System.currentTimeMillis();

        //time for the sort!
        boolean isSorted;
        for (var i = 0; i < array.length; i++) {
            isSorted = true;
            for (var j = 1; j < array.length - i; j++) {
                if (array[j] < array[j - 1]) {
                    compares++;
                    swap(array, j, j - 1);
                    isSorted = false;
                }
            }
            if (isSorted) break;
        }

        //sorted, so now return the time it took!!
        return (System.currentTimeMillis() - start) / 1000;
    }

}
