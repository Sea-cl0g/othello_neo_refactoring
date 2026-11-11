package ui.components;

import processing.core.PApplet;
import ui.Component;
import ui.layout.Bounds;
import ui.layout.Layout;

public class ScorePanel extends Component {
    private final Component stone;
    private final Component count;
    private final int panelColor;
    private final int activeColor;
    private boolean active;

    public ScorePanel(Layout layout, Component stone, Component count, int panelColor, int activeColor) {
        super(layout);
        this.stone = stone;
        this.count = count;
        this.panelColor = panelColor;
        this.activeColor = activeColor;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public void applyLayout(int width, int height) {
        super.applyLayout(width, height);
        stone.applyLayout(getBounds());
        count.applyLayout(getBounds());
    }

    @Override
    public void draw(PApplet app) {
        Bounds bounds = getBounds();
        app.pushStyle();
        app.rectMode(PApplet.CENTER);
        app.noStroke();
        if (active) {
            app.stroke(activeColor);
            app.strokeWeight(app.height / 200);
        }
        app.fill(panelColor);
        app.rect(bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight());
        app.popStyle();
        count.draw(app);
        stone.draw(app);
    }
}
