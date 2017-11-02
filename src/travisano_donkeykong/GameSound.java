/********************************************************
Purpose: holds all of the game sound
Author: Anthony Travisano
Date: 5/1/17
********************************************************/
package travisano_donkeykong;

import java.io.IOException;
import java.net.URL;
import javax.sound.sampled.*;

public class GameSound {
    //holds the intro song
    private Clip intro;
    //holds the hammer pickup sound
    private Clip hammerTime;
    //holds background music
    private Clip backMusic;
    //clip played when game is over
    private Clip gameOver;
    
    //constructor
    public GameSound()
    {
        intro = getLoadedSound(intro, "travisano_donkeykong\\DKintro.wav");
        hammerTime = getLoadedSound(hammerTime, "travisano_donkeykong\\DKHammerTime.wav");
        backMusic = getLoadedSound(backMusic, "travisano_donkeykong\\DKmainTheme.wav");
        gameOver = getLoadedSound(gameOver, "travisano_donkeykong\\DKgameOver.wav");
        //plays intro onload
        intro.start();
    }
    
    /*
    finds .wav file and returns the sound
    input: the variable of clip, the filename of sound
    output: functioning sound
    */
    public Clip getLoadedSound(Clip clip, String filename)
    {
	try 
        {
	// Open an audio input stream.
	URL url = this.getClass().getClassLoader().getResource(filename);
	AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
	// Get a sound clip resource.
	clip = AudioSystem.getClip();
	// Open audio clip and load samples from the audio input stream.
	clip.open(audioIn);
	} 
	catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) 
        {
	    System.out.println("unable to access sound" + e);
        }
        return clip;
    }
    
    /*
    stops intro music.  mostly used for incase user hits start button quickly
    */
    public void stopIntro()
    {
        intro.stop();
    }
    /*
    loops music continuously until told to stop
    */
    public void playGameTheme()
    {
        backMusic.loop(-1);
    }
    
    /*
    ends background music
    */
    public void stopGameTheme()
    {
        backMusic.stop();
    }
    
    /*
    starts the music played when player has hammer pickup
    */
    public void playHammerTime()
    {
        //only one song can play at once
        stopGameTheme();
        //song plays until hammer powerup runs out
        hammerTime.loop(-1);
    }
    
    /*
    stops the hammer music
    */
    public void stopHammerTime()
    {
        hammerTime.stop();
        playGameTheme();
    }
    
    /*
    ends all music and plays gameover tune
    */
    public void playGameOver()
    {
        hammerTime.stop();
        backMusic.stop();
        gameOver.start();
    }
    
    
    
}
