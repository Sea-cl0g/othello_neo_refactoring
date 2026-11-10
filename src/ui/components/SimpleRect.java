package ui.components;

import layout.Bounds;
import processing.core.PApplet;
import ui.Component;

public class SimpleRect extends Component {
    public SimpleRect(Bounds bounds) {
        super(bounds);
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