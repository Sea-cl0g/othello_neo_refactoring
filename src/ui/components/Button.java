package ui.components;

import processing.core.PApplet;
import ui.Component;
import ui.ComponentInteractive;
import ui.layout.Bounds;
import ui.layout.Layout;

public class Button extends ComponentInteractive {
    private final Component content;
    private final Runnable action;
    private final int backgroundColor;
    private final int hoverColor;

    public Button(Layout layout, Component content, Runnable action, int backgroundColor, int hoverColor) {
        super(layout);
        this.content = content;
        this.action = action;
        this.backgroundColor = backgroundColor;
        this.hoverColor = hoverColor;
    }

    @Override
    public void applyLayout(int width, int height) {
        super.applyLayout(width, height);
        content.applyLayout(getBounds());
    }

    @Override
    public void draw(PApplet app) {
        Bounds bounds = getBounds();

        app.pushStyle();
        app.rectMode(PApplet.CENTER);
        app.noStroke();
        app.fill(bounds.contains(app.mouseX, app.mouseY) ? hoverColor : backgroundColor);
        app.rect(bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight());
        app.popStyle();

        content.draw(app);
    }

    @Override
    protected void onClick() {
        action.run();
    }
}
