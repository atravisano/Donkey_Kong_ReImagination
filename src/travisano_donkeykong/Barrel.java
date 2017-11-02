/*************************************************************************
Purpose: handles all behaviors of Barrel
    -the first barrel moves down screen until it reaches first platform
    -barrels continuously move left or right
    -barrel changes direction when it hits edge of window  
Author: Anthony Travisano
Date: 4/29/17
*************************************************************************/
package travisano_donkeykong;

public class Barrel extends ImageEntity{
    
    //determining which way the barrel is rolling
    private boolean rollLeft;
    private boolean rollRight;
    //determines if barrel is falling
    private boolean falling;
    
    //constructor
    public Barrel(int x, int y, int velX, int velY, String filename)
    {
        super(x, y, velX, velY, filename);
        
        rollLeft = false;
        rollRight = false;
        falling = true;
    }
    
    /*
    firstBarrel(blueBarrel) moves down the screen through platforms
        until it reaches bottom platform
    */
    public void firstBarrel()
    {
        if(isFalling())
        {
           super.moveDown();
        }
       else
        {
          super.moveLeft();
        }
        
    }
    
    /*
    barrel bounces off sides of panel
    */
    public void checkBounce()
    {
       //subtracting image width so image does not go offscreen
       if(super.getLocX()>= (GameWindow.WINDOW_WIDTH-getImageWidth()))
       {
           //when the barrel hits the right side of the window it will start
           //moving left
            setRollLeft(true);
            setRollRight(false);
       }
       if(super.getLocX() <= 0)
       {
           //when it hits the left side of window it moves right
            setRollRight(true);
            setRollLeft(false);
       }
    }
    /*
    moves barrel depending on its facing direction
    */
    public void moveBarrel()
    {
        if(isRollRight())
        {
            super.moveRight();
        }
        else if(isRollLeft())
        {
            super.moveLeft();
        }
        else
        {
            //barrels spawn moving right
            super.moveRight();
        }
    }
    
    /*
    Purpose: makes barrel fall if its not touching a platform
    */
    public void checkFalling()
    {
       if(isFalling())
       {
           super.moveDown();
       }
    }

    //*************************************************
    //************* Accessor and Mutators *************
    
    /**
     * @return the rollLeft
     */
    public boolean isRollLeft() {
        return rollLeft;
    }

    /**
     * @param rollLeft the rollLeft to set
     */
    public void setRollLeft(boolean rollLeft) {
        this.rollLeft = rollLeft;
    }

    /**
     * @return the rollRight
     */
    public boolean isRollRight() {
        return rollRight;
    }

    /**
     * @param rollRight the rollRight to set
     */
    public void setRollRight(boolean rollRight) {
        this.rollRight = rollRight;
    }

    /**
     * @return the falling
     */
    public boolean isFalling() {
        return falling;
    }

    /**
     * @param falling the falling to set
     */
    public void setFalling(boolean falling) {
        this.falling = falling;
    }
}
