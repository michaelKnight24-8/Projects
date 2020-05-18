package practice;

import java.util.Arrays;

public class Selection extends Sorts {

    public Selection(int size) {
        super(size, "Selection");
    }

    @Override
    public double sort() {
        //get the time at the start
        double start = System.currentTimeMillis();

        for (int i = 0; i < size; i++) {
            int min = i;
            for (int j = i; j < size; j++) {
                if (array[j] < array[min]) {
                    min = j;
                    compares++;
                }
            }
            swap(array, min, i);
        }

        return (System.currentTimeMillis() - start) / 1000;
    }
}
