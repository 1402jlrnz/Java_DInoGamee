import greenfoot.*;

public class NextButton extends Actor {
    public NextButton() {
        try {
            GreenfootImage img = new GreenfootImage("next.png");
            // scale smaller
            int targetW = 150;    // try 80x30, adjust as needed
            int targetH = 150;
            img.scale(targetW, targetH);
            setImage(img);
        } catch (Exception e) {
            GreenfootImage img = new GreenfootImage(80, 30);
            img.setColor(new Color(255, 255, 255, 235));
            img.fillRect(0, 0, img.getWidth(), img.getHeight());
            img.setColor(Color.BLACK);
            img.setFont(new Font("Arial", true, false, 18));
            img.drawString("Next", 18, 22);
            setImage(img);
        }
    }

    public void act() {
        if (Greenfoot.mouseClicked(this)) {
            World w = getWorld();
            if (w instanceof InstructionWorld) {
                ((InstructionWorld) w).nextPage();
            }
        }
    }
}