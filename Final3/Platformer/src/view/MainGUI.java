package view;

import javax.swing.JFrame;

import model.types.MapType;

import controller.UserInput;


public class MainGUI {
	static final int UPDATE_RATE = 50;
	static final long UPDATE_PERIOD = 1000000000L / UPDATE_RATE;

	private static JFrame frame;
	public static final int WINDOW_HEIGHT = 8*33+31;
	public static final int WINDOW_WIDTH = 585;
	
	/**
	 * A function that will display the first
	 * level of our game to the global frame variable.
	 */
	public static void display() {
		frame = new JFrame("Mario");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		UserInput ui = new UserInput();
		ui.initialize(frame,MapType.SIMPLE);
		frame.setSize(WINDOW_WIDTH,WINDOW_HEIGHT);
		frame.setResizable(false);
		frame.setVisible(true);
	}
	
	/**
	 * The main function for the GUI program.
	 * It will initialize the window and repaint
	 * it based on the UPDATE PERIOD variable.
	 * @param args the command line arguments (unused)
	 */
	public static void main(String[] args) {
		display();
		
		long beginTime, timeTaken, timeLeft;
		while (true) {
	        beginTime = System.nanoTime();
	        frame.repaint();
	
	        timeTaken = System.nanoTime() - beginTime;
	        timeLeft = (UPDATE_PERIOD - timeTaken) / 1000000;
	        if (timeLeft < 10) timeLeft = 10;
	        try {
	            Thread.sleep(timeLeft);
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }

	    }

	}
}
