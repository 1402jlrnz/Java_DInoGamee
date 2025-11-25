import greenfoot.*;

public class PlayButton extends Actor {
    public PlayButton() {
        try {
            GreenfootImage img = new GreenfootImage("play.png");
            setImage(img);
        } catch (Exception e) {
            // Fallback: simple text button if asset missing
            GreenfootImage img = new GreenfootImage(200, 60);
            img.setColor(new Color(255, 255, 255, 235));
            img.fillRect(0, 0, img.getWidth(), img.getHeight());
            img.setColor(Color.BLACK);
            img.setFont(new Font("Arial", true, false, 28));
            img.drawString("Play", 80, 40);
            setImage(img);
        }
    }

    protected void addedToWorld(World w) {
        GreenfootImage img = getImage();
        if (img == null) return;

        int worldW = w.getWidth();
        int worldH = w.getHeight();

        // 1) Trim transparent padding so scaling uses only visible content
        img = trimTransparentBorders(img);
        setImage(img);

        // 2) Compute target size (smaller than before)
        int targetW = Math.max(240, Math.min(480, (int)(worldW * 0.60)));
        int targetH = (int)((double) img.getHeight() * ((double) targetW / (double) img.getWidth()));

        // Height clamp to avoid over-tall images
        int maxH = Math.max(80, Math.min(110, (int)(worldH * 0.22)));
        if (targetH > maxH) {
            targetH = maxH;
            targetW = (int)((double) img.getWidth() * ((double) targetH / (double) img.getHeight()));
        }

        // 3) Apply scaling if needed
        if (img.getWidth() != targetW || img.getHeight() != targetH) {
            img.scale(targetW, targetH);
        }

        // 4) Set the resized image back to the actor
        setImage(img);
    }

    private GreenfootImage trimTransparentBorders(GreenfootImage src) {
        int w = src.getWidth();
        int h = src.getHeight();
        int minX = w, minY = h, maxX = -1, maxY = -1;
        // Consider pixels with alpha > 10 as visible (to ignore subtle antialias fuzz)
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                Color c = src.getColorAt(x, y);
                if (c.getAlpha() > 10) {
                    if (x < minX) minX = x;
                    if (y < minY) minY = y;
                    if (x > maxX) maxX = x;
                    if (y > maxY) maxY = y;
                }
            }
        }
        if (maxX < minX || maxY < minY) {
            return src; // no visible pixels found
        }
        int newW = maxX - minX + 1;
        int newH = maxY - minY + 1;
        if (newW == w && newH == h) return src; // already tight

        GreenfootImage trimmed = new GreenfootImage(newW, newH);
        trimmed.drawImage(src, -minX, -minY);
        return trimmed;
    }

    public void act() {
        if (Greenfoot.mouseClicked(this)) {
            Greenfoot.setWorld(new GameWorld());
        }
    }
}
