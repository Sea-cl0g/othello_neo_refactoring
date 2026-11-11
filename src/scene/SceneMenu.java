package scene;

import ui.components.Button;
import ui.components.Image;
import ui.layout.Layout;

public class SceneMenu extends ComponentScene {
    private static SceneMenu instance = new SceneMenu();

    private SceneMenu() {
    }

    public static SceneMenu getInstance() {
        return instance;
    }

    void initialize(SceneManager manager, MenuColors colors) {
        super.initialize(colors);
        components.add(textButton("PLAY", 2f / 3f,
                () -> manager.changeScene(SceneModeSelect.getInstance()), colors));

        components.add(textButton("SCORES", 0.8f,
                () -> manager.changeScene(SceneScores.getInstance()), colors));

        Image gear = new Image(new Layout(0.5f, 0.5f, 2f / 3f, 2f / 3f),
                "assets/設定の歯車アイコン　その2.svg", colors.text, colors.shadow);
        components.add(new Button(new Layout(0.0625f, 0.9f, 0.0625f, 0.1f), gear,
                () -> manager.changeScene(SceneSetting.getInstance()), colors.panel, colors.text));
    }
}
