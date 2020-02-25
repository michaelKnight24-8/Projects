package practice;

public class Quick extends Sorts {

    public Quick(int size) {
        super();
        this.size = size;
    }

    @Override
    public float sort() {
        //get the time at the start
        float start = System.currentTimeMillis();

        int [] array = new int [size];

        //now fill out the array with random values!
        System.out.println("Quick");
        return (System.currentTimeMillis() - start / 1000);
    }
}
