package ui.components;

import java.util.List;
import processing.core.PApplet;
import ui.Component;
import ui.layout.Layout;

public class ParallaxGroup extends Component {
    private final float strength;
    private final List<Component> children;
    private int width;
    private int height;

    public ParallaxGroup(float strength, List<Component> children) {
        super(new Layout(0.5f, 0.5f, 1, 1));
        this.strength = strength;
        this.children = List.copyOf(children);
    }

    private int offsetX(int mouseX) {
        return Math.round((mouseX - width / 2f) * strength);
    }

    private int offsetY(int mouseY) {
        return Math.round((mouseY - height / 2f) * strength);
    }

    @Override
    public void applyLayout(int width, int height) {
        super.applyLayout(width, height);
        this.width = width;
        this.height = height;
        for (Component child : children) {
            child.applyLayout(width, height);
        }
    }

    @Override
    public boolean handleClick(int px, int py) {
        for (int i = children.size() - 1; i >= 0; i--) {
            if (children.get(i).handleClick(px - offsetX(px), py - offsetY(py))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean handleHover(int px, int py) {
        for (int i = children.size() - 1; i >= 0; i--) {
            if (children.get(i).handleHover(px - offsetX(px), py - offsetY(py))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void resetHover() {
        for (Component child : children) {
            child.resetHover();
        }
    }

    @Override
    public void clearFocus() {
        for (Component child : children) {
            child.clearFocus();
        }
    }

    @Override
    public boolean handleKeyPressed(PApplet app) {
        for (int i = children.size() - 1; i >= 0; i--) {
            if (children.get(i).handleKeyPressed(app)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void draw(PApplet app) {
        app.pushMatrix();
        app.translate(offsetX(app.mouseX), offsetY(app.mouseY));
        for (Component child : children) {
            child.draw(app);
        }
        app.popMatrix();
    }
}
