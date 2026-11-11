package scene;

import game.GameSettings;
import game.OthelloGame;
import game.ScoreBoard;
import processing.core.PApplet;

public class SceneManager {
    private int lastWidth = -1;
    private int lastHeight = -1;
    public boolean windowResized;
    private final GameSettings settings = new GameSettings();
    private final ScoreBoard scores = new ScoreBoard();
    private final MenuColors colors = MenuColors.DEFAULT;
    private Scene scene = SceneMenu.getInstance();

    public SceneManager() {
        SceneMenu.getInstance().initialize(this, colors);
        SceneModeSelect.getInstance().initialize(this, colors);
        SceneScores.getInstance().initialize(this, colors);
        SceneSetting.getInstance().initialize(this, colors);
    }

    GameSettings getSettings() {
        return settings;
    }

    ScoreBoard getScores() {
        return scores;
    }

    void startGame(int humanPlayers) {
        startGame(settings.getBoardSize(), humanPlayers);
    }

    void startGame(int size, int humanPlayers) {
        OthelloGame game = new OthelloGame(size, humanPlayers);
        SceneGame.getInstance().start(this, colors, game);
        changeScene(SceneGame.getInstance());
    }

    void finishGame(OthelloGame game) {
        scores.record(game, settings.getUsername());
        SceneResult.getInstance().showResult(this, colors, game);
        changeScene(SceneResult.getInstance());
    }

    public void changeScene(Scene next) {
        scene = java.util.Objects.requireNonNull(next);
        lastWidth = -1;
        lastHeight = -1;
    }

    public void draw(PApplet app) {
        if (lastWidth != app.width || lastHeight != app.height) {
            scene.applyLayout(app.width, app.height);
            lastWidth = app.width;
            lastHeight = app.height;
            windowResized = false;
        }
        scene.draw(app);
    }

    public void mousePressed(PApplet app) {
        scene.mousePressed(app);
    }
}