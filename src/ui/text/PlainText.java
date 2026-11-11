package ui.text;

import processing.core.PApplet;

public class PlainText implements TextRenderer {
    private String text;
    private final int align;

    public PlainText(String text) {
        this(text, PApplet.CENTER);
    }

    public PlainText(String text, int align) {
        this.text = text;
        this.align = align;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public void draw(PApplet app, float x, float y, float size, int color, String text) {
        app.pushStyle();
        app.textAlign(align, PApplet.CENTER);
        app.textSize(size);
        app.fill(color);
        app.text(text, x, y);
        app.popStyle();
    }
}
