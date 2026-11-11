package scene;

import java.util.ArrayList;
import java.util.List;
import processing.core.PApplet;
import ui.Component;
import ui.components.Button;
import ui.components.Label;
import ui.layout.Layout;
import ui.text.HoverTextDecorator;
import ui.text.PlainText;
import ui.text.ShadowTextDecorator;

abstract class ComponentScene implements Scene {
    protected final List<Component> components = new ArrayList<>();
    private int background;

    protected void initialize(MenuColors colors) {
        background = colors.background;
        components.clear();
    }

    protected Button textButton(String text, float yRatio, Runnable action, MenuColors colors) {
        return textButton(text, new Layout(5f / 6f, yRatio, 0.21f, 0.1f), action, colors);
    }

    protected Button textButton(String text, Layout layout, Runnable action, MenuColors colors) {
        Label content = new Label(new Layout(0.5f, 0.5f, 1, 2f / 3f),
                new ShadowTextDecorator(new HoverTextDecorator(new PlainText(text)),
                        colors.shadow, 0, 0.1f),
                colors.text);
        return new Button(layout, content, action, colors.panel, colors.text);
    }

    protected Button arrowButton(Layout layout, Runnable action, MenuColors colors) {
        Label arrow = new Label(new Layout(0.5f, 0.4f, 1, 1), new PlainText("←"), colors.text);
        return new Button(layout, arrow, action, colors.board, colors.text);
    }

    protected Label label(String text, Layout layout, MenuColors colors) {
        return label(new PlainText(text), layout, colors);
    }

    protected Label label(PlainText text, Layout layout, MenuColors colors) {
        return new Label(layout, new ShadowTextDecorator(text, colors.shadow, 0, 0.1f), colors.text);
    }

    @Override
    public void draw(PApplet app) {
        app.background(background);
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
        clearFocus();
        for (int i = components.size() - 1; i >= 0; i--) {
            if (components.get(i).handleClick(app.mouseX, app.mouseY)) {
                break;
            }
        }
    }

    @Override
    public void keyPressed(PApplet app) {
        for (int i = components.size() - 1; i >= 0; i--) {
            if (components.get(i).handleKeyPressed(app)) {
                break;
            }
        }
    }

    void clearFocus() {
        for (Component component : components) {
            component.clearFocus();
        }
    }
}