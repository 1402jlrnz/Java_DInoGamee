import greenfoot.*;

public class DifficultyButton extends Actor {
    private String difficulty; // Stores "easy", "medium", or "hard"

    public DifficultyButton(String text, String diff) {
        // Create an image for the button
        // This line creates a basic white box with the text in black
        GreenfootImage img = new GreenfootImage(text, 30, Color.BLACK, new Color(255, 255, 255, 180));
        setImage(img);
        this.difficulty = diff;
    }

    public void act() {
        if (Greenfoot.mouseClicked(this)) {
            // Cast the world to your GameWorld class
            GameWorld world = (GameWorld) getWorld();
            // Call the new method we will add to GameWorld
            world.startGame(difficulty); 
        }
    }
}