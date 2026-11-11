package scene;

import ui.components.SimpleRect;
import ui.layout.Layout;

final class SceneModeSelect extends ComponentScene {
    private static SceneModeSelect instance = new SceneModeSelect();

    private SceneModeSelect() {
    }

    public static SceneModeSelect getInstance() {
        return instance;
    }

    void initialize(SceneManager manager, MenuColors colors) {
        super.initialize(colors);
        components.add(new SimpleRect(new Layout(0.38f, 0.47f, 0.64f, 0.82f), colors.panel));
        label("MODE SELECT", new Layout(0.38f, 0.15f, 0.55f, 0.07f), colors);
        components.add(textButton("0P", new Layout(0.38f, 0.32f, 0.26f, 0.1f),
                () -> manager.startGame(0), colors));
        label("AI vs AI", new Layout(0.38f, 0.40f, 0.5f, 0.035f), colors);
        components.add(textButton("1P", new Layout(0.38f, 0.50f, 0.26f, 0.1f),
                () -> manager.startGame(1), colors));
        label("YOU (BLACK) vs AI", new Layout(0.38f, 0.58f, 0.5f, 0.035f), colors);
        components.add(textButton("2P", new Layout(0.38f, 0.68f, 0.26f, 0.1f),
                () -> manager.startGame(2), colors));
        label("HUMAN vs HUMAN", new Layout(0.38f, 0.76f, 0.5f, 0.035f), colors);
        components.add(textButton("BACK", 0.9f,
                () -> manager.changeScene(SceneMenu.getInstance()), colors));
    }
}