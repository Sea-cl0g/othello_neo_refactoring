package scene;

import processing.core.PApplet;

public interface Scene {
    public abstract void draw(PApplet app);
    public abstract void applyLayout(int width, int height);
    public abstract void mousePressed(PApplet app);
    public abstract void keyPressed(PApplet app);
}