package ui;

public class BoundingBox {
    private int x;
    private int y;
    private int w;
    private int h;

    private BoundingBox(int x, int y, int w, int h){
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public boolean contains(int px, int py){
        return Math.abs(x - px) < w / 2 && Math.abs(y - py) < h / 2;
    }
}
