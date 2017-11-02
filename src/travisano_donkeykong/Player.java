/****************************************************
Purpose: handles all of player's behaviors
    -player is able to jump for a fixed amount
    -player can climb ladders
    -player has certain amount of lives
Author: Anthony Travisano
Date: 4/18/17
****************************************************/
package travisano_donkeykong;

public class Player extends ImageEntity {
    //amount of player lives
    private int lives;
    //whether or not hammer was picked up
    private boolean hammerTime;
    //direction player is facing
    private boolean faceLeft;
    private boolean faceRight;
    //determines if player is climbing(going up)
    private boolean climbing;
    //determines if player is near ladder
    private boolean nearLadder;
    //current count in climbing sequence
    private int climbCount;
    //how long player climbs
    private final int CLIMB_MAX = 19;
    //current count of hammer time
    private int hammerCount;
    //max time player can be in hammer time
    private final int HAMMER_MAX = 200;
    //height of player's jump
    private final int GRAVITY = 1;
    //determines state of jump
    private boolean jumping;
    //how long player will jump
    private int jumpCount;
    //height of player jump
    private static final int JUMP_MAX = 7;
    //saves velocity before jump
    private int origVelY;
    
    
    //default constructor
    public Player()
    {
        super();
        lives = 3;
        hammerTime = false;
        climbing = false;
        climbCount = 0;
        nearLadder = false;
        hammerCount = 0;
    }
    
    //overloaded constructor
    public Player(int x, int y, int velX, int velY, String filename)
    {
        super(x, y, velX, velY, filename);
        origVelY = velY;
        lives = 3;
        hammerTime = false;
        faceLeft = false;
        faceRight = false;
        jumping  = false;
        jumpCount = 0;
        climbing = false;
        climbCount = 0;
        nearLadder = false;
        hammerCount = 0;
        
    }
    
    /*
    moves left like parent and updates which way character is looking
    */
    @Override
    public void moveLeft()
    {
       super.moveLeft();
       faceLeft=true;
       faceRight=false;
    }
    
    /*
    moves right like parent and updates which way character is looking
    */
    @Override
    public void moveRight()
    {
       super.moveRight();
       faceLeft=false;
       faceRight=true;
    }
    
    /*
    Initiates jump sequence
    */
    public void jump()
    {
        //prevents chance of irregular jump height
        setVelY(origVelY);
        setJumping(true);
        jumpCount = 0;
    }
    
    /*
    creates the jumping animation frame by frame
        player will jump right when facing right
        player will jump left when facing left
        player will jump up if standing
        bounding box moves with character
    */
    public void checkJumpState()
    {
        if(isJumping())
        {
            //ascending phase
            if(jumpCount < JUMP_MAX)
            {
                //****move right
                if(isFaceRight())
                {
                    //change y velocity for jump
                    super.setVelY(super.getVelY() + GRAVITY);
                    super.moveRight();
                }
                //****move left
                else if(isFaceLeft())
                {
                    //change y velocity for jump
                    super.setVelY(super.getVelY() + GRAVITY);
                    super.moveLeft();
                }
                
                super.moveUp();
            }
            //descending phase
            else
            {
                //****move right
                if(isFaceRight())
                {
                    //change y velocity for jump
                    super.setVelY(super.getVelY() - GRAVITY);
                    //making player descend
                    super.moveRight();
                }
                //****move left
                else if(isFaceLeft())
                {
                    //change y velocity for jump
                    super.setVelY(super.getVelY() - GRAVITY);
                    //making player descend
                    super.moveLeft();
                }
                super.moveDown();
            }
        jumpCount++;
        
        if(jumpCount >= JUMP_MAX*2)
        {
            if(isFaceLeft() || isFaceRight())
            {
                //final amount that puts player back on ground
                super.setLocY(super.getLocY() + JUMP_MAX);
                //returns velocity to original
                //used to reduce chance of bugs if player was jumping during
                //  level change
                super.setVelY(origVelY);
            }
                
                setJumping(false);
                jumpCount = 0;
        }
            
        }
    }
    
    /*
    initiates climb sequence
    */
    public void climb()
    {
        setVelY(origVelY);
        setClimbing(true);
        climbCount = 0;
    }
    
    /*
    moves up ladder for a fixed number
    */
    public void checkClimbing()
    {
        //You must be near a ladder and press up to climb
        if(isClimbing() && isNearLadder() && !isJumping())
        {
          
            if(climbCount<=CLIMB_MAX)
            {
                super.moveUp();
                climbCount++;
            }
            else
            {
                setClimbing(false);
                setNearLadder(false);
                climbCount = 0;
            }
        }
        else
        {
            setClimbing(false);
        }
    }
    
    /*
    makes sure player does not go offscreen
    */
    public void checkWalls()
    {
        //checks right 
        if(super.getLocX() >= GameWindow.WINDOW_WIDTH-super.getImageWidth())
        {
           super.moveLeft();
        }
        //checks left
        else if(super.getLocX() <= 0)
        {
            super.moveRight();
        }
    }
    
    public void hammerTime()
    {
        setHammerTime(true);
        hammerCount = 0;
    }
    
    /*
    calcutles how long player is in hammer time
    */
    public void checkHammerTime()
    {
        if(isHammerTime())
        {
            if(hammerCount<=HAMMER_MAX)
           {
               hammerCount++;
           }
            else
            {
                setHammerTime(false);
                hammerCount = 0;
            }
        }
    }
    
    /*
    Makes player lose 1 life
    */
    public void loseLife()
    {
        setLives(getLives()-1);
    }
    
    //********************************************************************
    //**************    Acessors and Mutators   ************************** 
    
    /**
     * @return the lives
     */
    public int getLives() {
        return lives;
    }

    /**
     * @param lives the lives to set
     */
    public void setLives(int lives) {
        this.lives = lives;
    }

    /**
     * @return the hammerTime
     */
    public boolean isHammerTime() {
        return hammerTime;
    }

    /**
     * @param hammerTime the hammerTime to set
     */
    public void setHammerTime(boolean hammerTime) {
        this.hammerTime = hammerTime;
    }

    /**
     * @return the faceLeft
     */
    public boolean isFaceLeft() {
        return faceLeft;
    }

    /**
     * @param faceLeft the faceLeft to set
     */
    public void setFaceLeft(boolean faceLeft) {
        this.faceLeft = faceLeft;
    }

    /**
     * @return the faceRight
     */
    public boolean isFaceRight() {
        return faceRight;
    }

    /**
     * @param faceRight the faceRight to set
     */
    public void setFaceRight(boolean faceRight) {
        this.faceRight = faceRight;
    }

    /**
     * @return the jumping
     */
    public boolean isJumping() {
        return jumping;
    }

    /**
     * @param jumping the jumping to set
     */
    public void setJumping(boolean jumping) {
        this.jumping = jumping;
    }

    /**
     * @return the climbing
     */
    public boolean isClimbing() {
        return climbing;
    }

    /**
     * @param climbing the climbing to set
     */
    public void setClimbing(boolean climbing) {
        this.climbing = climbing;
    }

    /**
     * @return the nearLadder
     */
    public boolean isNearLadder() {
        return nearLadder;
    }

    /**
     * @param nearLadder the nearLadder to set
     */
    public void setNearLadder(boolean nearLadder) {
        this.nearLadder = nearLadder;
    }

   
    
}
