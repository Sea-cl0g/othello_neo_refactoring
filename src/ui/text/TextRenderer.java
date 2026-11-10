package ui.text;

import processing.core.PApplet;

public interface TextRenderer {
    void draw(PApplet app, float x, float y, float size, int color);
}
