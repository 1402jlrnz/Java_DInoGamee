import greenfoot.*;

public class Stage2Obstacle extends Actor {
    private int speed;
    private final int OBSTACLE_WIDTH = 70; 
    private final int OBSTACLE_HEIGHT = 50;
    
    public Stage2Obstacle(int gameSpeed) {
        speed = gameSpeed;
        GreenfootImage image = new GreenfootImage("obstacle_pterodactyl.png");
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