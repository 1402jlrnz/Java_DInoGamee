import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Dino extends Actor
{
    // Animation
    private GreenfootImage[] runFrames;
    private GreenfootImage jumpImage;
    private int frameIndex = 0;
    private int animationDelay = 6;
    private int animationTimer = 0;

    // Jump physics
    private boolean jumping = false;
    private double velY = 0;
    private final double gravity = 0.8;     // Slightly stronger gravity for snappier movement
    private final double baseJumpStrength = -9;  // Initial jump lift
    private final double maxJumpBoost = -16;     // Max possible upward boost
    private double jumpStrength = baseJumpStrength;
    private boolean jumpKeyHeld = false;
    private int holdTimer = 0;
    private final int maxHoldFrames = 12; // Allow longer press for higher jump

    // Status
    private boolean alive = true;

    public Dino()
    {
        // Load animation frames
        runFrames = new GreenfootImage[2];
        runFrames[0] = new GreenfootImage("dino_run1.png");
        runFrames[1] = new GreenfootImage("dino_run2.png");
        jumpImage = new GreenfootImage("dino_jump.png");

        // Placeholder if missing
        if (runFrames[0] == null || runFrames[0].getWidth() == 0) {
            GreenfootImage placeholder = new GreenfootImage(60, 60);
            placeholder.setColor(Color.GREEN);
            placeholder.fillRect(0, 0, 60, 60);
            runFrames[0] = placeholder;
            runFrames[1] = placeholder;
            jumpImage = placeholder;
        }
        setImage(runFrames[0]);
    }

    public void act()
    {
        if (!alive) return;

        handleJump();
        applyPhysics();
        animateRun();
        checkCollision();
    }

    // --- Jump Handling ---
    private void handleJump()
    {
        // Start jump
        if (!jumping && Greenfoot.isKeyDown("space")) {
            jumping = true;
            velY = baseJumpStrength;
            holdTimer = 0;
            jumpKeyHeld = true;
        }

        // Boost upward while space is held (for variable jump height)
        if (jumping && jumpKeyHeld && Greenfoot.isKeyDown("space")) {
            if (holdTimer < maxHoldFrames) {
                velY -= 0.6; // smaller, smoother boost each frame
                holdTimer++;
            }
        }

        // Stop boost when space released
        if (!Greenfoot.isKeyDown("space")) {
            jumpKeyHeld = false;
        }
    }

    // --- Apply Gravity and Move Dino ---
    private void applyPhysics()
    {
        if (jumping) {
            velY += gravity;
            setLocation(getX(), (int)(getY() + velY));
            setImage(jumpImage);

            // Landing check
            int groundLevel = GameWorld.groundY - (getImage().getHeight() / 2);
            if (getY() >= groundLevel) {
                setLocation(getX(), groundLevel);
                jumping = false;
                velY = 0;
            }
        }
    }

    // --- Run Animation ---
    private void animateRun()
    {
        if (!jumping) {
            animationTimer++;
            if (animationTimer >= animationDelay) {
                frameIndex = (frameIndex + 1) % runFrames.length;
                setImage(runFrames[frameIndex]);
                animationTimer = 0;
            }
        }
    }

    // --- Collision ---
    private void checkCollision()
    {
        Actor obstacle = getAdjustedCollision();
        if (obstacle != null) {
            alive = false;
            // Optional: Greenfoot.playSound("die.wav");
        }
    }

    // Custom collision detection with smaller hitbox
    private Actor getAdjustedCollision()
    {
        GreenfootImage img = getImage();
        int shrinkX = img.getWidth() / 4;  // 25% smaller hitbox horizontally
        int shrinkY = img.getHeight() / 3; // 33% smaller hitbox vertically

        for (int x = -shrinkX; x <= shrinkX; x += shrinkX / 2) {
            for (int y = -shrinkY; y <= shrinkY; y += shrinkY / 2) {
                Actor obstacle = getOneObjectAtOffset(x, y, Obstacle.class);
                if (obstacle != null) return obstacle;
            }
        }
        return null;
    }

    public boolean isHit()
    {
        return !alive;
    }
}
