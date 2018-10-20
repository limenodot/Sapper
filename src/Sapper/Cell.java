package application;

public class Cell {
    protected int x;
    protected int y;
    protected boolean isMined;

    public Cell(int x, int y, boolean isMined) {
        this.x = x;
        this.y = y;
        this.isMined = isMined;
    }
}
