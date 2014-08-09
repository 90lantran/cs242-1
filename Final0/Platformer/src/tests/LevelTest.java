package tests;

import static org.junit.Assert.*;

import java.io.File;

import model.Block;
import model.InteractiveBlock;
import model.Level;
import model.MovingBlock;
import model.StaticBlock;

import org.junit.*;

public class LevelTest {
	
	@Test
	/**
	 * Checks if adding a map of background
	 * characters results in an array of 
	 * correctly typed static blocks.
	 */
	public void backgroundMapString() {
		String currentDir = new File("").getAbsolutePath();
		String testmap = currentDir + "/maps/test1.txt";
		Level level = new Level(testmap);
		Block[][] blocks = level.getBlocks();
		StaticBlock staticb = new StaticBlock();
		for (int i = 0; i < blocks.length; i++) {
			for (int j = 0; j < blocks[0].length; j++) {
				assertEquals(staticb.getClass(),blocks[i][j].getClass());
			}
		}
	}
	
	@Test
	/**
	 * Test2 text file contains the various available blocks
	 * that can be initialized for a given level and parses 
	 * through each possibility.
	 * 
	 * The interactive blocks are question blocks, and bricks,
	 */
	public void interactiveBlockMap() {
		String currentDir = new File("").getAbsolutePath();
		String testmap = currentDir + "/maps/test2.txt";
		Level level = new Level(testmap);
		Block[][] blocks = level.getBlocks();
		InteractiveBlock interb = new InteractiveBlock();
		
		for (int i = 0; i < blocks.length; i++) {
			assertEquals(interb.getClass(),blocks[i][0].getClass());
			assertEquals(interb.getClass(),blocks[i][1].getClass());
		}
	}
	
	@Test
	public void staticBlockMap() {
		String currentDir = new File("").getAbsolutePath();
		String testmap = currentDir + "/maps/test2.txt";
		Level level = new Level(testmap);
		Block[][] blocks = level.getBlocks();
		StaticBlock staticb = new StaticBlock();
		
		for (int i = 0; i < blocks.length; i++) {
			assertEquals(staticb.getClass(),blocks[i][3].getClass());
			assertEquals(staticb.getClass(),blocks[i][5].getClass());
		}
	}
	
	@Test
	public void movingBlockMap() {
		String currentDir = new File("").getAbsolutePath();
		String testmap = currentDir + "/maps/test2.txt";
		Level level = new Level(testmap);
		Block[][] blocks = level.getBlocks();
		MovingBlock movingb = new MovingBlock();
		
		for (int i = 0; i < blocks.length; i++) {
			assertEquals(movingb.getClass(),blocks[i][2].getClass());
			assertEquals(movingb.getClass(),blocks[i][4].getClass());
		}	
	}
}
