package scene;

import game.GameSettings;
import java.util.ArrayList;
import java.util.List;
import processing.core.PApplet;
import ui.Component;
import ui.components.CheckBox;
import ui.components.Image;
import ui.components.ParallaxGroup;
import ui.components.SimpleRect;
import ui.components.Slider;
import ui.components.TextBox;
import ui.layout.Layout;

final class SceneSetting extends ComponentScene {
    private static SceneSetting instance = new SceneSetting();
    private SceneManager manager;
    private TextBox theme;
    private CheckBox effects;
    private boolean themeChanged;

    private SceneSetting() {
    }

    public static SceneSetting getInstance() {
        return instance;
    }

    void initialize(SceneManager manager, MenuColors colors) {
        super.initialize(colors);
        this.manager = manager;
        GameSettings settings = manager.getSettings();
        List<Component> group = new ArrayList<>();
        group.add(new SimpleRect(new Layout(0.375f, 0.5f, 0.5f, 0.9f), colors.panel));
        group.add(label("SETTING", new Layout(0.375f, 0.125f, 0.5f, 0.1f), colors));
        group.add(new SimpleRect(new Layout(0.375f, 0.2f, 0.4875f, 0.01f), colors.text));

        group.add(label("BACKGROUND EFFECTS", new Layout(0.375f, 0.275f, 0.5f, 0.05f), colors));
        Image check = new Image(new Layout(0.5f, 0.5f, 0.75f, 0.75f),
                "assets/チェックマークの無料アイコン.svg", colors.text, colors.text);
        effects = new CheckBox(new Layout(0.375f, 0.35f, 0.0417f, 0.0667f), check,
                effects == null || effects.isChecked(), colors.board, colors.text);
        group.add(effects);

        group.add(label("THEME", new Layout(0.375f, 0.445f, 0.5f, 0.05f), colors));
        if (theme == null) {
            theme = new TextBox(new Layout(0.3625f, 0.52f, 0.4f, 0.0667f),
                    settings.getThemePath(), value -> themeChanged = true, colors.text, colors.shadow);
        } else {
            theme.setColors(colors.text, colors.shadow);
        }
        group.add(theme);
        group.add(arrowButton(new Layout(0.575f, 0.52f, 0.0417f, 0.0667f), theme::focus, colors));

        group.add(label("SOUND VOLUME", new Layout(0.375f, 0.645f, 0.5f, 0.05f), colors));
        group.add(new Slider(new Layout(0.375f, 0.7f, 2f / 3f, 0.04f), 0, 100,
                settings.getSoundVolume(), manager::setSoundVolume, colors.text, colors.board));

        group.add(label("GRID SIZE", new Layout(0.375f, 0.795f, 0.5f, 0.05f), colors));
        group.add(new Slider(new Layout(0.375f, 0.85f, 2f / 3f, 0.04f),
                GameSettings.MIN_SIZE, GameSettings.MAX_SIZE, settings.getBoardSize(),
                settings::setBoardSize, colors.text, colors.board));

        components.add(new ParallaxGroup(0.01f, group));
        components.add(textButton("BACK", 0.9f,
                () -> manager.changeScene(SceneMenu.getInstance()), colors));
    }

    @Override
    public void draw(PApplet app) {
        if (themeChanged) {
            themeChanged = false;
            theme.setError(!manager.applyTheme(theme.getText()));
        }
        super.draw(app);
    }
}
