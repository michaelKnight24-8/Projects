package practice;

import java.util.Arrays;

public class Insertion extends Sorts {

    public Insertion(int size) {
        super(size, "Insertion");
    }

    @Override
    public double sort() {

        //get the time at the start
        double start = System.currentTimeMillis();

        //now sort!!
        for (int i = 1; i < array.length; i++) {
            int current = array[i];
            int j = i - 1;
            while (j >= 0 && array[j] > current) {
                array[j + 1] = array[j];
                j--;
                compares++;
            }
            array[j + 1] = current;
        }

        return (System.currentTimeMillis() - start) / 1000;
    }
}
