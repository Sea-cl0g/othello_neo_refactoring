package scene;

import processing.core.PApplet;
import ui.BoundingBox;
import ui.Component;
import ui.components.SimpleRect;
import java.util.ArrayList;
import java.util.List;


public class SceneMenu implements Scene {
    private static SceneMenu instance = new SceneMenu();
    List<Component> components = new ArrayList<>();

    private SceneMenu() {
        components.add(new SimpleRect(new BoundingBox(400, 300, 200, 100)));
    }

    public static Scene getInstance() {
        return instance;
    }

    @Override
    public void draw(PApplet app) {
        app.background(40);

        for (Component component : components) {
            component.draw(app);
        }
    }

    @Override
    public void mousePressed() {
        System.err.println("mouse");
    }

    @Override
    public void keyPressed() {
        System.err.println("key");
    }
}
