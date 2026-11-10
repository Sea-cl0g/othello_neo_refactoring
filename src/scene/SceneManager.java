package scene;

public class SceneManager {
    private Scene scene = SceneMenu.getInstance();
    
    public void draw(){
        scene.draw();
    }

    
}
