package ui.components;

import game.OthelloGame;
import java.util.function.Consumer;
import processing.core.PApplet;
import ui.ComponentInteractive;
import ui.layout.Layout;

public class Board extends ComponentInteractive {
    private static final int TURN_FRAMES = 10;
    private final OthelloGame game;
    private final Consumer<OthelloGame.Move> moveAction;
    private final int frameColor;
    private final int boardColor;
    private final int gridColor;
    private final int blackColor;
    private final int whiteColor;
    private final int hintColor;
    private OthelloGame.Move hoverCell;
    private int turnStamp = TURN_FRAMES;

    public Board(Layout layout, OthelloGame game, Consumer<OthelloGame.Move> moveAction,
            int frameColor, int boardColor, int gridColor, int blackColor, int whiteColor, int hintColor) {
        super(layout);
        this.game = game;
        this.moveAction = moveAction;
        this.frameColor = frameColor;
        this.boardColor = boardColor;
        this.gridColor = gridColor;
        this.blackColor = blackColor;
        this.whiteColor = whiteColor;
        this.hintColor = hintColor;
    }

    public boolean isStartingTurn() {
        return game.isTurning() && turnStamp == TURN_FRAMES;
    }

    public boolean stepTurning() {
        if (0 < turnStamp) {
            turnStamp--;
            return false;
        }
        turnStamp = TURN_FRAMES;
        return true;
    }

    private float length() {
        return Math.min(getBounds().getWidth(), getBounds().getHeight());
    }

    private OthelloGame.Move cellAt(int px, int py) {
        float side = length();
        float left = getBounds().getX() - side / 2;
        float top = getBounds().getY() - side / 2;
        if (side <= 0 || px <= left || py <= top || px >= left + side || py >= top + side) {
            return null;
        }
        int x = Math.min(game.size() - 1, (int) ((px - left) * game.size() / side));
        int y = Math.min(game.size() - 1, (int) ((py - top) * game.size() / side));
        return new OthelloGame.Move(x, y);
    }

    @Override
    public boolean handleClick(int px, int py) {
        OthelloGame.Move move = cellAt(px, py);
        if (move == null) {
            return false;
        }
        moveAction.accept(move);
        return true;
    }

    @Override
    public boolean handleHover(int px, int py) {
        hoverCell = cellAt(px, py);
        isHoverd = hoverCell != null;
        return isHoverd;
    }

    @Override
    protected void onClick() {
    }

    @Override
    protected void onHover() {
    }

    private int stoneColor(int player) {
        return player == OthelloGame.BLACK ? blackColor : whiteColor;
    }

    @Override
    public void draw(PApplet app) {
        float side = length();
        if (side <= 0) {
            return;
        }
        float x = getBounds().getX();
        float y = getBounds().getY();
        float left = x - side / 2;
        float top = y - side / 2;
        float cell = side / game.size();
        float stone = cell * 0.8f;
        app.pushStyle();
        app.rectMode(PApplet.CENTER);
        app.ellipseMode(PApplet.CENTER);
        app.noStroke();
        app.fill(frameColor);
        app.rect(x, y, side + side / 25, side + side / 25);
        app.fill(boardColor);
        app.rect(x, y, side, side);

        app.stroke(gridColor);
        app.strokeWeight(1);
        for (int i = 0; i <= game.size(); i++) {
            app.line(left + i * cell, top, left + i * cell, top + side);
            app.line(left, top + i * cell, left + side, top + i * cell);
        }

        app.noStroke();
        for (int row = 0; row < game.size(); row++) {
            for (int col = 0; col < game.size(); col++) {
                int player = game.get(col, row);
                if (player == OthelloGame.EMPTY) {
                    continue;
                }
                float cx = left + (col + 0.5f) * cell;
                float cy = top + (row + 0.5f) * cell;
                app.fill(stoneColor(3 - player));
                app.ellipse(cx, cy + cell / 30, stone, stone);
                app.fill(stoneColor(player));
                app.ellipse(cx, cy, stone, stone);
            }
        }

        if (game.isTurning() && 0 < turnStamp) {
            OthelloGame.Move turning = game.getTurning();
            float cx = left + (turning.x() + 0.5f) * cell;
            float cy = top + (turning.y() + 0.5f) * cell;
            app.fill(boardColor);
            app.rect(cx, cy, cell * 0.9f, cell * 0.9f);
            app.fill(stoneColor(game.getTurn()));
            app.ellipse(cx, cy, stone * turnStamp / TURN_FRAMES, stone);
        }

        if (!game.isFinished() && !game.isTurning() && !game.isAI(game.getTurn())) {
            app.fill(hintColor);
            for (OthelloGame.Move move : game.getLegalMoves()) {
                app.ellipse(left + (move.x() + 0.5f) * cell, top + (move.y() + 0.5f) * cell,
                        cell * 0.16f, cell * 0.16f);
            }
            if (isHoverd && hoverCell != null) {
                app.noFill();
                app.stroke(hintColor);
                app.strokeWeight(5);
                app.rect(left + (hoverCell.x() + 0.5f) * cell, top + (hoverCell.y() + 0.5f) * cell,
                        cell, cell);
            }
        }
        app.popStyle();
    }
}
