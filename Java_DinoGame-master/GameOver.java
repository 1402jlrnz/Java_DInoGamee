import greenfoot.*;  // Import Greenfoot classes

public class GameOver extends Actor
{
    private int finalScore;

    public GameOver(int score)
    {
        finalScore = score;
        setImage(makeImage());
    }

    private GreenfootImage makeImage()
    {
        GreenfootImage img = new GreenfootImage(380, 140);
        img.setColor(new Color(255, 255, 255, 230));
        img.fillRect(0, 0, 380, 140);

        img.setColor(new Color(0, 0, 0));

        // Use greenfoot.Font instead of java.awt.Font
        Font titleFont = new Font("Arial", true, false, 28); // bold = true, italic = false
        img.setFont(titleFont);
        img.drawString("GAME OVER", 90, 40);

        Font scoreFont = new Font("Arial", false, false, 18);
        img.setFont(scoreFont);
        img.drawString("Final Score: " + finalScore, 110, 80);
        img.drawString("Press R to Restart", 95, 110);

        return img;
    }

    public void act()
    {
        if (Greenfoot.isKeyDown("r"))
        {
            Greenfoot.setWorld(new GameWorld());
        }
    }
}
