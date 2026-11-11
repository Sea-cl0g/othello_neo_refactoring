package scene;

final class SceneModeSelect extends ComponentScene {
    private static SceneModeSelect instance = new SceneModeSelect();

    private SceneModeSelect() {
    }

    public static SceneModeSelect getInstance() {
        return instance;
    }

    void initialize(SceneManager manager, MenuColors colors) {
        super.initialize(colors);
        components.add(textButton("BACK", 0.9f,
                () -> manager.changeScene(SceneMenu.getInstance()), colors));
    }
}
