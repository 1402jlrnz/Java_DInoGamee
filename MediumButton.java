import greenfoot.*;

public class MediumButton extends DifficultyButton {
    public MediumButton() {
        super("medium"); // Passes "medium" to the base class
        
        GreenfootImage image = new GreenfootImage("button_medium.png");
        int originalWidth = image.getWidth();
        int originalHeight = image.getHeight();
        
        int newWidth = 120; // Desired new width
        // Calculate new height to maintain aspect ratio
        int newHeight = (int) (originalHeight * ((double) newWidth / originalWidth));
        
        image.scale(newWidth, newHeight); // Rescale with calculated dimensions
        setImage(image);
    }
}