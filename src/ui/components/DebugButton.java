package ui.components;

import layout.Bounds;
import layout.Layout;
import processing.core.PApplet;
import ui.ComponentInteractive;

public class DebugButton extends ComponentInteractive {
    boolean clicked = false;

    public DebugButton(Bounds bounds, Layout layout) {
        super(bounds, layout);
    }

    @Override
    public void draw(PApplet app) {
        Bounds bounds = getBounds();

        app.pushStyle();
        app.rectMode(PApplet.CENTER);
        app.noStroke();
        if (clicked) {
            app.fill(0, 255, 0);
        } else {
            app.fill(255, 0, 0);
        }
        app.rect(
                bounds.getX(), bounds.getY(),
                bounds.getWidth(), bounds.getHeight());
        app.popStyle();
    }

    @Override
    public void onClick() {
        clicked = !clicked;
    }
}
