import greenfoot.*;

public class DifficultyButton extends Actor {
    private String difficulty; 

    public DifficultyButton(String diff) { // Constructor now only takes 'diff'
        // REMOVE THE GreenfootImage img = new GreenfootImage(...) LINE HERE
        // Subclasses will now set their own image
        this.difficulty = diff;
    }

    public void act() {
        if (Greenfoot.mouseClicked(this)) {
            GameWorld world = (GameWorld) getWorld();
            world.startGame(difficulty); 
        }
    }
}