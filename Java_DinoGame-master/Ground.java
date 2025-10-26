import greenfoot.*;

public class Ground extends Actor
{
    public Ground()
    {
        GreenfootImage img = new GreenfootImage("ground.png");
        img.scale(800, 80); // scale to world width and keep short height
        setImage(img);
    }

    public void scroll(int speed)
    {
        setLocation(getX() - speed, getY());
    }
}
