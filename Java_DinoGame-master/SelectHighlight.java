import greenfoot.*;

public class SelectHighlight extends Actor {
    public SelectHighlight() {
        try {
            GreenfootImage img = new GreenfootImage("select.png");
            // Scale down to fit under 120px-wide buttons (use ~140px for slight padding)
            int originalW = img.getWidth();
            int originalH = img.getHeight();
            int targetW = 350;
            int targetH = (int) (originalH * ((double) targetW / originalW));
            img.scale(targetW, targetH);
            setImage(img);
        } catch (Exception e) {
            // Fallback: subtle translucent rectangle if asset missing
            GreenfootImage img = new GreenfootImage(200, 100);
            img.setColor(new Color(255, 255, 0, 80));
            img.fillRect(0, 0, img.getWidth(), img.getHeight());
            setImage(img);
        }
    }
}
