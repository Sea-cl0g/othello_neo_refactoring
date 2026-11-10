package ui.text;

import processing.core.PApplet;

public class PlainText implements TextRenderer {
    private final String text;

    public PlainText(String text) {
        this.text = text;
    }

    @Override
    public void draw(PApplet app, float x, float y, float size, int color) {
        app.pushStyle();
        app.textAlign(PApplet.CENTER, PApplet.CENTER);
        app.textSize(size);
        app.fill(color);
        app.text(text, x, y);
        app.popStyle();
    }
}
