package ui.components;

import processing.core.PApplet;
import ui.Component;
import ui.ComponentInteractive;
import ui.layout.Bounds;
import ui.layout.Layout;

public class CheckBox extends ComponentInteractive {
    private final Component mark;
    private final int boxColor;
    private final int borderColor;
    private boolean checked;

    public CheckBox(Layout layout, Component mark, boolean checked, int boxColor, int borderColor) {
        super(layout);
        this.mark = mark;
        this.checked = checked;
        this.boxColor = boxColor;
        this.borderColor = borderColor;
    }

    public boolean isChecked() {
        return checked;
    }

    @Override
    public void applyLayout(int width, int height) {
        super.applyLayout(width, height);
        mark.applyLayout(getBounds());
    }

    @Override
    public void draw(PApplet app) {
        Bounds bounds = getBounds();
        app.pushStyle();
        app.rectMode(PApplet.CENTER);
        app.noStroke();
        if (isHoverd) {
            app.stroke(borderColor);
            app.strokeWeight(app.height / 200);
        }
        app.fill(boxColor);
        app.rect(bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight());
        app.popStyle();
        if (checked) {
            mark.draw(app);
        }
    }

    @Override
    protected void onClick() {
        checked = !checked;
    }

    @Override
    protected void onHover() {
    }
}
