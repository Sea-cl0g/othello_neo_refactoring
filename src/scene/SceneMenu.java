package scene;

import processing.core.PApplet;
import layout.Bounds;
import ui.Component;
import ui.components.SimpleRect;
import java.util.ArrayList;
import java.util.List;

public class SceneMenu implements Scene {
    private static SceneMenu instance = new SceneMenu();
    List<Component> components = new ArrayList<>();

    private SceneMenu() {
        components.add(new SimpleRect(new Bounds(400, 300, 200, 100)));
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
        for (Component component : components) {
            if (component.handleClick(app.mouseX, app.mouseY)) {
                break;
            }
        }
    }

    @Override
    public void applyLayout(int width, int height) {
        for (Component component : components) {
            component.applyLayout(width, height);
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
