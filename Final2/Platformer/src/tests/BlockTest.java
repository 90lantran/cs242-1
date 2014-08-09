package tests;

import static org.junit.Assert.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import model.block.Block;
import model.block.InteractiveBlock;
import model.block.MovingBlock;
import model.block.StaticBlock;
import model.types.BlockType;

import org.junit.Test;

public class BlockTest {

	@Test
	public void createNonNullBlock() {
		Block block = Block.create(0, 0, BlockType.GOOMBA);
		assertNotNull(block);
	}
	
	@Test
	public void createGoomba() {
		Block block = Block.create(2, 4, BlockType.GOOMBA);
		assertEquals(block.type,BlockType.GOOMBA);
	}
	
	@Test
	public void correctBlockImage() {
		Block block = Block.create(2,4,BlockType.GOOMBA);
		BufferedImage goomba = null;
		try {
			String dir = new File("").getAbsolutePath();
			goomba = ImageIO.read(new File(dir +"/images/sprites/goomba.png"));
		} catch (IOException e) {
			fail("Goomba image doesn't exist on disk.");
		}
		
		int width = goomba.getWidth();
		int height = goomba.getHeight();
		for (int i = 0 ; i < width; i++) {
			for (int j = 0; j < height; j++) {
				assertEquals(goomba.getRGB(i,j),block.sprite.getRGB(i, j));
			}
		}
	}
	
	@Test
	public void correctBlockClass() {
		Block block = Block.create(2,4,BlockType.GOOMBA);
		MovingBlock movingb = new MovingBlock();
		assertEquals(block.getClass(),movingb.getClass());
	}
	
	@Test
	public void koopaIsMovingBlock() {
		Block block = Block.create(2,4,BlockType.KOOPA);
		MovingBlock movingb = new MovingBlock();
		assertEquals(block.getClass(),movingb.getClass());
	}
	
	@Test
	public void boxIsStatic() {
		Block block = Block.create(2,4,BlockType.BOX);
		StaticBlock staticb = new StaticBlock();
		assertEquals(block.getClass(),staticb.getClass());
	}
	
	@Test
	public void groundIsStatic() {
		Block block = Block.create(2,4,BlockType.GROUND);
		StaticBlock staticb = new StaticBlock();
		assertEquals(block.getClass(),staticb.getClass());
	}
	
	@Test
	public void brickIsInteractive() {
		Block block = Block.create(2,4,BlockType.BRICK);
		InteractiveBlock interb = new InteractiveBlock();
		assertEquals(block.getClass(),interb.getClass());
	}
	
	@Test
	public void questionIsInteractive() {
		Block block = Block.create(2,4,BlockType.QUESTION);
		InteractiveBlock interb = new InteractiveBlock();
		assertEquals(block.getClass(),interb.getClass());
	}
	
	@Test
	/**
	 * This should pass as a collision:
	 * *----*---*
	 * |  |2| 	|
	 * |2 |1| 1 |
	 * |  | |   |
	 * *----*---
	 */
	public void testSimpleCollision() {
		Block mario_1 = Block.create(0, 0, BlockType.MARIO);
		Block mario_2 = Block.create(20, 0, BlockType.MARIO);
		assertTrue(mario_1.collision(mario_2));
	}
	
	@Test
	/**
	 * This should fail as a collision:
	 * *----*	*---*
	 * |	|	|	|
	 * |  1 |	| 2 |
	 * |	|	|	|
	 * *----*	*---*
	 */
	public void testFailCollision() {
		Block mario_1 = Block.create(0, 0, BlockType.MARIO);
		Block mario_2 = Block.create(28, 0, BlockType.MARIO);
		assertFalse(mario_1.collision(mario_2));
	}
	
	@Test
	/**
	 * This should pass as a collision
	 * for both blocks.
	 * 		*----*
	 * 		|	 |
	 * 		| 1  |
	 * *-----*	 |
	 * |	*|---*
	 * |	 |
	 * |  2  |
	 * |	 |
	 * *-----*
	 * The size of Mario.png is 26 x 33, so 
	 * we can contrive this situation with two blocks
	 * slightly offset from each other.
	 */
	public void testCornerCollision() {
		Block mario_1 = Block.create(20, 0, BlockType.MARIO);
		Block mario_2 = Block.create(0, 30, BlockType.MARIO);
		assertTrue(mario_1.collision(mario_2));
	}
}
