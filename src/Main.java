import processing.core.PApplet;

import scene.SceneManager;

public class Main extends PApplet {
    SceneManager scene = new SceneManager();

    public static void main(String[] args) {
        PApplet.main(Main.class.getName());
    }

    @Override
    public void settings() {
        size(800, 500);
    }

    @Override
    public void setup() {
        surface.setIcon(loadImage("assets/ai_character01_smile.png"));
        surface.setResizable(true);
        rectMode(CENTER);
    }

    @Override
    public void draw() {
        scene.draw(this);
    }
}