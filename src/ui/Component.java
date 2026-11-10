package ui;

import layout.Bounds;
import processing.core.PApplet;

public abstract class Component {
    private Bounds bounds;

    protected Component(Bounds bouds) {
        this.bounds = bouds;
    }

    protected Bounds getBounds() {
        return bounds;
    }

    public boolean handleClick(int px, int py) {
        return false;
    }

    public abstract void draw(PApplet app);
}
