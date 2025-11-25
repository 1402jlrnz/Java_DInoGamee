import greenfoot.*;

public class PreviousButton extends Actor {
    public PreviousButton() {
        try {
            GreenfootImage img = new GreenfootImage("previous.png");
            // scale smaller
            int targetW = 170;   // a bit wider than Next, adjust if needed
            int targetH = 170;
            img.scale(targetW, targetH);
            setImage(img);
        } catch (Exception e) {
            GreenfootImage img = new GreenfootImage(100, 30);
            img.setColor(new Color(255, 255, 255, 235));
            img.fillRect(0, 0, img.getWidth(), img.getHeight());
            img.setColor(Color.BLACK);
            img.setFont(new Font("Arial", true, false, 18));
            img.drawString("Previous", 10, 22);
            setImage(img);
        }
    }

    public void act() {
        if (Greenfoot.mouseClicked(this)) {
            World w = getWorld();
            if (w instanceof InstructionWorld) {
                ((InstructionWorld) w).previousPage();
            }
        }
    }
}