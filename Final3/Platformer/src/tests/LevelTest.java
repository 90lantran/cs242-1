package tests;

import static org.junit.Assert.assertEquals;
import model.Level;
import model.block.Block;
import model.block.InteractiveBlock;
import model.block.MovingBlock;
import model.block.StaticBlock;
import model.types.MapType;

import org.junit.Test;

public class LevelTest {
	
	@Test
	/**
	 * Checks if adding a map of background
	 * characters results in an array of 
	 * correctly typed static blocks.
	 */
	public void backgroundMapString() { 
		Level level = new Level(MapType.TEST1);
		Block[][] blocks = level.blocks;
		for (int i = 0; i < blocks.length; i++) {
			for (int j = 0; j < blocks[0].length; j++) {
				assertEquals(StaticBlock.class,blocks[i][j].getClass());
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
		Level level = new Level(MapType.TEST2);
		Block[][] blocks = level.blocks;
		
		for (int i = 0; i < blocks.length; i++) {
			assertEquals(InteractiveBlock.class,blocks[i][0].getClass());
			assertEquals(InteractiveBlock.class,blocks[i][1].getClass());
		}
	}
	
	@Test
	public void staticBlockMap() {
		Level level = new Level(MapType.TEST2);
		Block[][] blocks = level.blocks;
		
		for (int i = 0; i < blocks.length; i++) {
			assertEquals(StaticBlock.class,blocks[i][3].getClass());
			assertEquals(StaticBlock.class,blocks[i][5].getClass());
		}
	}
	
	@Test
	public void movingBlockMap() {
		Level level = new Level(MapType.TEST2);
		Block[][] blocks = level.blocks;
		
		for (int i = 0; i < blocks.length; i++) {
			assertEquals(MovingBlock.class,blocks[i][2].getClass());
			assertEquals(MovingBlock.class,blocks[i][4].getClass());
		}	
	}
}
