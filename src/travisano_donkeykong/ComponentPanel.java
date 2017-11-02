/************************************************************************
Purpose: panel that holds labels and buttons to give more info to player
Author: Anthony Travisano
Date: 4/28/17
************************************************************************/
package travisano_donkeykong;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ComponentPanel extends JPanel{
    //button that starts game
    private JButton btnStart;
    //button that displays game's instructions
    private JButton btnInstructions;
    //label to display current amount of lives
    public static JLabel lblLives;
    //label to display current score
    public static JLabel lblScore;
    //label to display if cheat is being ussed
    public static JLabel lblCheat;
    //reference of Game Panel
    private GamePanel gamePanel;
    //ref of Game Sound
    private GameSound gameSound;
    
    //constructor
    public ComponentPanel(GamePanel gamePanel, GameSound gameSound)
    {
        this.gameSound = gameSound;
        this.gamePanel = gamePanel;
        //initialize buttons
        btnStart = new JButton("Start");
        btnInstructions = new JButton("Instructions");
        //intitialize labels
        lblLives = new JLabel("Lives: 3");
        lblScore = new JLabel("Score: 0");
        lblCheat = new JLabel("Cheat: OFF");
        //add components to panel
        this.add(btnStart);
        this.add(btnInstructions);
        this.add(lblLives);
        this.add(lblScore);
        this.add(lblCheat);
        //setup button listeners
        btnStart.addActionListener(new ButtonListener());
        btnInstructions.addActionListener(new ButtonListener());
        //allows panel to be most important, maintain focus
        btnStart.setFocusable(false);
        btnInstructions.setFocusable(false);
    }
    
    /*
    private inner class to handle button presses
    */
    private class ButtonListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            if(e.getSource() == btnStart)
            {
                try
                {
                    //starts game if not already running
                    if(!gamePanel.isGameRunning())
                    {
                        gameSound.stopIntro();
                        gameSound.playGameTheme();
                        gamePanel.beginGame();
                    }
                }
                catch(Exception ex)
                {
                    JOptionPane.showMessageDialog(null, "Game Cannot Be Ran More Than Once.\n"
                            + "Please Exit Window.");
                    
                }
            }
            //displays a message dialog with game instructions
            if(e.getSource() == btnInstructions)
            {
                JOptionPane.showMessageDialog(null, "Instructions: "
                        + "\n-You play as mario, the guy in red overalls"
                        + "\nObjective: "
                        + "\n-climb ladders to advance in level"
                        + "\n-reach the key to go to next level"
                        + "\n-avoid barrels and the fireball"
                        + "\nHow to Play: "
                        + "\n-UP ARROW when in front of ladder to climb"
                        + "\n-LEFT ARROW to go left"
                        + "\n-RIGHT ARROW to go right"
                        + "\n-SPACEBAR to jump"
                        + "\n-J KEY to activate invincibility"
                        + "\nPickups: "
                        + "\n-the hammer pickup allows you destroy enemies"
                        + "\nScoring: "
                        + "\n+10,000 for clearing level"
                        + "\n+500 for destroying fireball"
                        + "\n+100 for destroying barrel"
                        + "\n\n Good Luck!"
                        
                        , "Instructions", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
}
