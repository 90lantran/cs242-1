package controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

import model.Level;
import model.block.MovingBlock;
import model.types.MapType;

/**
 * A class to take the keyboard
 * input from the user as it comes
 * in and make calls to the model to 
 * update the underlying structure.
 * @author hempy2
 *
 */
public class UserInput {
	public Level level;
	public JFrame window;
	
	/**
	 * Starts the game given an instance of the
	 * GUI frame.
	 * @param frame the frame to display the game in
	 */
	public void initialize(JFrame frame,MapType map) {
		addLevel(frame,MapType.SIMPLE);
		frame.addKeyListener(getKeyBoard());
		window = frame;
	}
	
	/**
	 * Creates a special listener for keyboard input
	 * that takes the user interaction with the game
	 * and translates it to changes in the model. 
	 * @return a key listener initialized for the game input
	 */
	public KeyListener getKeyBoard() {
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
				MovingBlock mario = (MovingBlock)level.mario;
				
				switch(e.getKeyCode()) {
				case 37: /** LEFT ARROW **/
					mario.move(KeyEvent.VK_LEFT);
					break;
				case 32: /** SPACE BAR **/
				case 38: /** UP ARROW **/
					mario.jump();
					break;
				case 39: /** RIGHT ARROW **/
					mario.move(KeyEvent.VK_RIGHT);
					break;
				case 40: /** DOWN ARROW **/
					break;
				case KeyEvent.VK_A:
					/*******************
					 * DEBUGGING KEY INPUT ONLY
					 *******************/
					Level.AI = !Level.AI;
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
	public void addLevel(JFrame frame,MapType m) {
		level = new Level(m);
		frame.add(level);
		window = frame;
	}
}
