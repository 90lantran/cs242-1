package tests;

import static org.junit.Assert.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import model.Block;
import model.InteractiveBlock;
import model.MovingBlock;
import model.StaticBlock;

import org.junit.Test;

public class BlockTest {

	@Test
	public void createNonNullBlock() {
		Block block = Block.create(0, 0, "Goomba");
		assertNotNull(block);
	}
	
	@Test
	public void createGoomba() {
		Block block = Block.create(2, 4, "Goomba");
		assertEquals(block.getType(),"Goomba");
	}
	
	@Test
	public void correctBlockImage() {
		Block block = Block.create(2,4,"Goomba");
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
				assertEquals(goomba.getRGB(i,j),block.getSprite().getRGB(i, j));
			}
		}
	}
	
	@Test
	public void correctBlockClass() {
		Block block = Block.create(2,4,"Goomba");
		MovingBlock movingb = new MovingBlock();
		assertEquals(block.getClass(),movingb.getClass());
	}
	
	@Test
	public void koopaIsMovingBlock() {
		Block block = Block.create(2,4,"Koopa");
		MovingBlock movingb = new MovingBlock();
		assertEquals(block.getClass(),movingb.getClass());
	}
	
	@Test
	public void boxIsStatic() {
		Block block = Block.create(2,4,"Box");
		StaticBlock staticb = new StaticBlock();
		assertEquals(block.getClass(),staticb.getClass());
	}
	
	@Test
	public void groundIsStatic() {
		Block block = Block.create(2,4,"Ground");
		StaticBlock staticb = new StaticBlock();
		assertEquals(block.getClass(),staticb.getClass());
	}
	
	@Test
	public void brickIsInteractive() {
		Block block = Block.create(2,4,"Brick");
		InteractiveBlock interb = new InteractiveBlock();
		assertEquals(block.getClass(),interb.getClass());
	}
	
	@Test
	public void questionIsInteractive() {
		Block block = Block.create(2,4,"Question");
		InteractiveBlock interb = new InteractiveBlock();
		assertEquals(block.getClass(),interb.getClass());
	}
}
