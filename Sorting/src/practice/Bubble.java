package practice;

public class Bubble extends Sorts {

    public Bubble(int size) {
        super();
        this.size = size;
    }

    @Override
    public float sort() {
        //get the time at the start
        float start = System.currentTimeMillis();
        System.out.println("Bubble");
        return (System.currentTimeMillis() - start / 1000);
    }
}
