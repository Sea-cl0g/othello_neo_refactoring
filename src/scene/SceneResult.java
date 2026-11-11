package scene;

import game.OthelloGame;
import java.util.List;
import processing.core.PApplet;
import ui.components.Label;
import ui.components.ParallaxGroup;
import ui.components.SimpleRect;
import ui.components.Stone;
import ui.layout.Layout;
import ui.text.PlainText;
import ui.text.ShadowTextDecorator;

final class SceneResult extends ComponentScene {
    private static SceneResult instance = new SceneResult();
    private SceneManager manager;

    private SceneResult() {
    }

    public static SceneResult getInstance() {
        return instance;
    }

    void showResult(SceneManager manager, MenuColors colors, OthelloGame game) {
        super.initialize(colors);
        this.manager = manager;
        int white = game.count(OthelloGame.WHITE);
        int black = game.count(OthelloGame.BLACK);
        boolean blackWins = white < black;
        components.add(new SimpleRect(new Layout(0.5f, 0.5f, 0.5625f, 0.45f), colors.panel));
        components.add(new Stone(new Layout(0.36f, 0.5f, 0.14f, 0.225f),
                blackWins ? colors.black : colors.white, blackWins ? colors.white : colors.black));
        components.add(new Label(new Layout(0.5f, 0.5f, 0.2f, 0.225f),
                new ShadowTextDecorator(new PlainText(Integer.toString(blackWins ? black : white),
                        PApplet.LEFT), colors.shadow, 0, 0.05f),
                colors.text));
        components.add(new ParallaxGroup(-0.01f, List.of(new Label(
                new Layout(0.5f, 0.875f, 0.8f, 0.1125f),
                new ShadowTextDecorator(new PlainText("Click to return to menu"), colors.shadow, 0, 0.1f),
                colors.alert))));
    }

    @Override
    public void mousePressed(PApplet app) {
        manager.changeScene(SceneMenu.getInstance());
    }
}
