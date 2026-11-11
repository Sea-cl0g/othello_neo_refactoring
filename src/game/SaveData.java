package game;

import java.io.File;
import processing.core.PApplet;
import processing.data.JSONObject;

public final class SaveData {
    private static final String PATH = "save_data.json";

    private SaveData() {
    }

    public static JSONObject load(PApplet app) {
        File file = app.dataFile(PATH);
        if (!file.isFile()) {
            throw new IllegalStateException("Save file not found: " + file);
        }
        JSONObject save = PApplet.loadJSONObject(file);
        if (save == null) {
            throw new IllegalStateException("Invalid save file: " + file);
        }
        return save;
    }

    public static void save(PApplet app, JSONObject save) {
        File file = app.dataFile(PATH);
        if (!app.saveJSONObject(save, file.getAbsolutePath())) {
            throw new IllegalStateException("Unable to save: " + file);
        }
    }
}
