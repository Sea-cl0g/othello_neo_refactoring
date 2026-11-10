package scene;

import processing.core.PApplet;

public interface Scene {
    public abstract void draw(PApplet app);
    public abstract void mousePressed();
    public abstract void keyPressed();
}