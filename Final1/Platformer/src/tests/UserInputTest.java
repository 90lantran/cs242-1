package tests;

import static org.junit.Assert.*;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;

import model.Block;
import model.Level;
import model.MapType;
import model.Pair;

import org.junit.BeforeClass;
import org.junit.Test;

import controller.UserInput;

public class UserInputTest {
	static JFrame frame;
	
	@BeforeClass
	public static void setUp() {
		frame = new JFrame("Mario");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		UserInput.initialize(frame,MapType.SIMPLE);
		frame.setResizable(false);
		frame.setVisible(true);
	}
	
	@Test
	/**
	 * A test for simple forward movement.
	 * @throws AWTException
	 */
	public void testRightArrowKey() throws AWTException {
		Level level = UserInput.getLevel();
		Block[][] blocks = level.getBlocks();
		Pair mario = level.getMario();
		
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_RIGHT);
		for (int i = 0; i < 15; i++) {
			frame.repaint();
		}
		try{Thread.sleep(1000);}catch(InterruptedException e){}
		robot.keyRelease(KeyEvent.VK_RIGHT);
		
		Block[][] blocks_a = level.getBlocks();
		Pair mario_after = level.getMario();
		
		assertEquals(blocks[mario.x][mario.y].getY(),blocks_a[mario_after.x][mario_after.y].getY());
		assertTrue(blocks[mario.x][mario.y].getX() <= blocks_a[mario_after.x][mario_after.y].getX());
	}
	
	@Test
	/**
	 * A test for leftward movement. Since mario
	 * starts of at the far left, the position shouldn't change
	 * when the key is pressed. No errors should occur causing
	 * any sort of out of bounds.
	 * @throws AWTException
	 */
	public void testLeftArrowKey() throws AWTException {
		Level level = UserInput.getLevel();
		Block[][] blocks = level.getBlocks();
		Pair mario = level.getMario();
		
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_LEFT);
		frame.repaint();
		try{Thread.sleep(250);}catch(InterruptedException e){}
		robot.keyRelease(KeyEvent.VK_LEFT);
		
		level = UserInput.getLevel();
		Block[][] blocks_a = level.getBlocks();
		Pair mario_after = level.getMario();
		
		assertEquals(blocks[mario.x][mario.y].getY(),blocks_a[mario_after.x][mario_after.y].getY());
		assertEquals(blocks[mario.x][mario.y].getX(),blocks_a[mario_after.x][mario_after.y].getX());
	}
	
	@Test
	/**
	 * A test for jumping with the up arrow key.
	 * @throws AWTException
	 */
	public void testUpArrowKey() throws AWTException {
		Level level = UserInput.getLevel();
		Block[][] blocks = level.getBlocks();
		Pair mario = level.getMario();
		
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_UP);
		frame.repaint();
		try{Thread.sleep(250);}catch(InterruptedException e){}
		robot.keyRelease(KeyEvent.VK_UP);
		
		level = UserInput.getLevel();
		Block[][] blocks_a = level.getBlocks();
		Pair mario_after = level.getMario();
		
		assertEquals(blocks[mario.x][mario.y].getY(),blocks_a[mario_after.x][mario_after.y].getY());
		assertEquals(blocks[mario.x][mario.y].getX(),blocks_a[mario_after.x][mario_after.y].getX());
	}

}
