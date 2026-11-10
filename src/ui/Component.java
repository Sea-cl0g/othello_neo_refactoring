package ui;

import layout.Bounds;
import layout.Layout;
import processing.core.PApplet;

public abstract class Component {
    private final Bounds bounds = new Bounds(0, 0, 0, 0);
    private Layout layout;

    protected Component(Layout layout) {
        this.layout = layout;
    }

    protected Bounds getBounds() {
        return bounds;
    }

    public void applyLayout(int width, int height) {
        layout.apply(getBounds(), width, height);
    }

    public boolean handleClick(int px, int py) {
        return false;
    }

    public abstract void draw(PApplet app);
}
