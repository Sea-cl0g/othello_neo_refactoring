package game;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class OthelloGame {
    public static final int EMPTY = 0;
    public static final int WHITE = 1;
    public static final int BLACK = 2;
    private static final int[][] DIRECTIONS = {
        {1, 0}, {-1, 0}, {0, 1}, {0, -1}, {1, 1}, {-1, -1}, {-1, 1}, {1, -1}
    };

    public record Move(int x, int y) { }

    private final int[][] board;
    private final int humanPlayers;
    private final Deque<Move> turning = new ArrayDeque<>();
    private int turn = BLACK;
    private boolean finished;
    private List<Move> legalMoves;

    public OthelloGame(int size, int humanPlayers) {
        if (size < GameSettings.MIN_SIZE || size > GameSettings.MAX_SIZE) {
            throw new IllegalArgumentException("Board size must be 4..100");
        }
        if (humanPlayers < 0 || humanPlayers > 2) {
            throw new IllegalArgumentException("Human players must be 0..2");
        }
        this.humanPlayers = humanPlayers;
        board = new int[size][size];
        int c = size / 2;
        board[c][c] = WHITE;
        board[c - 1][c - 1] = WHITE;
        board[c][c - 1] = BLACK;
        board[c - 1][c] = BLACK;
        legalMoves = findLegalMoves(turn);
    }

    public int size() {
        return board.length;
    }

    public int get(int x, int y) {
        return board[y][x];
    }

    public int getTurn() {
        return turn;
    }

    public boolean isFinished() {
        return finished;
    }

    public boolean isAI(int player) {
        if (player != WHITE && player != BLACK) {
            throw new IllegalArgumentException("Unknown player");
        }
        return humanPlayers == 0 || (humanPlayers == 1 && player == WHITE);
    }

    public List<Move> getLegalMoves() {
        return legalMoves;
    }

    public boolean canPlay(int x, int y) {
        return !finished && turning.isEmpty() && legalMoves.contains(new Move(x, y));
    }

    public int flipCount(int x, int y) {
        return flipsAt(x, y, turn).size();
    }

    public int count(int player) {
        int count = 0;
        for (int[] row : board) {
            for (int stone : row) {
                if (stone == player) {
                    count++;
                }
            }
        }
        return count;
    }

    public void place(int x, int y) {
        if (!canPlay(x, y)) {
            throw new IllegalArgumentException("Illegal move: " + x + ", " + y);
        }
        turning.add(new Move(x, y));
        turning.addAll(flipsAt(x, y, turn));
    }

    public boolean isTurning() {
        return !turning.isEmpty();
    }

    public Move getTurning() {
        return turning.peek();
    }

    public int getTurningCount() {
        return turning.size();
    }

    public void turnNext() {
        Move move = turning.remove();
        board[move.y()][move.x()] = turn;
        if (turning.isEmpty()) {
            turn = 3 - turn;
            legalMoves = findLegalMoves(turn);
            finished = legalMoves.isEmpty();
        }
    }

    private List<Move> findLegalMoves(int player) {
        List<Move> moves = new ArrayList<>();
        for (int y = 0; y < size(); y++) {
            for (int x = 0; x < size(); x++) {
                if (!flipsAt(x, y, player).isEmpty()) {
                    moves.add(new Move(x, y));
                }
            }
        }
        return List.copyOf(moves);
    }

    private List<Move> flipsAt(int x, int y, int player) {
        List<Move> flips = new ArrayList<>();
        if (!inside(x, y) || board[y][x] != EMPTY) {
            return flips;
        }
        for (int[] direction : DIRECTIONS) {
            int dx = direction[0];
            int dy = direction[1];
            int nx = x + dx;
            int ny = y + dy;
            int length = 0;
            while (inside(nx, ny) && board[ny][nx] == 3 - player) {
                length++;
                nx += dx;
                ny += dy;
            }
            if (length > 0 && inside(nx, ny) && board[ny][nx] == player) {
                for (int i = 1; i <= length; i++) {
                    flips.add(new Move(x + dx * i, y + dy * i));
                }
            }
        }
        return flips;
    }

    private boolean inside(int x, int y) {
        return x >= 0 && y >= 0 && x < size() && y < size();
    }
}