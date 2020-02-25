package practice;

public class Merge extends Sorts {

    public Merge(int size) {
        super();
        this.size = size;
    }

    @Override
    public float sort() {
        //get the time at the start
        float start = System.currentTimeMillis();
        System.out.println("Merge");
        return (System.currentTimeMillis() - start / 1000);
    }
}
