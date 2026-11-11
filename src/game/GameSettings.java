package game;

import java.util.Random;

public class GameSettings {
    public static final int MIN_SIZE = 4;
    public static final int MAX_SIZE = 100;
    private String username = "guest" + new Random().nextInt(1000);
    private int boardSize = 8;
    private int soundVolume = 85;
    private String themePath = "themes/default.json";

    public int getBoardSize() {
        return boardSize;
    }

    public void setBoardSize(int boardSize) {
        if (boardSize < MIN_SIZE || boardSize > MAX_SIZE) {
            throw new IllegalArgumentException("Board size must be 4..100");
        }
        this.boardSize = boardSize;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = java.util.Objects.requireNonNull(username);
    }

    public int getSoundVolume() {
        return soundVolume;
    }

    public void setSoundVolume(int soundVolume) {
        if (soundVolume < 0 || soundVolume > 100) {
            throw new IllegalArgumentException("Volume must be 0..100");
        }
        this.soundVolume = soundVolume;
    }

    public String getThemePath() {
        return themePath;
    }

    public void setThemePath(String themePath) {
        this.themePath = java.util.Objects.requireNonNull(themePath);
    }
}
