package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;

import model.Level;
import model.block.Block;
import model.types.MapType;

import org.junit.Test;

import controller.UserInput;

public class UserInputTest {
	
	/** 
	 * A function to avoid race 
	 * conditions between the tests
	 * @return a separate UserInput instance 
	 * for the test.
	 */
	public UserInput setUp() {
		JFrame frame;
		UserInput ui;
		
		frame = new JFrame("Mario");
		ui = new UserInput();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ui.initialize(frame,MapType.SIMPLE);
		frame.setResizable(false);
		frame.setVisible(true);
		
		return ui;
	}
	
	/**
	 * A helper function that will
	 * press the given key for 
	 * several frames.
	 * @param frame the frame to repaint
	 * @param e the key to press
	 * @param repaints the number of frame repaints to do
	 */
	public void pressKey(JFrame frame, int e, int repaints) {
		Robot robot;
		try {
			robot = new Robot();
			
			for (int i = 0; i < repaints; i++) {
				frame.repaint();
				robot.keyPress(e);
				try{Thread.sleep(100);}catch(InterruptedException exp){}
				robot.keyRelease(e);
			}
			
		} catch (AWTException e1) {
			e1.printStackTrace();
		}
	}
	
	@Test
	/**
	 * A test for simple forward movement.
	 * @
	 */
	public void testRightArrowKey()  {
		UserInput ui = setUp();
		JFrame frame = ui.window;
		Level level = ui.level;
		Block mario = level.mario;
		
		int xbefore = mario.x;
		int ybefore = mario.y;

		pressKey(frame,KeyEvent.VK_RIGHT,15);

		int xafter = mario.x;
		int yafter = mario.y;
		
		assertTrue(xbefore < xafter);
		assertEquals(ybefore,yafter);
	}
	
	@Test
	/**
	 * A test for leftward movement. Since mario
	 * starts of at the far left, the position shouldn't change
	 * when the key is pressed. No errors should occur causing
	 * any sort of out of bounds. 
	 * @
	 */
	public void testLeftArrowKey()  {
		UserInput ui = setUp();
		JFrame frame = ui.window;
		Level level = ui.level;
		Block mario = level.mario;
		
		pressKey(frame,KeyEvent.VK_LEFT,15);
		
		int xbefore = mario.x;
		int ybefore = mario.y;
		
		pressKey(frame,KeyEvent.VK_LEFT,15);
				
		int xafter = mario.x;
		int yafter = mario.y;
		
		assertEquals(xafter,xbefore);
		assertEquals(ybefore,yafter);
	}
	
	@Test
	/**
	 * The test moves Mario right 
	 * first in order to check that the left key also functions.
	 * @
	 */
	public void testLeftMove()  {
		UserInput ui = setUp();
		JFrame frame = ui.window;
		Level level = ui.level;
		Block mario = level.mario;
		

		pressKey(frame,KeyEvent.VK_RIGHT,20);
		
		int xbefore = mario.x;
		int ybefore = mario.y;

		pressKey(frame,KeyEvent.VK_LEFT,10);
		
		int xafter = mario.x;
		int yafter = mario.y;
	
		assertTrue(xafter < xbefore);
		assertEquals(ybefore,yafter);
	}
	
	@Test
	/**
	 * A test for jumping with the up arrow key.
	 * @
	 */
	public void testUpArrowKey()  {
		UserInput ui = setUp();
		JFrame frame = ui.window;
		Level level = ui.level;
		Block mario = level.mario;
		
		int ybefore = mario.y;

		pressKey(frame,KeyEvent.VK_UP,15);

		int yafter = mario.y;
		
		assertTrue(yafter < ybefore);
	}

}
