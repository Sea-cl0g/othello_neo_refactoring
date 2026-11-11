package ui.components;

import processing.core.PApplet;
import processing.core.PImage;
import ui.Component;
import ui.layout.Bounds;
import ui.layout.Layout;

public class ImageView extends Component {
    private final String imagePath;
    private PImage image;

    public ImageView(Layout layout, String imagePath) {
        super(layout);
        this.imagePath = imagePath;
    }

    @Override
    public void draw(PApplet app) {
        if (image == null) {
            image = app.loadImage(imagePath);
            if (image == null) {
                throw new IllegalStateException("Unable to load image: " + imagePath);
            }
        }

        Bounds bounds = getBounds();
        if (bounds.getWidth() <= 0 || bounds.getHeight() <= 0) {
            return;
        }

        float scale = Math.min((float) bounds.getWidth() / image.width, (float) bounds.getHeight() / image.height);
        float drawWidth = image.width * scale;
        float drawHeight = image.height * scale;

        app.pushStyle();
        app.imageMode(PApplet.CENTER);
        app.image(image, bounds.getX(), bounds.getY(), drawWidth, drawHeight);
        app.popStyle();
    }
}
