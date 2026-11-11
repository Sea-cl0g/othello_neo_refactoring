package ui;

import ui.layout.Layout;

public abstract class ComponentInteractive extends Component {
    protected boolean isHoverd;

    protected ComponentInteractive(Layout layout) {
        super(layout);
    }

    @Override
    public boolean handleClick(int px, int py) {
        if (!getBounds().contains(px, py))
            return false;

        onClick();
        return true;
    }

    @Override
    public boolean handleHover(int px, int py) {
        if (!getBounds().contains(px, py)) {
            isHoverd = false;
            return false;
        }

        isHoverd = true;
        onHover();
        return true;
    }

    public void resetHover() {
        isHoverd = false;
    }

    protected abstract void onClick();

    protected abstract void onHover();
}
