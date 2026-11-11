package ui.components;

import processing.core.PApplet;
import ui.Component;
import ui.layout.Bounds;
import ui.layout.Layout;

public class Stone extends Component {
    private final int color;
    private final int shadowColor;

    public Stone(Layout layout, int color, int shadowColor) {
        super(layout);
        this.color = color;
        this.shadowColor = shadowColor;
    }

    @Override
    public void draw(PApplet app) {
        Bounds bounds = getBounds();
        float d = Math.min(bounds.getWidth(), bounds.getHeight());
        app.pushStyle();
        app.ellipseMode(PApplet.CENTER);
        app.noStroke();
        app.fill(shadowColor);
        app.ellipse(bounds.getX(), bounds.getY() + d / 20, d, d);
        app.fill(color);
        app.ellipse(bounds.getX(), bounds.getY(), d, d);
        app.popStyle();
    }
}
