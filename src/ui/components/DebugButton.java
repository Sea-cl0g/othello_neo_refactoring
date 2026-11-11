package ui.components;

import processing.core.PApplet;
import ui.ComponentInteractive;
import ui.layout.Bounds;
import ui.layout.Layout;

public class DebugButton extends ComponentInteractive {
    boolean clicked = false;

    public DebugButton(Layout layout) {
        super(layout);
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

    @Override
    protected void onHover() {
    }
}
