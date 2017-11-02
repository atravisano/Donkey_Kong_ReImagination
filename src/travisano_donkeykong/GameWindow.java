/***************************************************************************
Purpose: Displays the gamePanel and component panel with buttons and labels
    to user
Author: Anthony Travisano
Date: 4/30/17
***************************************************************************/
package travisano_donkeykong;

import java.awt.BorderLayout;
import javax.swing.JFrame;

public class GameWindow extends JFrame {
    //window's width and height
    public static final int WINDOW_WIDTH = 600;
    public static final int WINDOW_HEIGHT = 875;
    //adds sound to game
    GameSound gameSound;
    //instance of game
    GamePanel gamePanel;
    //instance of panel holding buttons and labels
    ComponentPanel compPanel;
    
    //constructor
    public GameWindow()
    {
        //Window's title
        this.setTitle("Donkey Kong");
        //setting window size
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        //set close operation
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //intitializ game sound
        gameSound = new GameSound();
        //initialize game panel
        gamePanel = new GamePanel(compPanel, gameSound);
        //initialize component panel
        compPanel = new ComponentPanel(gamePanel, gameSound);
        //set Layout
        this.setLayout(new BorderLayout());
        //add main game panel to center of window
        this.add(gamePanel, BorderLayout.CENTER);
        //add panel with components to south
        this.add(compPanel, BorderLayout.SOUTH);
        //shows window
        this.setVisible(true);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        GameWindow app = new GameWindow();
    }
    
}
