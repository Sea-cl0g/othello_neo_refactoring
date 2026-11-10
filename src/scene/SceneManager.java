package scene;

public class SceneManager {
    private Scene scene = MenuScene.getInstance();
    
    public void draw(){
        scene.draw();
    }

    
}
