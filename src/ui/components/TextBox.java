package ui.components;

import java.awt.event.KeyEvent;
import java.util.function.Consumer;
import processing.core.PApplet;
import ui.Component;
import ui.layout.Bounds;
import ui.layout.Layout;

public class TextBox extends Component {
    private String text;
    private final Consumer<String> onChange;
    private int boxColor;
    private int textColor;
    private boolean focused;
    private boolean error;
    private int cursor;
    private float scroll;
    private float[] positions = new float[0];

    public TextBox(Layout layout, String text, Consumer<String> onChange, int boxColor, int textColor) {
        super(layout);
        this.text = text;
        this.onChange = onChange;
        cursor = text.length();
        setColors(boxColor, textColor);
    }

    public void setColors(int boxColor, int textColor) {
        this.boxColor = boxColor;
        this.textColor = textColor;
    }

    public String getText() {
        return text;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public void focus() {
        focused = true;
        cursor = text.length();
    }

    @Override
    public void clearFocus() {
        focused = false;
    }

    @Override
    public boolean handleClick(int px, int py) {
        focused = getBounds().contains(px, py);
        if (focused) {
            float x = px - (getBounds().getX() - getBounds().getWidth() / 2f
                    + getBounds().getHeight() * 0.375f) + scroll;
            cursor = text.length();
            for (int i = 0; i < text.length() && i + 1 < positions.length; i++) {
                if (x < (positions[i] + positions[i + 1]) / 2) {
                    cursor = i;
                    break;
                }
            }
            if (cursor > 0 && cursor < text.length() && Character.isLowSurrogate(text.charAt(cursor))) {
                cursor--;
            }
        }
        return focused;
    }

    @Override
    public boolean handleKeyPressed(PApplet app) {
        if (!focused) {
            return false;
        }
        if (app.keyEvent != null && (app.keyEvent.isControlDown()
                || app.keyEvent.isMetaDown() || app.keyEvent.isAltDown())) {
            return true;
        }
        String before = text;
        if (app.key == PApplet.CODED) {
            switch (app.keyCode) {
                case PApplet.LEFT -> cursor = cursor > 0 ? text.offsetByCodePoints(cursor, -1) : 0;
                case PApplet.RIGHT -> cursor = cursor < text.length()
                        ? text.offsetByCodePoints(cursor, 1) : text.length();
                case PApplet.UP, KeyEvent.VK_HOME -> cursor = 0;
                case PApplet.DOWN, KeyEvent.VK_END -> cursor = text.length();
                default -> { }
            }
        } else if (app.key == PApplet.BACKSPACE && cursor > 0) {
            int previous = text.offsetByCodePoints(cursor, -1);
            text = text.substring(0, previous) + text.substring(cursor);
            cursor = previous;
        } else if (app.key == PApplet.DELETE && cursor < text.length()) {
            text = text.substring(0, cursor) + text.substring(text.offsetByCodePoints(cursor, 1));
        } else if (app.key == PApplet.ENTER || app.key == PApplet.RETURN) {
            clearFocus();
        } else if (!Character.isISOControl(app.key) && !Character.isSurrogate(app.key)) {
            text = text.substring(0, cursor) + app.key + text.substring(cursor);
            cursor++;
        }
        if (!text.equals(before)) {
            onChange.accept(text);
        }
        return true;
    }

    @Override
    public void draw(PApplet app) {
        Bounds bounds = getBounds();
        float h = bounds.getHeight();
        float textX = bounds.getX() - bounds.getWidth() / 2f + h * 0.375f;
        float textY = bounds.getY() - h * 0.075f;
        app.pushStyle();
        app.rectMode(PApplet.CENTER);
        app.noStroke();
        app.fill(boxColor);
        app.rect(bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight());
        app.textAlign(PApplet.LEFT, PApplet.CENTER);
        app.textSize(h * 0.75f);
        positions = new float[text.length() + 1];
        for (int i = 1; i <= text.length(); i++) {
            positions[i] = app.textWidth(text.substring(0, i));
        }
        float available = Math.max(1, bounds.getWidth() - h * 0.75f);
        scroll = Math.max(0, Math.min(scroll, positions[text.length()] - available));
        scroll = Math.max(scroll, positions[cursor] - available);
        scroll = Math.min(scroll, positions[cursor]);
        app.imageMode(PApplet.CORNER);
        app.clip(textX, bounds.getY() - h / 2, available + 1, h);
        app.fill(error ? 0xFFFF0000 : textColor);
        app.text(text, textX - scroll, textY);
        if (focused && app.frameCount % 40 < 20) {
            float caretX = textX + positions[cursor] - scroll;
            app.stroke(0);
            app.strokeWeight(Math.max(1, app.width / 800));
            app.line(caretX, textY - h * 0.375f, caretX, textY + h * 0.375f);
        }
        app.noClip();
        app.popStyle();
    }
}
