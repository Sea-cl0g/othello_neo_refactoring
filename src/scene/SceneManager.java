package scene;

import processing.core.PApplet;

public class SceneManager {
    private int lastWidth = -1;
    private int lastHeight = -1;
    public boolean windowResized;

    private Scene scene = SceneMenu.getInstance();
    
    public void draw(PApplet app){
        if (lastWidth != app.width || lastHeight != app.height) {
            scene.applyLayout(app.width, app.height);
            lastWidth = app.width;
            lastHeight = app.height;
            windowResized = false;
        }

        scene.draw(app);
    }

    public void mousePressed(PApplet app){
        scene.mousePressed(app);
    }
}
