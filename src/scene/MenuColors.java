package scene;

final class MenuColors {
    static final MenuColors DEFAULT = new MenuColors(
            0xFFD2D2D2, 0x96323232, 0xFFFFFFFF, 0x96000000,
            0xFF43B370, 0xFF000000, 0xFF000000, 0xFFFFFFFF, 0xFFEBDE34);

    final int background;
    final int panel;
    final int text;
    final int shadow;
    final int board;
    final int grid;
    final int black;
    final int white;
    final int hint;

    private MenuColors(int background, int panel, int text, int shadow,
            int board, int grid, int black, int white, int hint) {
        this.background = background;
        this.panel = panel;
        this.text = text;
        this.shadow = shadow;
        this.board = board;
        this.grid = grid;
        this.black = black;
        this.white = white;
        this.hint = hint;
    }
}
