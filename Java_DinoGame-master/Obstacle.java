import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Obstacle extends Actor
{
    private int speed;

    public Obstacle(int gameSpeed)
    {
        speed = gameSpeed;
        // randomly choose an image variant
        int pick = Greenfoot.getRandomNumber(2);
        String filename = (pick == 0) ? "cactus1.png" : "cactus2.png";
        GreenfootImage img = new GreenfootImage(filename);
        if (img == null || img.getWidth() == 0) {
            // fallback simple image
            img = new GreenfootImage(40, 60);
            img.setColor(Color.GREEN.darker());
            img.fillRect(0, 0, 40, 60);
        }
        setImage(img);
    }

    public void act()
    {
        setLocation(getX() - speed, getY());
        // remove when off-screen
        if (getX() < -getImage().getWidth()) {
            getWorld().removeObject(this);
        }
        // stop movement when game is stopped
    }
}
