package scene;

import processing.core.PApplet;
import ui.Component;
import ui.components.DebugButton;
import ui.components.SimpleRect;
import ui.components.Label;
import ui.layout.Layout;
import ui.text.PlainText;
import ui.text.ShadowTextDecorator;

import java.util.ArrayList;
import java.util.List;

public class SceneMenu implements Scene {
    private static SceneMenu instance = new SceneMenu();
    List<Component> components = new ArrayList<>();

    private SceneMenu() {
        components.add(new Label(
                new Layout(0.5f, 0.8f, 0.8f, 0.06f),
                new PlainText("PLAIN TEXT"), 0xFFFFFFFF));

        components.add(new Label(
                new Layout(0.5f, 0.9f, 0.8f, 0.06f),
                new ShadowTextDecorator(
                        new PlainText("SHADOW TEXT"), 0xFF000000, 0.06f, 0.1f),
                0xFFFFFFFF));
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
