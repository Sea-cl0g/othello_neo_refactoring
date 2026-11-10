package scene;

import processing.core.PApplet;

public class SceneManager {
    private Scene scene = SceneMenu.getInstance();
    
    public void draw(PApplet app){
        scene.draw(app);
    }

    
}
