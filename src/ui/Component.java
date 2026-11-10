package ui;

import processing.core.PApplet;
import ui.layout.Bounds;
import ui.layout.Layout;

public abstract class Component {
    private final Bounds bounds = new Bounds(0, 0, 0, 0);
    private Layout layout;

    protected Component(Layout layout) {
        this.layout = layout;
    }

    protected Bounds getBounds() {
        return bounds;
    }

    public Layout getLayout() {
        return layout;
    }

    public void applyLayout(int width, int height) {
        layout.apply(getBounds(), width, height);
    }

    public boolean handleClick(int px, int py) {
        return false;
    }

    public abstract void draw(PApplet app);
}
