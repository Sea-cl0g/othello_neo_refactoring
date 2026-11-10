package ui.components;

import processing.core.PApplet;
import ui.Component;
import ui.layout.Bounds;
import ui.layout.Layout;

public class SimpleRect extends Component {
    public SimpleRect(Layout layout) {
        super(layout);
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
                bounds.getWidth(), bounds.getHeight());
        app.popStyle();
    }
}