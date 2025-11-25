import greenfoot.*;

public class StartText extends Actor
{
    public StartText()
    {
        GreenfootImage img = new GreenfootImage(420, 80);

        // Background with transparency (plain rectangle)
        img.setColor(new Color(255, 255, 255, 220));
        img.fillRect(0, 0, 420, 80); // changed from fillRoundRect()

        // Text color and font
        img.setColor(Color.BLACK);
        Font f = new Font("Arial", true, false, 20); // bold = true, italic = false
        img.setFont(f);

        // Draw text
        img.drawString("Press SPACE or Click to Start", 40, 50);

        setImage(img);
    }
}
