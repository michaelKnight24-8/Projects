package practice;

public class Selection extends Sorts {

    public Selection(int size) {
        super();
        this.size = size;
    }

    @Override
    public float sort() {
        //get the time at the start
        float start = System.currentTimeMillis();
        System.out.println("selection");
        return (System.currentTimeMillis() - start / 1000);
    }
}
