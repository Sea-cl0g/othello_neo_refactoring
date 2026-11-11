package game;

import java.util.Objects;
import java.util.Random;

public class AIPlayer {
    private final Random random;

    public AIPlayer() {
        this(new Random());
    }

    public AIPlayer(Random random) {
        this.random = Objects.requireNonNull(random);
    }

    public OthelloGame.Move selectMove(OthelloGame game) {
        if (game.isFinished() || game.getLegalMoves().isEmpty()) {
            throw new IllegalStateException("No move is available");
        }
        OthelloGame.Move best = null;
        int bestScore = -1;
        int ties = 0;
        for (OthelloGame.Move move : game.getLegalMoves()) {
            int score = game.flipCount(move.x(), move.y());
            if (score > bestScore) {
                best = move;
                bestScore = score;
                ties = 1;
            } else if (score == bestScore && random.nextInt(++ties) == 0) {
                best = move;
            }
        }
        return Objects.requireNonNull(best);
    }
}