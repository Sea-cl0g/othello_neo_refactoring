package ui.text;

import processing.core.PApplet;

public class HoverTextDecorator implements TextRenderer {
    private final TextRenderer inner;

    public HoverTextDecorator(TextRenderer inner) {
        this.inner = inner;
    }

    @Override
    public String getText() {
        return inner.getText();
    }

    @Override
    public void draw(PApplet app, float x, float y, float size, int color, String text) {
        inner.draw(app, x, y, size, color, text);
    }

    @Override
    public void draw(PApplet app, float x, float y, float size, int color, boolean hovered) {
        String text = getText();
        inner.draw(app, x, y, size, color, hovered ? ">" + text + "<" : text);
    }
}
