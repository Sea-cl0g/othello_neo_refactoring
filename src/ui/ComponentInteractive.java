package ui;

public abstract class ComponentInteractive extends Component {
    protected ComponentInteractive(BoundingBox bounds) {
        super(bounds);
    }

    @Override
    public boolean handleClick(int px, int py) {
        if (!getBounds().contains(px, py)) return false;

        onClick();
        return true;
    }

    protected abstract void onClick();
}
