package tests;

import javax.swing.JFrame;

import model.types.MapType;
import controller.UserInput;

/**
 * NOTE:
 * This is a MANUAL TEST CASE.
 * Do not try to include this in 
 * the AllTests.java class. It will break!
 * 
 * Please see the testmanual.pdf or testmanual.odt
 * located in the top-level project folder for 
 * information on how to run this test.
 * 
 * @author hempy2
 *
 */
public class ManualGUITest {
	static final int UPDATE_RATE = 25;
	static final long UPDATE_PERIOD = 1000000000L / UPDATE_RATE;
	static JFrame frame;
	/**
	 * A function that will display the first
	 * level of our game to the global frame variable.
	 */
	public static void display() {
		frame = new JFrame("Mario");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		UserInput ui = new UserInput();
		ui.initialize(frame,MapType.SIMPLE);
		frame.setSize(585,8*33+31);
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
