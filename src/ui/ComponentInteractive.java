package ui;

import layout.Bounds;
import layout.Layout;

public abstract class ComponentInteractive extends Component {
    protected ComponentInteractive(Bounds bounds, Layout layout) {
        super(bounds, layout);
    }

    @Override
    public boolean handleClick(int px, int py) {
        if (!getBounds().contains(px, py)) return false;

        onClick();
        return true;
    }

    protected abstract void onClick();
}
