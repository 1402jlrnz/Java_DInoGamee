import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Desktop;
import java.io.File;

public class GameWorld extends World {
    
    // --- World State and Game Components ---
    public static int groundY = 360;
    public static int dinoGroundOverlap = 10; // Slight overlap to look grounded
    private int spawnTimer = 0;
    private int spawnInterval; 
    private int gameSpeed;     
    private ScoreBoard scoreBoard;
    private boolean running = false; 
    private boolean gameOver = false;
    private Dino dino;
    private Ground ground1, ground2;
    private GreenfootSound bgm;
    
    // --- Level/Stage System Variables ---
    private int currentLevel = 1; 
    private final int LEVEL_UP_SCORE = 1000; 
    
    // --- Difficulty Constants (Initial Values) ---
    private final int EASY_SPEED = 3;
    private final int EASY_INTERVAL = 110;

    private final int MEDIUM_SPEED = 5;
    private final int MEDIUM_INTERVAL = 90;

    private final int HARD_SPEED = 7;
    private final int HARD_INTERVAL = 70;
    
    private final int INSANE_SPEED = 9;
    private final int INSANE_INTERVAL = 55;
    // ------------------------------------------

    public GameWorld() {   
        super(800, 400, 1, false);
        bgm = new GreenfootSound("arcade_puzzler.mp3");
        bgm.playLoop();
        prepare();
    }
    
    // --- Background Switching Method (NEW) ---
    private void updateBackground(int level) {
        String filename = "bg" + level + ".png";
        
        GreenfootImage bg;
        try {
            bg = new GreenfootImage(filename);
            bg.scale(getWidth(), getHeight());
            setBackground(bg);
        } catch (Exception e) {
            // Fallback: Use a plain color and print error if image is missing
            GreenfootImage plain = new GreenfootImage(800, 400);
            plain.setColor(Color.BLUE);
            plain.fill();
            setBackground(plain);
            System.err.println("Background file not found: " + filename);
        }
    }
    // -----------------------------------------
    
    // --- Method to Start the Game (Called by DifficultyButton Actors) ---
    public void startGame(String difficulty) {
        // 1. Set the initial difficulty variables
        if (difficulty.equals("easy")) {
            gameSpeed = EASY_SPEED;
            spawnInterval = EASY_INTERVAL;
        } else if (difficulty.equals("hard")) {
            gameSpeed = HARD_SPEED;
            spawnInterval = HARD_INTERVAL;
        } else if (difficulty.equals("insane")) {
            gameSpeed = INSANE_SPEED;
            spawnInterval = INSANE_INTERVAL;
        } else { // "medium" or default
            gameSpeed = MEDIUM_SPEED;
            spawnInterval = MEDIUM_INTERVAL;
        }
        
        // 2. Clean up the menu actors
        removeObjects(getObjects(DifficultyButton.class));
        removeObjects(getObjects(StartText.class)); 

        // 3. Start the game!
        running = true;
        Greenfoot.start();
    }
    // --------------------------------------------------

    private void prepare() {
        // ✅ Initial Background Setup (Stage 1)
        updateBackground(1); 

        // ✅ Ground setup
        ground1 = new Ground();
        ground2 = new Ground();
        int groundHeight = ground1.getImage().getHeight();

        addObject(ground1, getWidth() / 2, groundY + (groundHeight / 2));
        addObject(ground2, getWidth() + (getWidth() / 2), groundY + (groundHeight / 2));

        // ✅ Dino
        dino = new Dino();
        addObject(dino, 100, groundY - (dino.getImage().getHeight() / 2) + dinoGroundOverlap); 

        // ✅ Scoreboard
        scoreBoard = new ScoreBoard();
        addObject(scoreBoard, 70, 30);

        // ✅ Difficulty Buttons (Menu Display)
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        int spacing = 120; // Spacing for 4 buttons

        // Add the four buttons using (int) casting
        addObject(new EasyButton(), (int)(centerX - (spacing * 1.5)), centerY);   
        addObject(new MediumButton(), (int)(centerX - (spacing * 0.5)), centerY); 
        addObject(new HardButton(), (int)(centerX + (spacing * 0.5)), centerY);  
        addObject(new InsaneButton(), (int)(centerX + (spacing * 1.5)), centerY); 
    }

