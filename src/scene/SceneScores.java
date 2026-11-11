package scene;

final class SceneScores extends ComponentScene {
    private static SceneScores instance = new SceneScores();

    private SceneScores() {
    }

    public static SceneScores getInstance() {
        return instance;
    }

    void initialize(SceneManager manager, MenuColors colors) {
        super.initialize(colors);
        components.add(textButton("BACK", 0.9f,
                () -> manager.changeScene(SceneMenu.getInstance()), colors));
    }
}
