/***************************************************
Purpose: The framework for the objects
Author: Anthony Travisano
Date: 4/18/17
*****************************************************/
package travisano_donkeykong;

import java.awt.Graphics;

public abstract class Sprite {
    //x,y positions for object
    private int locX;
    private int locY;
    //x,y object's velocity
    private int velX;
    private int velY;
    //boolean to check whether object is visble on screen
    private boolean visible;
    
    //default consructor
    public Sprite()
    {
        this(0, 0, 0, 0, true);
    }
    
    //overloaded constructor
    public Sprite(int pX, int pY, int pVelX, int pVelY, boolean pVisible)
    {
        locX = pX;
        locY = pY;
        velX = pVelX;
        velY = pVelY;
        visible = pVisible;
    }
    
    
    //draw method must be implemented by child
    public abstract void draw(Graphics g);
    
    
    //moves object left on screen
    public void moveLeft()
    {
        setLocX(getLocX()-getVelX());
    }
    
    //moves object right on screen
    public void moveRight()
    {
        setLocX(getLocX()+getVelX());
    }
    
    //moves object up screen
    public void moveUp()
    {
        setLocY(getLocY()-getVelY());
    }
    
    //moves object down screen
    public void moveDown()
    {
        setLocY(getLocY()+getVelY());
    }

    //************************************************************************
    //************************** Accesors and Mutators ***********************
    
    /**
     * @return the locX
     */
    public int getLocX() {
        return locX;
    }

    /**
     * @param locX the locX to set
     */
    public void setLocX(int locX) {
        this.locX = locX;
    }

    /**
     * @return the locY
     */
    public int getLocY() {
        return locY;
    }

    /**
     * @param locY the locY to set
     */
    public void setLocY(int locY) {
        this.locY = locY;
    }

    /**
     * @return the velX
     */
    public int getVelX() {
        return velX;
    }

    /**
     * @param velX the velX to set
     */
    public void setVelX(int velX) {
        this.velX = velX;
    }

    /**
     * @return the velY
     */
    public int getVelY() {
        return velY;
    }

    /**
     * @param velY the velY to set
     */
    public void setVelY(int velY) {
        this.velY = velY;
    }

    /**
     * @return the visible
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     * @param visible the visible to set
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
    }   
    
}
