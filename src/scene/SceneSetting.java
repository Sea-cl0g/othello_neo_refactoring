package scene;

import game.GameSettings;
import processing.core.PApplet;
import ui.components.SimpleRect;
import ui.layout.Layout;
import ui.text.PlainText;

final class SceneSetting extends ComponentScene {
    private static SceneSetting instance = new SceneSetting();
    private GameSettings settings;
    private PlainText sizeText;
    private PlainText message;

    private SceneSetting() {
    }

    public static SceneSetting getInstance() {
        return instance;
    }

    void initialize(SceneManager manager, MenuColors colors) {
        super.initialize(colors);
        settings = manager.getSettings();
        components.add(new SimpleRect(new Layout(0.38f, 0.47f, 0.64f, 0.82f), colors.panel));
        label("SETTING", new Layout(0.38f, 0.15f, 0.55f, 0.08f), colors);
        label("GRID SIZE", new Layout(0.38f, 0.28f, 0.5f, 0.05f), colors);
        sizeText = label("", new Layout(0.38f, 0.39f, 0.2f, 0.07f), colors);
        components.add(textButton("-", new Layout(0.2f, 0.39f, 0.1f, 0.1f),
                () -> adjust(-1), colors));
        components.add(textButton("+", new Layout(0.56f, 0.39f, 0.1f, 0.1f),
                () -> adjust(1), colors));
        components.add(textButton("RESET 8", new Layout(0.38f, 0.52f, 0.25f, 0.08f),
                () -> {
                    settings.setBoardSize(8);
                    message.setText("Applies to the next game");
                }, colors));
        message = label("Applies to the next game", new Layout(0.38f, 0.61f, 0.57f, 0.03f), colors);
        label("NAME: guest (editing deferred)", new Layout(0.38f, 0.69f, 0.57f, 0.028f), colors);
        label("THEME / SOUND: NOT IMPLEMENTED", new Layout(0.38f, 0.75f, 0.57f, 0.028f), colors);
        label("Not saved after closing", new Layout(0.38f, 0.81f, 0.57f, 0.028f), colors);
        components.add(textButton("BACK", 0.9f,
                () -> manager.changeScene(SceneMenu.getInstance()), colors));
    }

    private void adjust(int amount) {
        int next = settings.getBoardSize() + amount;
        if (next < GameSettings.MIN_SIZE || next > GameSettings.MAX_SIZE) {
            message.setText("GRID SIZE must be 4..100");
        } else {
            settings.setBoardSize(next);
            message.setText("Applies to the next game");
        }
    }

    @Override
    public void draw(PApplet app) {
        sizeText.setText(Integer.toString(settings.getBoardSize()));
        super.draw(app);
    }
}