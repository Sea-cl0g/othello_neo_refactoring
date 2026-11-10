package scene;

public class SceneMenu implements Scene {
    private static SceneMenu instance = new SceneMenu();
    
    private SceneMenu(){   
    }

    public static Scene getInstance(){
        return instance;
    }

    @Override
    public void draw(){
        System.err.println("test");
    }

    @Override
    public void mousePressed(){
        System.err.println("mouse");
    }

    @Override
    public void keyPressed(){
        System.err.println("key");
    }
}
