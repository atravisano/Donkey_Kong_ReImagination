/****************************************************
Purpose: displays and runs the game
    -creates all objects of game
        -player, fireball, barrels, ladders, platforms, hollow platforms, oil drum
    -checks collisions between objects
    -game goes to level 9
    -updates sprites
    -update labels with current information
    -able to restart game
Author: Anthony Travisano
Date: 4/30/17
*****************************************************/
package travisano_donkeykong;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class GamePanel extends JPanel implements Runnable{
    //*************** MOVING OBJECTS *********************
    //represents the charcter you will be playing as
    private Player player;
    //blueBarrel represents the first barrel that drops stright down and creates the fireball
    private Barrel blueBarrel;
    //fireball is the enemy that cheases you
    private FireBall fireBall;  
    //*****************************************************
    //************** LEVEL OBJECTS ************************
    //represents the oil drum on screen
    private ImageEntity oilDrum;
    //ladders
    private ImageEntity ladder1;
    private ImageEntity ladder2;
    private ImageEntity ladder3;
    private ImageEntity ladder4;
    private ImageEntity ladder5;
    //platforms
    private ImageEntity level1;
    private ImageEntity level2;
    private ImageEntity level3;
    private ImageEntity level4;
    private ImageEntity level5;
    private ImageEntity level6;
    //hollow platforms
    private ImageEntity hLevel2;
    private ImageEntity hLevel3;
    private ImageEntity hLevel4;
    private ImageEntity hLevel5;
    private ImageEntity hLevel6;
    //hammer pickup
    private ImageEntity hammer;
    //images on top platform
    private ImageEntity donkeyKong;
    private ImageEntity barrelStack;
    private ImageEntity key;
    
    //*****************************************************
    //arraylist that manages barrels on screen
    private List<Barrel>barrelManager;
    //listener for keyboard presses
    private KeyboardGameListener listener;
    //keeps track of how many iterations the game's while loop has executed
    private int gameCounter;
    //number of game loop iterations before new barrel spawns
    private int spawnRate;
    //current game level you are playing on
    private int gameLevel;
    //boolean controls when game is being played
    private boolean gameRunning;
    //boolean controls when the game starts
    private boolean startScreen;
    //thread runs the entire game
    private Thread game;
    //instance of component panel to manipulate labels
    private ComponentPanel compPanel;
    //instance of gameSound to add sound to movements, collisions
    private GameSound gameSound;
    //variable to hold score
    private int score;
    //variable to determine if cheat is activated
    private boolean cheat;
    
    public GamePanel(ComponentPanel compPanel, GameSound gameSound)
    {
        //refs
        this.compPanel = compPanel;
        this.gameSound = gameSound;
        //********** MOVING OBJECTS ************************************
        //test mario orig size: 12x16
        player = new Player(200, 750, 8, 6, "rightMario.png");
        //first barrel
        blueBarrel = new Barrel(200, 150, 8, 18,"blueBarrelFalling.png");
        //fireball is not created until blueBarrel reaches oil drum
        fireBall = null;
        //**************************************************************
        //************ LEVEL OBJECTS ***********************************
        //images on top platform
        donkeyKong = new ImageEntity(50, 70, 0, 0, "donkeyKong.png");
        barrelStack = new ImageEntity(200, 80, 0, 0, "barrelStack.png");
        key = new ImageEntity(300, 100, 0, 0, "key.png");
        //oil drum
        oilDrum = new ImageEntity(0, 740, 0, 0, "Oil_drum.png");
        //ladder orig size: 64x120
        //ladders are located towards the end of platforms
        ladder1 = new ImageEntity(440, 660, 0, 0, "ladder.png");
        //each ladder's y is subtracted by the ladder's height, 120
        ladder2 = new ImageEntity(100, 540, 0, 0, "ladder.png");
        ladder3 = new ImageEntity(440, 420, 0, 0, "ladder.png");
        ladder4 = new ImageEntity(100, 300, 0, 0, "ladder.png");
        ladder5 = new ImageEntity(440, 180, 0, 0, "ladder.png");
        //platform orig size: 324x18
        //platforms are the objects that the player can walk on
        level1 = new ImageEntity(0, 780, 0, 0, "bottomPlatform.png");
        //each platform's y is subtracted by ladder's height, 120
        level2 = new ImageEntity(0, 660, 0, 0, "platform.png");
        level3 = new ImageEntity(100, 540, 0, 0, "platform.png");
        level4 = new ImageEntity(0, 420, 0, 0, "platform.png");
        level5 = new ImageEntity(100, 300, 0, 0, "platform.png");
        level6 = new ImageEntity(0, 180, 0, 0, "platform.png");
        //hollow platforms
        //x,y position is determined by normal platform's width and height
        hLevel2 = new ImageEntity(490, 660, 0, 0, "hollowPlatform.png");
        hLevel3 = new ImageEntity(0, 540, 0, 0, "hollowPlatform.png");
        hLevel4 = new ImageEntity(490, 420, 0, 0, "hollowPlatform.png");
        hLevel5 = new ImageEntity(0, 300, 0, 0, "hollowPlatform.png");
        hLevel6 = new ImageEntity(490, 180, 0, 0, "hollowPlatform.png");
        //hammer pickup
        hammer = new ImageEntity(530, 630, 0, 0, "hammer.png");
        //**************************************************************
        //there will be a max of 10 barrels on screen for first level
        barrelManager = new ArrayList<>(10);
        //intial values
        spawnRate = 100;
        gameCounter = 0;
        score = 0;
        gameLevel = 1;
        //set up keyboard listener
        listener = new KeyboardGameListener();
        this.addKeyListener(listener);
        this.setFocusable(true);
        //game does not start until user presses enter
        startScreen = true;
        gameRunning = false;
        cheat = false;
        //Creates thread for game
        game = new Thread(this);
    }
    /*
    the main function of the panel
        -checks for collisions
        -updates sprites
    */
    @Override
    public void run()
    {
        while(isGameRunning())
        {
            try
            {
               Thread.sleep(50); 
            }
            catch (InterruptedException e)
            {
               e.printStackTrace();
            }
            //player's methods
            player.checkJumpState();
            player.checkClimbing();
            player.checkWalls();
            player.checkHammerTime();
            //collision checking
            checkCollisions();
            checkNextLevel();
            //first barrel will move down when displayed on screen
            if(blueBarrel != null)
            {
                blueBarrel.firstBarrel();
            }
            //fireball will chase player when displayed on screen
            if(fireBall != null)
            {
                fireBall.hunt(player.getLocX());
                fireBall.checkLadder();
            }
            //update all barrels
            for(Barrel b : barrelManager)
            {
                b.checkBounce();
                b.moveBarrel();
                b.checkFalling();
            }
            //updating current iteration
            gameCounter++;
            //maintaining barrels on screen
            spawnBarrel();
            removeBarrel();
            //update the labels
            updateScoreLabel();
            updateLivesLabel();
            updateCheatLabel();
            //update object's image
            updateSprite();
            //update panel
            repaint();
        }
        JOptionPane.showMessageDialog(null, "Game Over!\n Your Final Score was: " + score,
                        "Game Over", JOptionPane.INFORMATION_MESSAGE);
    }
    
    /*
    draws all objects to panel
    */
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        this.setBackground(Color.BLACK);
        //draws all in game objects
        if(isGameRunning())
        {
            //displays current level
            g.setColor(Color.WHITE);
            g.drawString("LEVEL : "+ gameLevel, 500, 25);
            donkeyKong.draw(g);
            barrelStack.draw(g);
            key.draw(g);
            //draws platforms 
            level1.draw(g);
            level2.draw(g);
            level3.draw(g);
            level4.draw(g);
            level5.draw(g);
            level6.draw(g);
            //draws hollow platforms
            hLevel2.draw(g);
            hLevel2.draw(g);
            hLevel3.draw(g);
            hLevel4.draw(g);
            hLevel5.draw(g);
            hLevel6.draw(g);
            //draws oil drum
            oilDrum.draw(g);
            //draws ladders
            ladder1.draw(g);
            ladder2.draw(g);
            ladder3.draw(g);
            ladder4.draw(g);
            ladder5.draw(g);
            //draws player
            player.draw(g);
            //draws first barrel
            if(blueBarrel != null)
            {
              blueBarrel.draw(g);
            }
            //drawing all barrels that are visible
            for(Barrel b: barrelManager)
            {
                if(b.isVisible())
                {
                  b.draw(g);
                }
            }
            //draws fireball
            if(fireBall != null)
            {
                fireBall.draw(g);
            }
            //draws hammer pickup
            if(hammer.isVisible())
            {
                hammer.draw(g);
            }
        }
        
    }
    
    /*
    allows the game to start
    */
    public void beginGame()
    {
        setGameRunning(true);
        game.start();
    }
    
    /*
    changes the label to current score
    */
    public void updateScoreLabel()
    {
        compPanel.lblScore.setText("Score: " + score);
    }
    
    /*
    changes the label to current amount of lives
    */
    public void updateLivesLabel()
    {
        compPanel.lblLives.setText("Lives: " + player.getLives());
    }
    
    /*
    changes the cheat label
    */
    public void updateCheatLabel()
    {
        if(isCheat())
        {
            compPanel.lblCheat.setText("Cheat: ON");
        }
        else
        {
            compPanel.lblCheat.setText("Cheat: OFF");
        }
    }
    
    /*
    changes images to match the direction its facing
    */
    public void updateSprite()
    {   
        if(fireBall != null && fireBall.isFaceRight())
        {
            fireBall.newImage("leftFireBall.png");
        }
        else if(fireBall != null)
        {
            fireBall.newImage("rightFireBall.png");
        }
        
        for(Barrel b : barrelManager)
        {
            if(b.isRollLeft())
            {
                b.newImage("DKBarrelLeft.png");
            }
            else
            {
                b.newImage("DKBarrelRight.png");
            }
        }
        
        /*player's image changes in keypressed event and bluebarrel changes
        in collision method when it hits the bottom platform.*/
        
    }
    
    /*
    checks if player is at goal
    */
    public void checkNextLevel()
    {
        if(gameLevel != 10)
        {    
            if(player.getBoundingBox().intersects(key.getBoundingBox())
                    || (player.getLocX() <= key.getLocX() && player.getLocY() <= 154))
            {
                //when player reaches touches the key they go to next Level
                //they can not reach past key
                resetObjects();
                nextLevel();
            }
        }
        else
        {
            //game only goes to level 9
            setGameRunning(false);
        }
    }
    
    /*
    increases difficulty
    */
    public void nextLevel()
    {
        //player earns score for succesfully completing level
        score += 10000;
        //increases spawn rate of barrels
        spawnRate -= 10;
        //display to use they are on new level
        gameLevel++;
    }
    
    /*
    checks when objects intersect
    */
    public void checkCollisions()
    {
       //seperatd into methods to increase code readability
       checkPlayerCollisions();
       checkBarrelCollisions();
       checkBlueBarrelCollisions();
       checkFireBallCollisions();
    }
    
    /*
    checks player collisions with ladders and hammer pickup.
    checks player collisions with barrels and fireball when in hammer mode
    */
    public void checkPlayerCollisions()
    {
        //player can climb if it is near a ladder
        if(player.getBoundingBox().intersects(ladder1.getBoundingBox())
                || player.getBoundingBox().intersects(ladder2.getBoundingBox())
                || player.getBoundingBox().intersects(ladder3.getBoundingBox())
                || player.getBoundingBox().intersects(ladder4.getBoundingBox())
                || player.getBoundingBox().intersects(ladder5.getBoundingBox()))
        {
            player.setNearLadder(true);
        }
        else
        {
            player.setNearLadder(false);
        }
        //player goes into hammer mode when colliding with hammer object
        if(player.getBoundingBox().intersects(hammer.getBoundingBox()))
        {
            hammer.setVisible(false);
            player.hammerTime();
            
        }
         
        //when player is in hammerTime it can destroy moving objects by collision
        if(player.isHammerTime())
        {
            gameSound.playHammerTime();
            if(fireBall != null && player.getBoundingBox().intersects(fireBall.getBoundingBox()))
            {
                //makes fireBall respawn at oil drum
                fireBall = spawnNewFireBall();
                //earn 500 points for destroying fireBall
                score += 500;
            }
            //checking all barrels for collision
            for(Barrel b : barrelManager)
            {
                if(player.getBoundingBox().intersects(b.getBoundingBox()))
                {
                    //barrel diappears from screen
                    b.setVisible(false);
                    //earn 100 points for destroying barrels
                    score += 100;
                }
            }
        }
        else
        {
            gameSound.stopHammerTime();
        }
    }
    
    /*
    check barrel collisions with platforms, oil drum, and player
    */
    public void checkBarrelCollisions()
    {
         //each barrel checks for platforms
        for(Barrel b : barrelManager)
        {
            if(b.getBoundingBox().intersects(level1.getBoundingBox())
                    || b.getBoundingBox().intersects(level2.getBoundingBox())
                    || b.getBoundingBox().intersects(level3.getBoundingBox())
                    || b.getBoundingBox().intersects(level4.getBoundingBox())
                    || b.getBoundingBox().intersects(level5.getBoundingBox())
                    || b.getBoundingBox().intersects(level6.getBoundingBox()))
            {
                b.setFalling(false);
            }
            else
            {
                //when barrel is not on a platform, the barrel is falling
                b.setFalling(true);
            }
            //barrel disappears after colliding with oilDrum
            if(b.getBoundingBox().intersects(oilDrum.getBoundingBox()))
            {
                b.setVisible(false);
            }
            //player loses life when barrel collides with player
            //As long as player does not have hammer pickup or have cheat activated
            if(b.getBoundingBox().intersects(player.getBoundingBox()) && !player.isHammerTime() && !isCheat())
            {
                player.loseLife();
                restartGame();
                //there is no point in going though rest of list if no elements are in it
                break;
            }
        } 
    }
    
    /*
    check blue barrel collisions with bottom platform, player and oil drum
    */
    public void checkBlueBarrelCollisions()
    {
        if(blueBarrel != null)
        {
            //blueBarrel no longer moves down when touching bottom platform
            if(blueBarrel.getBoundingBox().intersects(level1.getBoundingBox()))
            {
                blueBarrel.setFalling(false);
                blueBarrel.newImage("blueBarrelRolling.png");
            }
            //player loses life and game is reset if barrel collides and cheat is not on
            if(blueBarrel.getBoundingBox().intersects(player.getBoundingBox()) && !isCheat())
            {
                player.loseLife();
                
                restartGame();
            }
            //blueBarrel disappears when colliding with oilDrum
            if(blueBarrel.getBoundingBox().intersects(oilDrum.getBoundingBox()))
            {
                blueBarrel = null;
                //creates fireball when bluebarrel disappears
                fireBall = spawnNewFireBall();
            }
        }
    }
    
    /*
    checks fireball collisions with ladder and fireball
    */
    public void checkFireBallCollisions()
    {
        if(fireBall != null)
        {
            //fireball will climb ladder if close to it
            if(fireBall.getBoundingBox().intersects(ladder1.getBoundingBox())
                    || fireBall.getBoundingBox().intersects(ladder2.getBoundingBox())
                    || fireBall.getBoundingBox().intersects(ladder3.getBoundingBox())
                    || fireBall.getBoundingBox().intersects(ladder4.getBoundingBox())
                    || fireBall.getBoundingBox().intersects(ladder5.getBoundingBox()))
            {
               fireBall.setClimbing(true);
            }
            else
            {
                fireBall.setClimbing(false);
            }
            //player loses life when fireball collides with player and cheat is not on
            if(fireBall.getBoundingBox().intersects(player.getBoundingBox()) && !isCheat())
            {
                player.loseLife();
                restartGame();
            }
        }
    }
    
    /*
    creates new fireball
    */
    public FireBall spawnNewFireBall()
    {
        return new FireBall(oilDrum.getLocX(), 750, 10, 4, "rightFireball.png");
    }
    
    /*
    creates new blue barrel
    */
    public Barrel spawnNewBlueBarrel()
    {
        return new Barrel(200, 150, 8, 18, "blueBarrelFalling.png");
    }
    
    /*
    creates new barrel every ___ iterations
    */
    public void spawnBarrel()
    {
        if(gameCounter % spawnRate == 0)
        {
            barrelManager.add(new Barrel(200, 60, 8, 6, "DKbarrelRight.png"));
        }
    }
    
    /*
    removes barrel from arraylist
    */
    public void removeBarrel()
    {
        //uses iterator to remove objects while in a loop
        //using .remove() in a for loop going through arrayList gives error
        Iterator<Barrel>it = barrelManager.iterator();
        //.hasNext() returns true if there is another element in List
        while(it.hasNext())
        {
            //returns next element in iteration
            Barrel b = it.next();
            if(!b.isVisible())
            {
                //removes element from array list if it is not visible
                it.remove();
            }
        }
    }
    /*
    removes all barrels from list
    */
    public void removeAllBarrels()
    {
        //uses iterator to remove objects while in a loop
        //using .clear() while in a loop gives error
        Iterator<Barrel>it = barrelManager.iterator();
        //.hasNext() returns true if there is another element in List
        while(it.hasNext())
        {
            //returns next element in iteration
            Barrel b = it.next();
            //removes element from array list if it is not visible
            it.remove();
        }
    }
   
    /*
    resets game to initial position unless player has zero lives
        then game is over
    */
    public void restartGame()
    {
        if(player.getLives() == 0)
        {
            gameSound.playGameOver();
            setGameRunning(false);
        }
        else
        {
            //reset moving objects to original position
            resetObjects();
        }
    }
    
    /*
    puts all objects in original positions
    */
    public void resetObjects()
    {
        //reset player
        player.newImage("rightMario.png");
        player.setJumping(false);
        player.setClimbing(false);
        player.setHammerTime(false);
        player.setLocX(200);
        player.setLocY(750);
        //reset fireball
        fireBall = null;
        //clearing all barrels from screen
        removeAllBarrels();
        //reset first barrrel
        blueBarrel = spawnNewBlueBarrel();
        //allow hammer to be on screen again
        hammer.setVisible(true);
    }
    
    //private class to handle keyboard presses
    private class KeyboardGameListener implements KeyListener {
        
        @Override
        public void keyPressed(KeyEvent ke) {
            if (ke.getKeyCode()==(KeyEvent.VK_LEFT))
            {
                if(!player.isClimbing() && !player.isJumping())
                {
                    player.newImage("leftMario.png");
                    player.moveLeft();
                }
            }	
            if (ke.getKeyCode()==(KeyEvent.VK_RIGHT))
            {
                if(!player.isClimbing() && !player.isJumping())
                {
                    player.newImage("rightMario.png");
                    player.moveRight();
                }
            }
            if (ke.getKeyCode()==(KeyEvent.VK_UP))
            {
                if(!player.isClimbing() && !player.isJumping())
                {
                player.climb();
                }
            }
            if (ke.getKeyCode()==(KeyEvent.VK_DOWN))
            {
                //player.moveDown();
            }
            if(ke.getKeyCode()==(KeyEvent.VK_SPACE))
            {
                if(!player.isJumping() && !player.isClimbing())
                {
                    //player will jump if it is not already jumping
                    player.jump();
                }
            }
            if(ke.getKeyCode() == (KeyEvent.VK_J))
            {
                //changes cheat every key press
                setCheat(!isCheat());
            }
            
            repaint();
        
        }
        
        
        @Override
        public void keyTyped(KeyEvent ke) {
            //todo code
        }

        @Override
        public void keyReleased(KeyEvent ke) {
            //todo code
        }
        
    }

    //************************************************************************
    //********************* Accessors and Mutators ***************************
    /**
     * @return the gameRunning
     */
    public boolean isGameRunning() {
        return gameRunning;
    }

    /**
     * @param gameRunning the gameRunning to set
     */
    public void setGameRunning(boolean gameRunning) {
        this.gameRunning = gameRunning;
    }

    /**
     * @return the startScreen
     */
    public boolean isStartScreen() {
        return startScreen;
    }

    /**
     * @param startScreen the startScreen to set
     */
    public void setStartScreen(boolean startScreen) {
        this.startScreen = startScreen;
    }

    /**
     * @return the cheat
     */
    public boolean isCheat() {
        return cheat;
    }

    /**
     * @param cheat the cheat to set
     */
    public void setCheat(boolean cheat) {
        this.cheat = cheat;
    }
}
