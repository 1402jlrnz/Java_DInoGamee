import greenfoot.*;

public class Stage3Obstacle extends Actor {
    private int speed;
    private final int OBSTACLE_WIDTH = 80; 
    private final int OBSTACLE_HEIGHT = 45;
    
    public Stage3Obstacle(int gameSpeed) {
        speed = gameSpeed;
        GreenfootImage image = new GreenfootImage("obstacle_rock.png");
        image.scale(OBSTACLE_WIDTH, OBSTACLE_HEIGHT);
        setImage(image);
    }
    
    public void act() {
        World w = getWorld();
        if (w instanceof GameWorld && ((GameWorld) w).isGameOver()) return;
        setLocation(getX() - speed, getY());
        if (getX() < -getImage().getWidth()) {
            getWorld().removeObject(this);
        }
    }
}