package scene;

final class SceneSetting extends ComponentScene {
    private static SceneSetting instance = new SceneSetting();

    private SceneSetting() {
    }

    public static SceneSetting getInstance() {
        return instance;
    }

    void initialize(SceneManager manager, MenuColors colors) {
        super.initialize(colors);
        components.add(textButton("BACK", 0.9f,
                () -> manager.changeScene(SceneMenu.getInstance()), colors));
    }
}
