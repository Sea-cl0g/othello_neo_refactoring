package scene;

public class MenuScene implements Scene {
    private static MenuScene instance = new MenuScene();
    
    private MenuScene(){   
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
