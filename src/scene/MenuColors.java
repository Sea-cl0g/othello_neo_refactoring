package scene;

import java.io.IOException;
import java.nio.file.Files;
import processing.core.PApplet;
import processing.data.JSONArray;
import processing.data.JSONObject;

final class MenuColors {
    static final MenuColors DEFAULT = new MenuColors(
            0xFFD2D2D2, 0x96323232, 0xFFFFFFFF, 0x96000000,
            0xFF43B370, 0xFF323232, 0xFF000000, 0xFF000000, 0xFFFFFFFF, 0xFFEBDE34,
            0xFFFF0000);

    final int background;
    final int panel;
    final int text;
    final int shadow;
    final int board;
    final int frame;
    final int grid;
    final int black;
    final int white;
    final int hint;
    final int alert;

    static MenuColors load(PApplet app, String path) throws IOException {
        String source = Files.readString(app.dataFile(path).toPath());
        JSONObject theme;
        try {
            theme = JSONObject.parse(source);
        } catch (RuntimeException error) {
            // Processing's parser reports syntax errors as plain RuntimeException.
            throw new IllegalArgumentException("Invalid theme JSON: " + path, error);
        }
        if (!(theme.get("colors") instanceof JSONObject colors)) {
            throw new IllegalArgumentException("Theme must contain a colors object: " + path);
        }
        return new MenuColors(
                color(colors, "back_col"), color(colors, "displayBack_col"),
                color(colors, "displayText_col"), color(colors, "displayTextShadow_col"),
                color(colors, "board_col"), color(colors, "boardFrame_col"), color(colors, "grid_col"),
                color(colors, "stone2_col"), color(colors, "stone1_col"), DEFAULT.hint,
                color(colors, "alertText_col"));
    }

    private static int color(JSONObject colors, String key) {
        if (!(colors.get(key) instanceof JSONArray parts) || (parts.size() != 3 && parts.size() != 4)) {
            throw new IllegalArgumentException(key + " must contain RGB or RGBA values");
        }
        int[] values = {0, 0, 0, 255};
        for (int i = 0; i < parts.size(); i++) {
            if (!(parts.get(i) instanceof Number number)
                    || number.doubleValue() < 0 || number.doubleValue() > 255
                    || number.doubleValue() != number.intValue()) {
                throw new IllegalArgumentException(key + " must contain integers in 0..255");
            }
            values[i] = number.intValue();
        }
        return values[3] << 24 | values[0] << 16 | values[1] << 8 | values[2];
    }

    private MenuColors(int background, int panel, int text, int shadow,
            int board, int frame, int grid, int black, int white, int hint, int alert) {
        this.background = background;
        this.panel = panel;
        this.text = text;
        this.shadow = shadow;
        this.board = board;
        this.frame = frame;
        this.grid = grid;
        this.black = black;
        this.white = white;
        this.hint = hint;
        this.alert = alert;
    }
}
