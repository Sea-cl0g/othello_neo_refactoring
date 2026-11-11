package scene;

import game.GameSettings;
import game.OthelloGame;
import game.ScoreBoard;
import game.SaveData;
import game.SoundManager;
import java.io.IOException;
import processing.core.PApplet;
import processing.data.JSONObject;

public class SceneManager {
    private int lastWidth = -1;
    private int lastHeight = -1;
    public boolean windowResized;
    private final GameSettings settings = new GameSettings();
    private final ScoreBoard scores = new ScoreBoard();
    private MenuColors colors = MenuColors.DEFAULT;
    private Scene scene = SceneMenu.getInstance();
    private PApplet app;
    private SoundManager sounds;

    public void initialize(PApplet app) {
        this.app = app;
        JSONObject save = SaveData.load(app);
        String path = save.getString("theme_path", settings.getThemePath());
        try {
            colors = MenuColors.load(app, path);
            settings.setThemePath(path);
        } catch (IOException | IllegalArgumentException error) {
            System.err.println("Unable to load theme " + path + ": " + error.getMessage()
                    + ". Loading themes/default.json.");
            try {
                colors = MenuColors.load(app, "themes/default.json");
            } catch (IOException | IllegalArgumentException fallbackError) {
                throw new IllegalStateException("Unable to load the default theme", fallbackError);
            }
        }
        sounds = new SoundManager(app, settings.getSoundVolume());
        initializeScenes();
    }

    private void initializeScenes() {
        SceneMenu.getInstance().initialize(this, colors);
        SceneModeSelect.getInstance().initialize(this, colors);
        SceneScores.getInstance().initialize(this, colors);
        SceneSetting.getInstance().initialize(this, colors);
        lastWidth = -1;
        lastHeight = -1;
    }

    GameSettings getSettings() {
        return settings;
    }

    ScoreBoard getScores() {
        return scores;
    }

    void setSoundVolume(int volume) {
        sounds.setVolume(volume);
        settings.setSoundVolume(volume);
    }

    void playTurnSound() {
        sounds.play(SoundManager.Sound.TURN);
    }

    boolean applyTheme(String path) {
        MenuColors next;
        try {
            next = MenuColors.load(app, path);
        } catch (IOException | IllegalArgumentException error) {
            System.err.println("Unable to load theme " + path + ": " + error.getMessage());
            return false;
        }
        JSONObject save = SaveData.load(app);
        save.setString("theme_path", path);
        SaveData.save(app, save);
        colors = next;
        settings.setThemePath(path);
        initializeScenes();
        updateLayout(app);
        return true;
    }

    void startGame(int humanPlayers) {
        OthelloGame game = new OthelloGame(settings.getBoardSize(), humanPlayers);
        SceneGame.getInstance().start(this, colors, game);
        changeScene(SceneGame.getInstance());
    }

    void finishGame(PApplet app, OthelloGame game) {
        scores.record(app, game, settings.getUsername());
        SceneResult.getInstance().showResult(this, colors, game);
        changeScene(SceneResult.getInstance());
    }

    public void changeScene(Scene next) {
        java.util.Objects.requireNonNull(next);
        sounds.play(next == SceneResult.getInstance() ? SoundManager.Sound.GAME_END
                : scene == SceneResult.getInstance() ? SoundManager.Sound.AWARD : SoundManager.Sound.CLICK);
        if (scene instanceof ComponentScene current) {
            current.clearFocus();
        }
        scene = next;
        lastWidth = -1;
        lastHeight = -1;
    }

    private void updateLayout(PApplet app) {
        if (lastWidth != app.width || lastHeight != app.height) {
            scene.applyLayout(app.width, app.height);
            lastWidth = app.width;
            lastHeight = app.height;
            windowResized = false;
        }
    }

    public void draw(PApplet app) {
        updateLayout(app);
        scene.draw(app);
    }

    public void mousePressed(PApplet app) {
        updateLayout(app);
        scene.mousePressed(app);
    }

    public void keyPressed(PApplet app) {
        scene.keyPressed(app);
    }
}
