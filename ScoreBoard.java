import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot, Font, Color, etc.)

public class ScoreBoard extends Actor
{
    private int score = 0;
    private Color textColor = Color.BLACK;

    public ScoreBoard()
    {
        updateImage();
    }

    public void addScore(int amount)
    {
        score += amount;
        updateImage();
    }

    public int getScore()
    {
        return score;
    }

    private void updateImage()
    {
        String txt = "Score: " + score;
        GreenfootImage image = new GreenfootImage(140, 32);

        // transparent background
        image.setColor(new Color(0, 0, 0, 0));
        image.fill();

        // Use greenfoot.Font instead of java.awt.Font
        Font font = new Font("Arial", true, false, 16); // bold = true, italic = false
        image.setFont(font);

        // Draw text
        image.setColor(textColor);
        image.drawString(txt, 6, 20);

        setImage(image);
    }

    public void setTextColor(Color c) {
        if (c != null) {
            textColor = c;
            updateImage();
        }
    }
}
