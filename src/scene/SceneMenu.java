package scene;

import processing.core.PApplet;
import layout.Layout;
import ui.Component;
import ui.components.DebugButton;
import ui.components.SimpleRect;
import java.util.ArrayList;
import java.util.List;

public class SceneMenu implements Scene {
    private static SceneMenu instance = new SceneMenu();
    List<Component> components = new ArrayList<>();

    private SceneMenu() {
        components.add(new SimpleRect(
                new Layout(0.5f, 0.5f, 0.5f, 0.5f)));

        components.add(new DebugButton(
                new Layout(0.25f, 0.25f, 0.5f, 0.5f)));

        components.add(new DebugButton(
                new Layout(0.55f, 0.5f, 0.5f, 0.5f)));
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
    public void applyLayout(int width, int height) {
        for (Component component : components) {
            component.applyLayout(width, height);
        }
    }

    @Override
    public void mousePressed(PApplet app) {
        for (int i = components.size() - 1; i >= 0; i--) {
            if (components.get(i).handleClick(app.mouseX, app.mouseY)) {
                break;
            }
        }
    }

    @Override
    public void keyPressed(PApplet app) {
        System.err.println("key");
    }
}
