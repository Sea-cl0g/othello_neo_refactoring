package ui.components;

import processing.core.PApplet;
import ui.Component;
import ui.layout.Bounds;
import ui.layout.Layout;
import ui.text.TextRenderer;

public class Label extends Component {
    private final TextRenderer text;
    private final int color;

    public Label(Layout layout, TextRenderer text, int color) {
        super(layout);
        this.text = text;
        this.color = color;
    }

    @Override
    public void draw(PApplet app) {
        draw(app, false);
    }

    @Override
    public void draw(PApplet app, boolean hovered) {
        Bounds bounds = getBounds();
        if (bounds.getHeight() > 0) {
            text.draw(app, bounds.getX(), bounds.getY(), bounds.getHeight(), color, hovered);
        }
    }
}
