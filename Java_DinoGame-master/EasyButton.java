import greenfoot.*;

public class EasyButton extends DifficultyButton {
    public EasyButton() {
        super("easy");
        
        GreenfootImage image = new GreenfootImage("button_easy.png"); // Load the original
        
        int originalWidth = image.getWidth();
        int originalHeight = image.getHeight();
        
        int newWidth = 120; // Desired new width
        // Calculate new height to maintain aspect ratio
        int newHeight = (int) (originalHeight * ((double) newWidth / originalWidth));
        
        image.scale(newWidth, newHeight); // Rescale with calculated dimensions
        setImage(image);
    }
}