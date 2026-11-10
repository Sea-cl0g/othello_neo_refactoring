package ui;

import layout.Bounds;

public abstract class ComponentInteractive extends Component {
    protected ComponentInteractive(Bounds bounds) {
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
