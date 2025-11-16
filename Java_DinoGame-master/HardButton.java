import greenfoot.*;

public class HardButton extends DifficultyButton {
    public HardButton() {
        super("hard"); // Passes "hard" to the base class
        
        GreenfootImage image = new GreenfootImage("button_hard.png");
       int originalWidth = image.getWidth();
        int originalHeight = image.getHeight();
        
        int newWidth = 120; // Desired new width
        // Calculate new height to maintain aspect ratio
        int newHeight = (int) (originalHeight * ((double) newWidth / originalWidth));
        
        image.scale(newWidth, newHeight); // Rescale with calculated dimensions
        setImage(image);
    }
}