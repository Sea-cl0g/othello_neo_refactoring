package scene;

import processing.core.PApplet;
import ui.Component;
import ui.components.Button;
import ui.components.ImageView;
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
        components.add(new Button(
                new Layout(0.5f, 0.38f, 0.36f, 0.16f),
                new Label(
                        new Layout(0.5f, 0.5f, 0.85f, 0.65f),
                        new ShadowTextDecorator(new PlainText("PLAY"), 0xFF000000, 0.06f, 0.1f),
                        0xFFFFFFFF),
                () -> System.out.println("test"),
                0xFF555555, 0xFF777777));

        components.add(new Button(
                new Layout(0.5f, 0.66f, 0.36f, 0.16f),
                new ImageView(
                        new Layout(0.5f, 0.5f, 0.65f, 0.8f),
                        "assets/ai_character01_smile.png"),
                () -> System.out.println("testsaaa"),
                0xFF555555, 0xFF777777));
    }

    public static Scene getInstance() {
        return instance;
    }

    @Override
    public void draw(PApplet app) {
        app.background(40);
        for (Component component : components) {
            component.resetHover();
        }
        
        for (int i = components.size() - 1; i >= 0; i--) {
            if (components.get(i).handleHover(app.mouseX, app.mouseY)) {
                break;
            }
        }

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
