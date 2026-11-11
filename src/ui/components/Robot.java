package ui.components;

import java.util.EnumMap;
import java.util.Map;
import processing.core.PApplet;
import ui.Component;
import ui.layout.Bounds;
import ui.layout.Layout;

public class Robot extends Component {
    public enum Face {
        SMILE("assets/ai_character01_smile.png"),
        THINK("assets/ai_character08_think.png"),
        IDEA("assets/ai_character05_idea.png"),
        SURPRISE("assets/ai_character07_surprise.png"),
        TEHE("assets/ai_character12_tehe.png");

        private final String path;

        Face(String path) {
            this.path = path;
        }
    }

    private final Map<Face, Image> images = new EnumMap<>(Face.class);
    private final Bounds bounced = new Bounds(0, 0, 0, 0);
    private Face face = Face.SMILE;
    private float bounce;
    private boolean rising;

    public Robot(Layout layout) {
        super(layout);
        for (Face value : Face.values()) {
            images.put(value, new Image(new Layout(0.5f, 0.5f, 1, 1), value.path));
        }
    }

    public void setFace(Face face) {
        this.face = face;
    }

    @Override
    public void draw(PApplet app) {
        Bounds bounds = getBounds();
        float height = bounds.getHeight();
        if (rising) {
            if (bounce < -height / 5) {
                rising = false;
            } else {
                bounce -= height / 150;
            }
        } else {
            if (0 < bounce) {
                rising = true;
            } else {
                bounce += height / 250;
            }
        }
        bounced.setBounds(bounds.getX(), Math.round(bounds.getY() + bounce),
                bounds.getWidth(), bounds.getHeight());
        Image image = images.get(face);
        image.applyLayout(bounced);
        image.draw(app);
    }
}
