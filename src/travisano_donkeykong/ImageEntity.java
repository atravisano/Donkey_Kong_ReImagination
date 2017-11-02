/***************************************************
Purpose: framework for images
    -handles loading images
    -creates bounding box for image
Author: Anthony Travisano
Date: 4/18/17
***************************************************/
package travisano_donkeykong;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.net.URL;


public class ImageEntity extends Sprite {
    //variables for the image
    protected Image image;
    protected String filename;
    
    
    //default constructor
    public ImageEntity()
    {
        super();
    }
    
    //overloaded constructor
     public ImageEntity(int pLocx, int pLocy, int pVelX, int pVelY, String pFileName)
    {
    	super();
    	setLocX(pLocx);
    	setLocY(pLocy);
        setVelX(pVelX);
        setVelY(pVelY);
    	filename = pFileName;
    	loadImage();
        
    }

    /*
     loads image
     */
    public void loadImage() {
    	URL url = null;
    	Toolkit tk = Toolkit.getDefaultToolkit();
        url = this.getClass().getResource(filename);
        image = tk.getImage(url);
    }    
    
    @Override
    /*
    draws image on screen with specified coords
    */public void draw(Graphics g) {
      Graphics2D g2d = (Graphics2D)g;
    	
		g2d.drawImage(image, getLocX(), getLocY(), null);
    }
    
    /*
    gives object new image by string
    */
    public void newImage(String filename)
    {
        this.filename = filename;
        loadImage();
    }
    
    //**************************************************************
    //***************Accesors and Mutators**************************
    
    //returns image
    public Image getImage() { return image; }
    
    //sets image
    public void setImage(Image image) {
        this.image = image;    
    }
    
    /*
    creates bounding box for object
    */
    public Rectangle getBoundingBox()
    {
        Rectangle r;
        r = new Rectangle((int)getLocX(), (int)getLocY(), image.getWidth(null), image.getHeight(null));
        return r;
    }
    
    /*
    returns image's width
    */
    public int getImageWidth()
    {
        return image.getWidth(null);
    }
    
    /*
    returns image's height
    */
    public int getImageHeight()
    {
        return image.getHeight(null);
    }
}
