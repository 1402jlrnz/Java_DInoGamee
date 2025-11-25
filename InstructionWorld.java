import greenfoot.*;

public class InstructionWorld extends World {
    private int currentPage = 1;
    private final int TOTAL_PAGES = 2;

    public InstructionWorld() {
        super(800, 400, 1, false);
        setPageBackground();
        addButtons();
    }

    private void setPageBackground() {
        String filename = "map.png";    // always use the single map.png
        try {
            GreenfootImage bg = new GreenfootImage(filename);

            // --- adjust these factors to control map size ---
            int targetW = (int)(getWidth() * 0.80);   // 80% of world width
            int targetH = (int)(getHeight() * 0.80);  // 80% of world height
            bg.scale(targetW, targetH);

            // create a background canvas
            GreenfootImage canvas = new GreenfootImage(getWidth(), getHeight());
            canvas.setColor(Color.BLACK);   // background color behind the map
            canvas.fill();

            // center the scaled map on the canvas
            int x = (getWidth() - targetW) / 2;
            int y = (getHeight() - targetH) / 2;
            canvas.drawImage(bg, x, y);

            int offsetX = -35;  // move all text slightly to the left

            // --- Page 1: jumping mechanic text (centered) ---
            if (currentPage == 1) {
                canvas.setColor(Color.WHITE);
                Font font = new Font("Arial", true, false, 18);
                canvas.setFont(font);

                String line1 = "Jumping: Press SPACE to jump.";
                String line2 = "Time your jumps to avoid obstacles.";

                int lineSpacing = 24;          // vertical distance between lines
                int lines = 2;
                int totalHeight = lineSpacing * (lines - 1);

                // center vertically around the middle of the screen
                int baseY = (canvas.getHeight() / 2) - (totalHeight / 2);

                int x1 = getCenteredX(canvas, line1) + offsetX;
                int x2 = getCenteredX(canvas, line2) + offsetX;

                canvas.drawString(line1, x1, baseY);
                canvas.drawString(line2, x2, baseY + lineSpacing);
            }

            // --- Page 2: level-up immunity explanation (centered) ---
            if (currentPage == 2) {
                canvas.setColor(Color.WHITE);
                Font font = new Font("Arial", true, false, 18);
                canvas.setFont(font);

                String line1 = "Level Up & Immunity:";
                String line2 = "- Each stage makes you immune to previous obstacles.";
                String line3 = "- Example: In Stage 2 you ignore Stage 1 (cactus).";
                String line4 = "- In higher stages you only get hit by the current stage.";

                int lineSpacing = 22;
                int lines = 4;
                int totalHeight = lineSpacing * (lines - 1);

                int baseY = (canvas.getHeight() / 2) - (totalHeight / 2);

                int x1 = getCenteredX(canvas, line1) + offsetX;
                int x2 = getCenteredX(canvas, line2) + offsetX;
                int x3 = getCenteredX(canvas, line3) + offsetX;
                int x4 = getCenteredX(canvas, line4) + offsetX;

                canvas.drawString(line1, x1, baseY);
                canvas.drawString(line2, x2, baseY + lineSpacing);
                canvas.drawString(line3, x3, baseY + lineSpacing * 2);
                canvas.drawString(line4, x4, baseY + lineSpacing * 3);
            }

            setBackground(canvas);
        } catch (Exception e) {
            GreenfootImage plain = new GreenfootImage(getWidth(), getHeight());
            plain.setColor(Color.BLACK);
            plain.fill();
            setBackground(plain);
        }
    }

    // Helper to horizontally center text on the canvas
    private int getCenteredX(GreenfootImage canvas, String text) {
        // 18 must match the font size used above
        GreenfootImage temp = new GreenfootImage(text, 18, Color.WHITE, new Color(0, 0, 0, 0));
        int textWidth = temp.getWidth();
        return (canvas.getWidth() - textWidth) / 2;
    }

    private void addButtons() {
        int centerX = getWidth() / 2;
        int bottomY = getHeight() - 40;

        addObject(new PreviousButton(), centerX - 120, bottomY);
        addObject(new NextButton(), centerX + 120, bottomY);
        addObject(new ExitButton(), getWidth() - 60, 40);
    }

    public void nextPage() {
        if (currentPage < TOTAL_PAGES) {
            currentPage++;
            setPageBackground();   // still uses map.png
        }
    }

    public void previousPage() {
        if (currentPage > 1) {
            currentPage--;
            setPageBackground();   // still uses map.png
        }
    }

    public void act() {
        String key = Greenfoot.getKey();
        if ("escape".equals(key) || "backspace".equals(key)) {
            Greenfoot.setWorld(new MenuWorld());
        }
    }
}