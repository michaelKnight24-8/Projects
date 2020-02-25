package practice;

import java.util.Arrays;

// abstract class so that I can create an array of the sorts, making for
  // easy traversal of all the sorts for the compare sorts functionality
  // all other sorts inherit from this base class
public abstract class Sorts {

    protected long compares;
    protected int size;
    protected int [] array;
    private String name;

    //called by every child class, since what is done here is shared by every sorting
    //algorithm
    public Sorts(int size, String name) {
        this.name = name;
        this.size = size;
        compares = 0;
        array = new int [size];

        //now populate the array with random values 0 - 500
        for (int i = 0; i < size; i++) {
            array[i] = (int) (Math.random() * size);
        }

    }

    protected void swap(int [] array, int first, int second) {
        var temp = array[first];
        array[first] = array[second];
        array[second] = temp;
    }

    public String getName() { return name; }

    public long getCompares() { return compares; }

    public abstract double sort();

}
