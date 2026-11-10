package layout;

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
}
