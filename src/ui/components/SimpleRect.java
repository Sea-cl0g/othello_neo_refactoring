package ui.components;

import processing.core.PApplet;
import ui.BoundingBox;
import ui.Component;

public class SimpleRect extends Component {
    public SimpleRect(BoundingBox bounds) {
        super(bounds);
    }

    @Override
    public void draw(PApplet app) {
        BoundingBox bounds = getBounds();

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