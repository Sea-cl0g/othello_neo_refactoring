import processing.core.PApplet;

import scene.SceneManager;

public class Main extends PApplet {
    SceneManager scene = new SceneManager();

    public static void main(String[] args) {
        PApplet.main(Main.class.getName());
    }

    @Override
    public void settings() {
        size(800, 600);
    }

    @Override
    public void setup() {
        rectMode(CENTER);
    }

    @Override
    public void draw() {
        scene.draw();
    }
}