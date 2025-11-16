import greenfoot.*;

public class GameTitle extends Actor {
    public GameTitle(String title) {
        GreenfootImage img = new GreenfootImage(500, 100);
        img.setColor(new Color(255, 255, 255, 0));
        img.fillRect(0, 0, img.getWidth(), img.getHeight());

        img.setColor(Color.BLACK);
        Font f = new Font("Arial", true, false, 42);
        img.setFont(f);
        int textWidth = (new GreenfootImage(title, 42, Color.BLACK, new Color(0,0,0,0))).getWidth();
        img.drawString(title, Math.max(0, (img.getWidth() - textWidth) / 2), 65);

        setImage(img);
    }
}
