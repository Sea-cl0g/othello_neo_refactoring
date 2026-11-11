package scene;

final class MenuColors {
    static final MenuColors DEFAULT = new MenuColors(
            0xFFD2D2D2, 0x96323232, 0xFFFFFFFF, 0x96000000);

    final int background;
    final int panel;
    final int text;
    final int shadow;

    private MenuColors(int background, int panel, int text, int shadow) {
        this.background = background;
        this.panel = panel;
        this.text = text;
        this.shadow = shadow;
    }
}
