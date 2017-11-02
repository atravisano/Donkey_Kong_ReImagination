/****************************************************
Purpose: handles the enemy FireBall's behaviors
   -fireBall follows player
   -fireBall can climb ladders if near
Author: Anthony Travisano
Date: 4/29/17
*****************************************************/
package travisano_donkeykong;

public class FireBall extends ImageEntity {
    //variables used for climbing method
    private boolean climbing;
    private int climbCount;
    private final int CLIMB_MAX = 19;
    //variables deciding which way enemy is facing
    private boolean faceLeft;
    private boolean faceRight;
    
    public FireBall(int x, int y, int velX, int velY, String filename)
    {
        super(x, y, velX, velY, filename);
        climbing = false;
        climbCount = 0;
        faceLeft = false;
        faceRight = true;
    }
   
   /*
    fireBall will follow player using their x position
    */
   public void hunt(int playerLocX)
   {
       if(!isClimbing())
       {
            if(super.getLocX() <= playerLocX)
            {
                super.moveRight();
                setFaceRight(true);
                setFaceLeft(false);
            }
            else
            {
                super.moveLeft();
                setFaceLeft(true);
                setFaceRight(false);
            }
       }   
   }
   
   /*
   fireBall climbs ladder for a fixed amount if near a ladder
   */
   public void checkLadder()
   {
       if(isClimbing())
       {
           if(climbCount<=CLIMB_MAX)
            {
                super.moveUp();
                climbCount++;
            }
            else
            {
                setClimbing(false);
                climbCount = 0;
            }
       }
   }
   
   //****************************************************
   //************* Accesors and Mutators ****************
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
    
    
}
