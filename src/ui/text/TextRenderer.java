package ui.text;

import processing.core.PApplet;

public interface TextRenderer {
    String getText();

    void draw(PApplet app, float x, float y, float size, int color, String text);

    default void draw(PApplet app, float x, float y, float size, int color) {
        draw(app, x, y, size, color, false);
    }

    default void draw(PApplet app, float x, float y, float size, int color, boolean hovered) {
        draw(app, x, y, size, color, getText());
    }
}
