import greenfoot.*;

public class Stage1Obstacle extends Actor {
    private int speed;
    
    public Stage1Obstacle(int gameSpeed) {
        speed = gameSpeed;
        // Loads the simple cactus image
        setImage("obstacle_cactus.png"); 
        // Optional: Rescale the image here if needed, e.g., getImage().scale(30, 60);
    }
    
    public void act() {
        World w = getWorld();
        if (w instanceof GameWorld && ((GameWorld) w).isGameOver()) return;
        // Basic scrolling movement
        setLocation(getX() - speed, getY());
        
        // Remove object when it moves off-screen
        if (getX() < -getImage().getWidth()) {
            getWorld().removeObject(this);
        }
    }
}