import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class GameWorld extends World {
    
    // --- Existing Class Variables ---
    public static int groundY = 360;
    private int spawnTimer = 0;
    private int spawnInterval; // Set by difficulty selection
    private int gameSpeed;     // Set by difficulty selection
    private ScoreBoard scoreBoard;
    private boolean running = false; // Starts as false (menu state)
    private boolean gameOver = false;
    private Dino dino;
    private Ground ground1, ground2;
    
    // --- Difficulty Constants ---
    private final int EASY_SPEED = 3;
    private final int EASY_INTERVAL = 110;

    private final int MEDIUM_SPEED = 5;
    private final int MEDIUM_INTERVAL = 90;

    private final int HARD_SPEED = 7;
    private final int HARD_INTERVAL = 70;
    // ------------------------------------------

    public GameWorld() {   
        super(800, 400, 1, false);
        // The setDifficulty() text prompt is removed.
        // We now call prepare() to set up the world AND display the buttons.
        prepare(); 
    }
    
    // --- Method to Start the Game (Called by DifficultyButton Actors) ---
    public void startGame(String difficulty) {
        // 1. Set the difficulty variables based on the button's input
        if (difficulty.equals("easy")) {
            gameSpeed = EASY_SPEED;
            spawnInterval = EASY_INTERVAL;
        } else if (difficulty.equals("hard")) {
            gameSpeed = HARD_SPEED;
            spawnInterval = HARD_INTERVAL;
        } else { // "medium" or default
            gameSpeed = MEDIUM_SPEED;
            spawnInterval = MEDIUM_INTERVAL;
        }
        
        // 2. Clean up the menu actors
        // Remove the buttons from the world
        removeObjects(getObjects(DifficultyButton.class));
        
        // 3. Start the game!
        running = true;
        Greenfoot.start();
    }
    // --------------------------------------------------

    private void prepare() {
        // ✅ Background
        GreenfootImage bg;
        try {
            bg = new GreenfootImage("bg.png");
            bg.scale(getWidth(), getHeight());
            setBackground(bg);
        } catch (Exception e) {
            GreenfootImage plain = new GreenfootImage(800, 400);
            plain.setColor(new Color(135, 206, 235));
            plain.fill();
            setBackground(plain);
        }

        // ✅ Ground setup
        ground1 = new Ground();
        ground2 = new Ground();
        int groundHeight = ground1.getImage().getHeight();

        addObject(ground1, getWidth() / 2, groundY + (groundHeight / 2));
        addObject(ground2, getWidth() + (getWidth() / 2), groundY + (groundHeight / 2));

        // ✅ Dino
        dino = new Dino();
        addObject(dino, 100, groundY - (dino.getImage().getHeight() / 2));

        // ✅ Scoreboard
        scoreBoard = new ScoreBoard();
        addObject(scoreBoard, 70, 30);

        // ✅ Difficulty Buttons (NEW Menu Display)
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        int spacing = 100; // Space between buttons

        addObject(new EasyButton(), centerX - spacing, centerY);
        addObject(new MediumButton(), centerX, centerY);
        addObject(new HardButton(), centerX + spacing, centerY);
        
        // You can remove the StartText class entirely since the buttons handle the start.
        // If you kept the StartText class, it should be removed here:
        removeObjects(getObjects(StartText.class));
    }

    public void act() {
        // Handle restart input after game over
        if (gameOver) {
            if (Greenfoot.isKeyDown("r")) {
                Greenfoot.setWorld(new GameWorld());
            }
            return;
        }

        // Game not started yet. Just return and wait for a button click. (SIMPLIFIED)
        if (!running) {
            return;
        }

        // ------------------------------------------------------------------
        // --- GAME IS RUNNING BELOW HERE ---
        // ------------------------------------------------------------------

        // ✅ Score update
        scoreBoard.addScore(1);

        // ✅ Difficulty scaling (applied every 500 points)
        int s = scoreBoard.getScore();
        if (s % 500 == 0 && s != 0) {
            if (gameSpeed < 15) { // Cap max speed
                gameSpeed++;
            }
            if (spawnInterval > 40) { // Cap min spawn interval
                spawnInterval -= 5;
            }
        }

        // ✅ Obstacle spawning
        spawnTimer++;
        if (spawnTimer >= spawnInterval) {
            spawnTimer = 0;
            // The Obstacle constructor must accept the gameSpeed variable
            Obstacle o = new Obstacle(gameSpeed); 
            int y = groundY - (o.getImage().getHeight() / 2) + 4;
            addObject(o, getWidth() + o.getImage().getWidth() / 2, y);
        }

        // ✅ Ground scrolling
        ground1.scroll(gameSpeed);
        ground2.scroll(gameSpeed);

        // ✅ Reposition ground
        if (ground1.getX() + ground1.getImage().getWidth() / 2 <= 0)
            ground1.setLocation(ground2.getX() + ground2.getImage().getWidth(), ground1.getY());
        if (ground2.getX() + ground2.getImage().getWidth() / 2 <= 0)
            ground2.setLocation(ground1.getX() + ground1.getImage().getWidth(), ground2.getY());

        // ✅ Collision check
        if (dino.isHit()) {
            showGameOver();
        }
    }

    private void showGameOver() {
        addObject(new GameOver(scoreBoard.getScore()), getWidth() / 2, getHeight() / 2);
        gameOver = true;
    }
}