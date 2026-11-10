package ui.text;

import processing.core.PApplet;

public class ShadowTextDecorator implements TextRenderer {
    private final TextRenderer inner;
    private final int shadowColor;
    private final float offsetXRatio;
    private final float offsetYRatio;

    public ShadowTextDecorator(TextRenderer inner, int shadowColor, float offsetXRatio, float offsetYRatio) {
        this.inner = inner;
        this.shadowColor = shadowColor;
        this.offsetXRatio = offsetXRatio;
        this.offsetYRatio = offsetYRatio;
    }

    @Override
    public void draw(PApplet app, float x, float y, float size, int color) {
        inner.draw(app, x + size * offsetXRatio, y + size * offsetYRatio, size, shadowColor);
        inner.draw(app, x, y, size, color);
    }
}