    public void act() {
        // Handle restart input after game over
        if (gameOver) {
            if (Greenfoot.isKeyDown("r")) {
                Greenfoot.setWorld(new GameWorld());
            }
            return;
        }

        // Wait for a difficulty button to be clicked
        if (!running) {
            return;
        }

        // ------------------------------------------------------------------
        // --- GAME IS RUNNING BELOW HERE ---
        // ------------------------------------------------------------------

        // ✅ Score update
        int s = scoreBoard.getScore();
        scoreBoard.addScore(1);

        // ✅ Level Advancement Check
        if (currentLevel < 4 && s >= currentLevel * LEVEL_UP_SCORE) {
            currentLevel++;
            dino.levelUp(currentLevel); 
            
            // 🛑 NEW: Change the background to the new level's background
            updateBackground(currentLevel); 
            
            LevelUpMessage message = new LevelUpMessage(currentLevel);
            addObject(message, getWidth() / 2, getHeight() / 2);
        }

        // ✅ Difficulty Scaling (Increases speed/spawn rate every 500 points)
        if (s % 500 == 0 && s != 0) {
            if (gameSpeed < 15) { 
                gameSpeed++;
            }
            if (spawnInterval > 40) { 
                spawnInterval -= 5;
            }
        }

        // ✅ Obstacle Spawning
        spawnTimer++;
        if (spawnTimer >= spawnInterval) {
            spawnTimer = 0;
            spawnObstacle(); 
        }

        // ✅ Ground scrolling and repositioning
        ground1.scroll(gameSpeed);
        ground2.scroll(gameSpeed);

        if (ground1.getX() + ground1.getImage().getWidth() / 2 <= 0)
            ground1.setLocation(ground2.getX() + ground2.getImage().getWidth(), ground1.getY());
        if (ground2.getX() + ground2.getImage().getWidth() / 2 <= 0)
            ground2.setLocation(ground1.getX() + ground1.getImage().getWidth(), ground2.getY());

        // ✅ Collision check
        if (dino.isHit()) {
            showGameOver();
        }
    }

    private void spawnObstacle() {
        
        Actor o = null;
        int spawnType = Greenfoot.getRandomNumber(100); 
        
        int flyingHeight = 300; // Y position for flying obstacles
        int groundAlignmentOffset = 8; // Slight overlap into ground for visual contact

        // Logic to determine which obstacle to spawn based on the current level
        if (currentLevel >= 4) {
            if (spawnType < 65) { o = new Stage4Obstacle(gameSpeed); } 
            else { o = new Stage3Obstacle(gameSpeed); } 
        } else if (currentLevel == 3) {
             if (spawnType < 60) { o = new Stage3Obstacle(gameSpeed); } 
             else { o = new Stage2Obstacle(gameSpeed); } 
        } else if (currentLevel == 2) {
             if (spawnType < 50) { o = new Stage2Obstacle(gameSpeed); } 
             else { o = new Stage1Obstacle(gameSpeed); } 
        } else { // Level 1 only spawns Stage 1 obstacles
            o = new Stage1Obstacle(gameSpeed);
        }
        
        if (o != null) {
            int y;
            // Stage 2 obstacle (Pterodactyl) can now be flying OR near ground
            if (o instanceof Stage2Obstacle) {
                boolean spawnFlying = Greenfoot.getRandomNumber(100) < 50; // 50% chance
                if (spawnFlying) {
                    y = flyingHeight;
                } else {
                    y = groundY - (o.getImage().getHeight() / 2) + groundAlignmentOffset;
                }
            } else {
                // Ground obstacles: Place center so bottom aligns with groundY
                y = groundY - (o.getImage().getHeight() / 2) + groundAlignmentOffset;
            }
            addObject(o, getWidth() + o.getImage().getWidth() / 2, y);
        }
    }

   private void showGameOver() {
    gameOver = true;
    setBackgroundImageSafe("game_over_bg.png");
    if (scoreBoard != null) {
        scoreBoard.setTextColor(Color.WHITE);
    }
    if (bgm != null) {
        bgm.stop();
    }
    Greenfoot.playSound("gameover.mp3");
}

    private void setBackgroundImageSafe(String filename) {
        try {
            GreenfootImage img = new GreenfootImage(filename);
            img.scale(getWidth(), getHeight());
            setBackground(img);
        } catch (Exception e) {
            GreenfootImage fallback = new GreenfootImage(getWidth(), getHeight());
            fallback.setColor(Color.BLACK);
            fallback.fill();
            setBackground(fallback);
        }
    }

    public void stopped() {
        if (bgm != null) {
            bgm.pause();
        }
    }

    public boolean isGameOver() {
        return gameOver;
    }

}