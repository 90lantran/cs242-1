package controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

import model.Block;
import model.Level;
import model.MapType;
import model.Pair;

/**
 * A class to take the keyboard
 * input from the user as it comes
 * in and make calls to the model to 
 * update the underlying structure.
 * @author hempy2
 *
 */
public class UserInput {
	private static Level level;
	
	public static Level getLevel() {
		return level;
	}
	
	/**
	 * Starts the game given an instance of the
	 * GUI frame.
	 * @param frame the frame to display the game in
	 */
	public static void initialize(JFrame frame,MapType map) {
		addLevel(frame,MapType.SIMPLE);
		frame.addKeyListener(getKeyBoard());
	}
	
	/**
	 * Creates a special listener for keyboard input
	 * that takes the user interaction with the game
	 * and translates it to changes in the model. 
	 * @return a key listener initialized for the game input
	 */
	public static KeyListener getKeyBoard() {
		KeyListener keyboard = new KeyListener () {
			public void keyReleased(KeyEvent e) {}
			public void keyTyped(KeyEvent e) {}
			
			@Override
			/**
			 *  When a key is pressed, this updates
			 *  the character sprite in the frame.
			 *  @param e the key pressed in the event
			 */
			public void keyPressed(KeyEvent e) {
				Block[][] blocks = level.getBlocks();
				Pair mario = level.getMario();
				
				switch(e.getKeyCode()) {
				case 37: /** LEFT ARROW **/
					blocks[mario.x][mario.y].move(-2,0);
					break;
				case 32: /** SPACE BAR **/
				case 38: /** UP ARROW **/
					blocks[mario.x][mario.y].impulse(0,-4);
					break;
				case 39: /** RIGHT ARROW **/
					blocks[mario.x][mario.y].move(2,0);
					break;
				case 40: /** DOWN ARROW **/
					break;
				default:
					break;
				}	
			}
		};
		
		return keyboard;
	}
	
	/**
	 * Adds a level to the current GUI
	 * given the name of the map file.
	 * @param frame the window for the game
	 * @param m the type of map to play on
	 */
	public static void addLevel(JFrame frame,MapType m) {
		level = new Level(m);
		frame.add(level);
	}
}
