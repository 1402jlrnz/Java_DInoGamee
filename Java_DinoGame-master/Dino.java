import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Dino extends Actor
{
    // Animation
    private GreenfootImage[] runFrames;
    private GreenfootImage jumpImage;
    private int frameIndex = 0;
    private int animationDelay = 6;
    private int animationTimer = 0;
    
    private int currentStage = 1;
    // --- NEW: Resizing Constant ---
    private final int DINO_SIZE = 70; // Adjust this value to change the size of the Dino
    // ----------------------------

    // Jump physics
    private boolean jumping = false;
    private double velY = 0;
    private final double gravity = 0.8;      // Slightly stronger gravity for snappier movement
    private final double baseJumpStrength = -10;  // Initial jump lift
    private boolean jumpKeyHeld = false;
    private int holdTimer = 0;
    private final int maxHoldFrames = 12; // Allow longer press for higher jump

    // Status
    private boolean alive = true;

    public Dino()
    {
        // 1. Setup Image Files and Resizing
        runFrames = new GreenfootImage[2];
        
        // Load and Rescale Running Frames
        runFrames[0] = new GreenfootImage("dino_run1.png");
        runFrames[1] = new GreenfootImage("dino_run2.png");
        
        // Load and Rescale Jumping Frame
        jumpImage = new GreenfootImage("dino_jump.png"); 
        
        // Apply Resizing to all frames
        for (int i = 0; i < runFrames.length; i++) {
            if (runFrames[i] != null) {
                runFrames[i].scale(DINO_SIZE, DINO_SIZE); 
            }
        }
        if (jumpImage != null) {
            jumpImage.scale(DINO_SIZE, DINO_SIZE);
        }
        
        // 2. Placeholder check (Kept your robust fallback logic)
        if (runFrames[0] == null || runFrames[0].getWidth() == 0) {
             GreenfootImage placeholder = new GreenfootImage(DINO_SIZE, DINO_SIZE);
             placeholder.setColor(Color.GREEN);
             placeholder.fillRect(0, 0, DINO_SIZE, DINO_SIZE);
             runFrames[0] = placeholder;
             runFrames[1] = placeholder;
             jumpImage = placeholder;
        }
        
        setImage(runFrames[0]);
    }
    // --- NEW Variable to track Dino's current immunity stage ---

// --- Method called by GameWorld on level up ---
public void levelUp(int newLevel) {
    if (newLevel > currentStage) {
        currentStage = newLevel;
        
        // --- DINO GROWTH LOGIC ---
        // Example: Dino gets 10% bigger each stage
        GreenfootImage currentImage = getImage();
        if (currentImage != null) {
            int newSize = (int)(DINO_SIZE * (1 + (0.1 * (currentStage - 1))));
            currentImage.scale(newSize, newSize);
            // NOTE: You would need to update ALL frames (runFrames, jumpImage) 
            // if you wanted the running animation to also be bigger. 
            // For simplicity, we are only changing the currently displayed image size here.
        }
    }
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
            Greenfoot.playSound("jump.mp3");
            holdTimer = 0;
            jumpKeyHeld = true;
        }

        // Boost upward while space is held (for variable jump height)
        if (jumping && jumpKeyHeld && Greenfoot.isKeyDown("space")) {
            // Apply boost only when moving up or near the peak
            if (velY < 0.8) { 
                velY -= 0.6; 
            }
            holdTimer++;
        }

        // Stop boost when space released or maximum boost time reached
        if (!Greenfoot.isKeyDown("space") || holdTimer >= maxHoldFrames) {
            jumpKeyHeld = false;
        }
    }

    // --- Apply Gravity and Move Dino ---
  // --- Apply Gravity and Move Dino (Fixed Landing Logic) ---
private void applyPhysics()
{
    if (jumping) {
        velY += gravity;
        setLocation(getX(), (int)(getY() + velY));
        
        // 1. Calculate the Y-coordinate where the Dino's center should be when standing
        int groundLevel = GameWorld.groundY - (getImage().getHeight() / 2) + GameWorld.dinoGroundOverlap;
        
        // 2. Landing check: Check if the dino has hit or passed the ground level
        if (getY() >= groundLevel) {
            
            // 3. FIX: Force the dino's location to be EXACTLY the groundLevel
            setLocation(getX(), groundLevel); 
            
            jumping = false;
            velY = 0;
        }
    }
}

    // --- Run Animation (Now correctly shows jump image when in the air) ---
    private void animateRun()
    {
        if (jumping) {
            // Show jump image instantly if jumping
            setImage(jumpImage);
            animationTimer = 0; 
        } else {
            // Running animation
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
    // Find any obstacle the Dino is currently touching
    Actor obstacle = getAdjustedCollision(); 
    
    if (obstacle != null) {
        
        // --- NEW IMMUNITY CHECK LOGIC ---
        
        // 1. Check Stage 1 Obstacles (Cactus)
        if (obstacle instanceof Stage1Obstacle) {
            if (currentStage > 1) { // Dino is immune in Stage 2, 3, or 4
                getWorld().removeObject(obstacle);
                return; // Stop processing collision
            }
        }
        
        // 2. Check Stage 2 Obstacles (Pterodactyl)
        else if (obstacle instanceof Stage2Obstacle) {
            if (currentStage > 2) { // Dino is immune in Stage 3 or 4
                getWorld().removeObject(obstacle);
                return;
            }
        }
        
        // 3. Check Stage 3 Obstacles (Rock Formation)
        else if (obstacle instanceof Stage3Obstacle) {
            if (currentStage > 3) { // Dino is immune in Stage 4
                getWorld().removeObject(obstacle);
                return;
            }
        }
        
        // 4. Check Stage 4 Obstacles (Lava Pit)
        // Dino is never immune to the highest current threat (Stage 4 in this case)
        
        // --- GAME OVER ---
        // If the code reaches here, the obstacle is the current threat or higher.
        alive = false;
        // Optional: Greenfoot.playSound("die.wav");
    }
}

    // Custom collision detection with smaller hitbox
    private Actor getAdjustedCollision()
{
    // Check for an obstacle directly overlapping the Dino's location.
    // Use an offset to simulate a smaller hitbox for better player feel.
    // Try shifting the check slightly forward (e.g., +10 pixels) to hit obstacles sooner.
    
    // Check slightly ahead of the Dino and slightly above the center (to account for feet)
    Actor obstacle = getOneObjectAtOffset(10, 15, Actor.class); 
    
    // Ensure the detected object is one of your obstacle types
    if (obstacle instanceof Stage1Obstacle || 
        obstacle instanceof Stage2Obstacle ||
        obstacle instanceof Stage3Obstacle ||
        obstacle instanceof Stage4Obstacle) 
    {
        return obstacle;
    }
    return null;
}
    public boolean isHit()
    {
        return !alive;
    }
}