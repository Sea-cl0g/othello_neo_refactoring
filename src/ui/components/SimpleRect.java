package ui.components;

import processing.core.PApplet;
import ui.Component;
import layout.Bounds;
import layout.Layout;

public class SimpleRect extends Component {
    public SimpleRect(Bounds bounds, Layout layout) {
        super(bounds, layout);
    }

    @Override
    public void draw(PApplet app) {
        Bounds bounds = getBounds();

        app.pushStyle();
        app.rectMode(PApplet.CENTER);
        app.noStroke();
        app.fill(255);
        app.rect(
            bounds.getX(), bounds.getY(),
            bounds.getWidth(), bounds.getHeight()
        );
        app.popStyle();
    }
}