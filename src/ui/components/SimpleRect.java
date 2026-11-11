package ui.components;

import processing.core.PApplet;
import ui.Component;
import ui.layout.Bounds;
import ui.layout.Layout;

public class SimpleRect extends Component {
    private final int color;

    public SimpleRect(Layout layout) {
        this(layout, 0xFFFFFFFF);
    }

    public SimpleRect(Layout layout, int color) {
        super(layout);
        this.color = color;
    }

    @Override
    public void draw(PApplet app) {
        Bounds bounds = getBounds();
        app.pushStyle();
        app.rectMode(PApplet.CENTER);
        app.noStroke();
        app.fill(color);
        app.rect(bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight());
        app.popStyle();
    }
}