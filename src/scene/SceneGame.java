package scene;

import game.AIPlayer;
import game.OthelloGame;
import processing.core.PApplet;
import ui.components.Board;
import ui.components.Robot;
import ui.components.ScorePanel;
import ui.components.Stone;
import ui.layout.Layout;
import ui.text.PlainText;

final class SceneGame extends ComponentScene {
    private static final int THINK_FRAMES = 50;
    private static SceneGame instance = new SceneGame();
    private final AIPlayer ai = new AIPlayer();
    private SceneManager manager;
    private OthelloGame game;
    private Board board;
    private Robot robot;
    private ScorePanel blackPanel;
    private ScorePanel whitePanel;
    private PlainText blackCount;
    private PlainText whiteCount;
    private int thinking;

    private SceneGame() {
    }

    public static SceneGame getInstance() {
        return instance;
    }

    void start(SceneManager manager, MenuColors colors, OthelloGame game) {
        super.initialize(colors);
        this.manager = manager;
        this.game = game;
        thinking = THINK_FRAMES;
        board = new Board(new Layout(0.5f, 0.5f, 0.9f, 0.9f), game, this::humanMove,
                colors.frame, colors.board, colors.grid, colors.black, colors.white, colors.hint);
        robot = new Robot(new Layout(0.89f, 2f / 3f, 0.25f, 0.45f));
        blackCount = new PlainText("", PApplet.LEFT);
        whiteCount = new PlainText("", PApplet.LEFT);
        blackPanel = scorePanel(new Layout(0.11f, 0.14f, 0.175f, 0.14f),
                colors.black, colors.white, blackCount, colors);
        whitePanel = scorePanel(new Layout(0.89f, 0.86f, 0.175f, 0.14f),
                colors.white, colors.black, whiteCount, colors);
        components.add(board);
        components.add(robot);
        components.add(blackPanel);
        components.add(whitePanel);
    }

    private ScorePanel scorePanel(Layout layout, int stoneColor, int shadowColor,
            PlainText count, MenuColors colors) {
        return new ScorePanel(layout,
                new Stone(new Layout(0.25f, 0.5f, 0.3125f, 0.625f), stoneColor, shadowColor),
                label(count, new Layout(0.5f, 0.5f, 0.5f, 0.76f), colors),
                colors.panel, colors.alert);
    }

    private void humanMove(OthelloGame.Move move) {
        if (!game.isAI(game.getTurn()) && game.canPlay(move.x(), move.y())) {
            game.place(move.x(), move.y());
        }
    }

    @Override
    public void draw(PApplet app) {
        if (board.isStartingTurn()) {
            manager.playTurnSound();
        }
        blackCount.setText(Integer.toString(game.count(OthelloGame.BLACK)));
        whiteCount.setText(Integer.toString(game.count(OthelloGame.WHITE)));
        blackPanel.setActive(game.getTurn() == OthelloGame.BLACK);
        whitePanel.setActive(game.getTurn() == OthelloGame.WHITE);
        super.draw(app);
        update(app);
    }

    private void update(PApplet app) {
        if (game.isTurning()) {
            if (game.size() / 2 < game.getTurningCount()) {
                robot.setFace(game.isAI(game.getTurn()) ? Robot.Face.TEHE : Robot.Face.SURPRISE);
            }
            if (board.stepTurning()) {
                game.turnNext();
                if (game.isFinished()) {
                    manager.finishGame(app, game);
                }
            }
        } else if (game.isAI(game.getTurn())) {
            think();
        }
    }

    private void think() {
        if (0 < thinking) {
            if (thinking == THINK_FRAMES) {
                robot.setFace(Robot.Face.THINK);
            } else if (thinking == 20) {
                robot.setFace(Robot.Face.IDEA);
            }
            thinking--;
            return;
        }
        OthelloGame.Move move = ai.selectMove(game);
        game.place(move.x(), move.y());
        robot.setFace(Robot.Face.SMILE);
        thinking = THINK_FRAMES;
    }
}
