package ui;

import processing.core.PApplet;

public abstract class Component {
    private BoundingBox bounds;

    protected Component(BoundingBox bouds) {
        this.bounds = bouds;
    }

    protected BoundingBox getBounds() {
        return bounds;
    }

    public boolean handleClick(int px, int py) {
        return false;
    }

    public abstract void draw(PApplet app);
}
