package ui.components;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PShape;
import ui.Component;
import ui.layout.Bounds;
import ui.layout.Layout;

public class Image extends Component {
    private final String imagePath;
    private final int color;
    private final int hoverColor;
    private PImage image;
    private PShape shape;

    public Image(Layout layout, String imagePath) {
        this(layout, imagePath, 0xFFFFFFFF, 0xFFFFFFFF);
    }

    public Image(Layout layout, String imagePath, int color, int hoverColor) {
        super(layout);
        this.imagePath = imagePath;
        this.color = color;
        this.hoverColor = hoverColor;
    }

    @Override
    public void draw(PApplet app) {
        draw(app, false);
    }

    @Override
    public void draw(PApplet app, boolean hovered) {
        if (imagePath.endsWith(".svg")) {
            if (shape == null) {
                shape = app.loadShape(imagePath);
                if (shape == null) {
                    throw new IllegalStateException("Unable to load shape: " + imagePath);
                }
                shape.disableStyle();
            }
            Bounds bounds = getBounds();
            app.pushStyle();
            app.shapeMode(PApplet.CENTER);
            app.noStroke();
            app.fill(hovered ? hoverColor : color);
            app.shape(shape, bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight());
            app.popStyle();
            return;
        }
        if (image == null) {
            image = app.loadImage(imagePath);
            if (image == null) {
                throw new IllegalStateException("Unable to load image: " + imagePath);
            }
        }

        Bounds bounds = getBounds();
        float scale = Math.min((float) bounds.getWidth() / image.width, (float) bounds.getHeight() / image.height);
        float drawWidth = image.width * scale;
        float drawHeight = image.height * scale;

        app.pushStyle();
        app.imageMode(PApplet.CENTER);
        app.image(image, bounds.getX(), bounds.getY(), drawWidth, drawHeight);
        app.popStyle();
    }
}
