package scene;

import processing.core.PApplet;

public class SceneManager {
    private int lastWidth = -1;
    private int lastHeight = -1;
    public boolean windowResized;

    private Scene scene = SceneMenu.getInstance();
    
    public void draw(PApplet app){
        windowResized = lastWidth != app.width || lastHeight != app.height;
        lastWidth = app.width;
        lastHeight = app.height;
        scene.draw(app, windowResized);
    }

    
}
