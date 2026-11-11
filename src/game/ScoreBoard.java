package game;

import java.util.ArrayList;
import java.util.List;
import processing.core.PApplet;
import processing.data.JSONArray;
import processing.data.JSONObject;

public class ScoreBoard {
    public record Entry(String name, int score) { }
    private List<Entry> entries;

    public List<Entry> getEntries(PApplet app) {
        if (entries == null) {
            entries = load(app);
        }
        return entries;
    }

    public void record(PApplet app, OthelloGame game, String username) {
        if (!game.isFinished()) {
            throw new IllegalStateException("Cannot record an unfinished game");
        }
        int white = game.count(OthelloGame.WHITE);
        int black = game.count(OthelloGame.BLACK);
        int winner = white < black ? OthelloGame.BLACK : OthelloGame.WHITE;
        int score = game.count(winner) * 1000 / (game.size() * game.size());
        String name = (game.isAI(winner) ? "[AI]" : "") + username;

        JSONObject save = loadSave(app);
        JSONArray scores = save.getJSONArray("scores");
        int index = 0;
        while (index < scores.size() && scores.getJSONObject(index).getInt("score") >= score) {
            index++;
        }
        if (index < scores.size()) {
            for (int i = scores.size() - 1; i > index; i--) {
                scores.setJSONObject(i, scores.getJSONObject(i - 1));
            }
            JSONObject entry = new JSONObject();
            entry.setString("name", name);
            entry.setInt("score", score);
            scores.setJSONObject(index, entry);
            SaveData.save(app, save);
        }
        entries = null;
    }

    private List<Entry> load(PApplet app) {
        JSONArray scores = loadSave(app).getJSONArray("scores");
        List<Entry> loaded = new ArrayList<>();
        for (int i = 0; i < scores.size(); i++) {
            JSONObject entry = scores.getJSONObject(i);
            loaded.add(new Entry(entry.getString("name"), entry.getInt("score")));
        }
        return List.copyOf(loaded);
    }

    private JSONObject loadSave(PApplet app) {
        JSONObject save = SaveData.load(app);
        if (!(save.get("scores") instanceof JSONArray)) {
            throw new IllegalStateException("Save file must contain a scores array");
        }
        return save;
    }
}
