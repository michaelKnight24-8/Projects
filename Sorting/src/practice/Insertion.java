package practice;

public class Insertion extends Sorts {

    public Insertion(int size) {
        super();
        this.size = size;
    }

    @Override
    public float sort() {
        //get the time at the start
        float start = System.currentTimeMillis();
        System.out.println("insertion");
        return (System.currentTimeMillis() - start / 1000);
    }
}
