import greenfoot.*;

public class InstructionButton extends Actor {

    public InstructionButton() {
        try {
            GreenfootImage img = new GreenfootImage("instruction.png");

            // ↓ adjust these to control size
            int targetW = 40;    // width in pixels
            int targetH = 40;    // height in pixels

            img.scale(targetW, targetH);
            setImage(img);
        } catch (Exception e) {
            // Fallback: simple text button if asset missing
            GreenfootImage img = new GreenfootImage(30, 30);
            img.setColor(new Color(255, 255, 255, 235));
            img.fillRect(0, 0, img.getWidth(), img.getHeight());
            img.setColor(Color.BLACK);
            img.setFont(new Font("Arial", true, false, 24));
            img.drawString("Instructions", 20, 38);
            setImage(img);
        }
    }
protected void addedToWorld(World w) {
    GreenfootImage img = getImage();
    if (img == null) return;

    int worldW = w.getWidth();
    int worldH = w.getHeight();

    // Same style as PlayButton: scale relative to world size
    int targetW = Math.max(240, Math.min(480, (int)(worldW * 0.60)));
    int targetH = (int)((double) img.getHeight() * ((double) targetW / (double) img.getWidth()));

    int maxH = Math.max(80, Math.min(110, (int)(worldH * 0.22)));
    if (targetH > maxH) {
        targetH = maxH;
        targetW = (int)((double) img.getWidth() * ((double) targetH / (double) img.getHeight()));
    }

    if (img.getWidth() != targetW || img.getHeight() != targetH) {
        img.scale(targetW, targetH);
    }

    setImage(img);
}
    public void act() {
        if (Greenfoot.mouseClicked(this)) {
            Greenfoot.setWorld(new InstructionWorld());
        }
    }
}