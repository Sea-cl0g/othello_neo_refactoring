package scene;

import game.ScoreBoard;
import java.util.ArrayList;
import java.util.List;
import processing.core.PApplet;
import ui.Component;
import ui.components.ParallaxGroup;
import ui.components.SimpleRect;
import ui.components.TextBox;
import ui.layout.Layout;
import ui.text.PlainText;

final class SceneScores extends ComponentScene {
    private static final int ROWS = 10;
    private static SceneScores instance = new SceneScores();
    private ScoreBoard scores;
    private final PlainText[] numbers = new PlainText[ROWS];
    private final PlainText[] names = new PlainText[ROWS];
    private final PlainText[] values = new PlainText[ROWS];

    private SceneScores() {
    }

    public static SceneScores getInstance() {
        return instance;
    }

    void initialize(SceneManager manager, MenuColors colors) {
        super.initialize(colors);
        scores = manager.getScores();
        List<Component> group = new ArrayList<>();
        group.add(new SimpleRect(new Layout(0.375f, 0.5f, 0.5f, 0.9f), colors.panel));
        group.add(label("SCORES", new Layout(0.375f, 0.125f, 0.5f, 0.1f), colors));
        group.add(new SimpleRect(new Layout(0.375f, 0.2f, 0.4875f, 0.01f), colors.text));
        for (int i = 0; i < ROWS; i++) {
            float y = 0.25f + 0.05f * i;
            numbers[i] = new PlainText("", PApplet.LEFT);
            names[i] = new PlainText("");
            values[i] = new PlainText("", PApplet.RIGHT);
            group.add(label(numbers[i], new Layout(0.18f, y, 0.1f, 0.05f), colors));
            group.add(label(names[i], new Layout(0.375f, y, 0.25f, 0.05f), colors));
            group.add(label(values[i], new Layout(0.58f, y, 0.1f, 0.05f), colors));
        }
        TextBox name = new TextBox(new Layout(0.3625f, 0.885f, 0.4f, 0.0667f),
                manager.getSettings().getUsername(), manager.getSettings()::setUsername,
                colors.text, colors.shadow);
        group.add(name);
        group.add(arrowButton(new Layout(0.575f, 0.885f, 0.0417f, 0.0667f), name::focus, colors));
        components.add(new ParallaxGroup(0.01f, group));
        components.add(textButton("BACK", 0.9f,
                () -> manager.changeScene(SceneMenu.getInstance()), colors));
    }

    private String shorten(String name) {
        return 12 < name.length() ? name.substring(0, 12) + "..." : name;
    }

    @Override
    public void draw(PApplet app) {
        List<ScoreBoard.Entry> entries = scores.getEntries(app);
        for (int i = 0; i < ROWS; i++) {
            boolean exists = i < entries.size();
            numbers[i].setText(exists ? "No." + (i + 1) : "");
            names[i].setText(exists ? shorten(entries.get(i).name()) : "");
            values[i].setText(exists ? entries.get(i).score() / 10.0 + "%" : "");
        }
        super.draw(app);
    }
}
