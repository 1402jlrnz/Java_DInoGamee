import greenfoot.*;

public class ExitButton extends Actor {
    public ExitButton() {
        try {
            GreenfootImage img = new GreenfootImage("exit.png");
            int targetW = 80;
            int targetH = 80;
            img.scale(targetW, targetH);
            setImage(img);
        } catch (Exception e) {
            GreenfootImage img = new GreenfootImage(80, 30);
            img.setColor(new Color(255, 255, 255, 235));
            img.fillRect(0, 0, img.getWidth(), img.getHeight());
            img.setColor(Color.BLACK);
            img.setFont(new Font("Arial", true, false, 18));
            img.drawString("Exit", 20, 22);
            setImage(img);
        }
    }

    public void act() {
        if (Greenfoot.mouseClicked(this)) {
            Greenfoot.setWorld(new MenuWorld());
        }
    }
}
