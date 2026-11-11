package ui.components;

import java.util.function.IntConsumer;
import processing.core.PApplet;
import ui.ComponentInteractive;
import ui.layout.Bounds;
import ui.layout.Layout;

public class Slider extends ComponentInteractive {
    private final int min;
    private final int max;
    private final IntConsumer onChange;
    private final int barColor;
    private final int knobColor;
    private int value;
    private int mouseX;
    private boolean dragging;

    public Slider(Layout layout, int min, int max, int value, IntConsumer onChange,
            int barColor, int knobColor) {
        super(layout);
        this.min = min;
        this.max = max;
        this.value = value;
        this.onChange = onChange;
        this.barColor = barColor;
        this.knobColor = knobColor;
    }

    private float barWidth() {
        return getBounds().getWidth() / 2f;
    }

    private float barLeft() {
        return getBounds().getX() - barWidth() / 2;
    }

    private void update() {
        int next = (int) Math.floor((mouseX - barLeft()) * (max - min + 1) / barWidth()) + min;
        next = Math.max(min, Math.min(max, next));
        if (next != value) {
            value = next;
            onChange.accept(value);
        }
    }

    @Override
    public boolean handleClick(int px, int py) {
        mouseX = px;
        return super.handleClick(px, py);
    }

    @Override
    public boolean handleHover(int px, int py) {
        mouseX = px;
        return super.handleHover(px, py);
    }

    @Override
    public void draw(PApplet app) {
        if (dragging && !app.mousePressed) {
            dragging = false;
        } else if (dragging && isHoverd) {
            update();
        }
        Bounds bounds = getBounds();
        float h = bounds.getHeight();
        float knobX = barLeft() + barWidth() * (value - min) / (max - min + 1);
        app.pushStyle();
        app.rectMode(PApplet.CENTER);
        app.noStroke();
        app.fill(barColor);
        app.rect(bounds.getX(), bounds.getY(), barWidth(), h / 2);
        app.textAlign(PApplet.CENTER, PApplet.CENTER);
        app.textSize(h * 5 / 3);
        app.text("-", bounds.getX() - bounds.getWidth() * 0.3f, bounds.getY());
        app.text("+", bounds.getX() + bounds.getWidth() * 0.3f, bounds.getY());
        app.fill(knobColor);
        app.rect(knobX, bounds.getY(), h / 2, h * 1.25f);
        app.fill(barColor);
        app.textSize(h * 5 / 6);
        app.text(value, knobX, bounds.getY() + h * 1.25f);
        app.popStyle();
    }

    @Override
    protected void onClick() {
        dragging = true;
        update();
    }

    @Override
    protected void onHover() {
    }
}
