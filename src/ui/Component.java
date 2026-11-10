package ui;

import layout.Bounds;
import layout.Layout;
import processing.core.PApplet;

public abstract class Component {
    private Bounds bounds;
    private Layout layout;

    protected Component(Bounds bouds) {
        this.bounds = bouds;
    }

    protected Bounds getBounds() {
        return bounds;
    }

    public void applyLayout(int width, int height){
        layout.apply(getBounds(), width, height);
    }

    public boolean handleClick(int px, int py) {
        return false;
    }

    public abstract void draw(PApplet app);
}
