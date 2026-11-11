package scene;

import java.util.List;
import ui.components.ParallaxGroup;
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
        components.add(new ParallaxGroup(0.01f, List.of(
                textButton("0P", new Layout(0.375f, 0.375f, 0.21f, 0.1f),
                        () -> manager.startGame(0), colors),
                textButton("1P", new Layout(0.375f, 0.5f, 0.21f, 0.1f),
                        () -> manager.startGame(1), colors),
                textButton("2P", new Layout(0.375f, 0.625f, 0.21f, 0.1f),
                        () -> manager.startGame(2), colors))));
        components.add(textButton("BACK", 0.9f,
                () -> manager.changeScene(SceneMenu.getInstance()), colors));
    }
}
