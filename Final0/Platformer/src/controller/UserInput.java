package controller;

import javax.swing.JFrame;

import model.Level;

/**
 * A class to take the keyboard
 * input from the user as it comes
 * in and make calls to the model to 
 * update the underlying structure.
 * @author hempy2
 *
 */
public class UserInput {
	
	/**
	 * Adds a level to the current GUI
	 * given the name of the map file.
	 * 
	 * INFO FOR LATER USE
	 * JPanel it = new JPanel(new FlowLayout(FlowLayout.LEADING));
	 * it.add(mainPanel);
	 * 
	 * A FlowLayout always respects the preferred 
	 * size of the components added to it.
	 * Get rid of the "it" panel and try just using
	 * 
	 * //this.getContentPane().add(it);
	 * add(mainPanel);
	 * 
	 * The default layout for a frame is a BorderLayout which will 
	 * try to increase/decrease the size of all components added to it.
	 * 
	 * @param frame the window for the game
	 * @param filename the name of the map file
	 * @param background the name of the background image
	 */
	public static void addLevel(JFrame frame,String filename,String background) {
		Level level = new Level(filename);
		level.setBackground(background);
		frame.add(level);
	}
}
