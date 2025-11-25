import greenfoot.*;

public class LevelUpMessage extends Actor {
    private int lifeSpan = 100; // Display for about 2 seconds
    
    public LevelUpMessage(int levelNumber) {
        // Load your single custom "Level Up" image.
        // The 'levelNumber' parameter is still passed but not used to draw text.
        // It might be useful if you had different image files like "levelup1.png", "levelup2.png".
        try {
            GreenfootImage img = new GreenfootImage("levelup_banner.png"); // Make sure this filename is correct
            // Optional: Resize the banner if needed, e.g., img.scale(200, 80);
            setImage(img);
        } catch (IllegalArgumentException e) {
            // Fallback if the image file is missing (shows a simple message box)
            GreenfootImage fallback = new GreenfootImage("LEVEL UP!", 40, Color.WHITE, new Color(0, 0, 0, 180));
            setImage(fallback);
        }
    }
    
    public void act() {
        lifeSpan--;
        
        // Fade the message out slightly as it disappears
        if (lifeSpan < 30) {
            getImage().setTransparency(lifeSpan * 8); 
        }
        
        // Remove the object when the time is up
        if (lifeSpan <= 0) {
            getWorld().removeObject(this);
        }
    }
}