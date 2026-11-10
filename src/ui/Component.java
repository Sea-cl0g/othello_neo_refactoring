package ui;

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
}
