package p55.a2017.bdeb.qc.ca.ibdhelper.Pain;

public class LocationArray {
    public static final int ARRAY_SIZE = 8;
    private boolean[][] position = new boolean[ARRAY_SIZE][ARRAY_SIZE];

    public int size() {
        return ARRAY_SIZE;
    }

    public boolean[][] getPosition() {
        return position;
    }

    public void reset() {
        position = new boolean[ARRAY_SIZE][ARRAY_SIZE];
    }
}
