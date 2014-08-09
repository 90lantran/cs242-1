package view;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import controller.TextInput;
import controller.UserInput;


/**
 * @author Will
 * 
 * Code snippets for game loop control taken from:
 * http://www3.ntu.edu.sg/home/ehchua/programming/java/J8d_Game_Framework.html
 */
public class ChessGUI {
	static final int UPDATE_RATE = 4;    
	static final long UPDATE_PERIOD = 1000000000L / UPDATE_RATE;
	private static JFrame frame;
		
	/**
	 * Displays the board to the screen in a new window.
	 */
	public static void display() {
		frame = new JFrame("Chess");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		UserInput.addBoard(frame);
		frame.setSize(585,650);
		frame.setResizable(false);
		frame.setJMenuBar(getMenu());
		frame.addMouseListener(UserInput.getMouse(frame));
		frame.setVisible(true);
		
		TextInput.createAndShowGUI();
	}
	
	/**
	 * The main function for the chess game. 
	 * It will constantly monitor the program 
	 * for changes in input. 
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		UserInput.initialize();
		
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
	
	/**
	 * Sets up the window menu.
	 * Create the menu bar.
	 * Build the first menu.
	 * @return the initialized menubar
	 */
	public static JMenuBar getMenu() {
		JMenuBar menuBar;
		JMenu menu;

		ActionListener actionlistener = UserInput.getButtonListener(frame);

		menuBar = new JMenuBar();

		menu = new JMenu("Board");
		JMenuItem menuitem = new JMenuItem("Undo");
		menuitem.setActionCommand("Undo");
		menuitem.addActionListener(actionlistener);
		menu.add(menuitem);

		menu.addSeparator();
		menuitem = new JMenuItem("Forfeit");
		menuitem.setActionCommand("Forfeit");
		menuitem.addActionListener(actionlistener);
		menu.add(menuitem);

		menuitem = new JMenuItem("Restart");
		menuitem.setActionCommand("Restart");
		menuitem.addActionListener(actionlistener);
		menu.add(menuitem);

		menuBar.add(menu);

		menu = new JMenu("Scores");
		menuitem = new JMenuItem("Show Results");
		menuitem.setActionCommand("Scores");
		menuitem.addActionListener(actionlistener);
		menu.add(menuitem);
		menuBar.add(menu);

		return menuBar;
	}
}
