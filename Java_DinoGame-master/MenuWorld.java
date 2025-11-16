import greenfoot.*;

public class MenuWorld extends World {
    public MenuWorld() {
        super(800, 400, 1, false);
        setBackgroundImage("intro.png");
        prepare();
    }

    private void setBackgroundImage(String filename) {
        try {
            GreenfootImage bg = new GreenfootImage(filename);
            bg.scale(getWidth(), getHeight());
            setBackground(bg);
        } catch (Exception e) {
            GreenfootImage plain = new GreenfootImage(getWidth(), getHeight());
            plain.setColor(Color.BLACK);
            plain.fill();
            setBackground(plain);
        }
    }

   
    public void act() {
        String key = Greenfoot.getKey();
        if ("enter".equals(key) || "return".equals(key)) {
            Greenfoot.setWorld(new GameWorld());
        }
    }

    private void prepare() {
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        addObject(new PlayButton(), centerX, centerY + 140);
    }
}
