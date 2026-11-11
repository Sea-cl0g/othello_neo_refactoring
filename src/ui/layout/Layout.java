package ui.layout;

public class Layout {
    private final float xRatio;
    private final float yRatio;
    private final float widthRatio;
    private final float heightRatio;

    public Layout(float x, float y, float w, float h) {
        xRatio = x;
        yRatio = y;
        widthRatio = w;
        heightRatio = h;
    }

    public void apply(Bounds bounds, int width, int height) {
        bounds.setBounds(
            Math.round(width * xRatio),
            Math.round(height * yRatio),
            Math.round(width * widthRatio),
            Math.round(height * heightRatio)
        );
    }

    public void apply(Bounds bounds, Bounds parent) {
        int parentX = parent.getX() - parent.getWidth() / 2;
        int parentY = parent.getY() - parent.getHeight() / 2;
        bounds.setBounds(
            parentX + Math.round(parent.getWidth() * xRatio),
            parentY + Math.round(parent.getHeight() * yRatio),
            Math.round(parent.getWidth() * widthRatio),
            Math.round(parent.getHeight() * heightRatio)
        );
    }
}
