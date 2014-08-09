package model;

import java.awt.image.BufferedImage;
import java.io.File;

/**
 * A class description used for breaking up
 * the level into small chunks that each represent
 * a portion of the screen. Each level will be made
 * of many blocks.
 * 
 * @author Will
 *
 */
public class Block {
	int x,y;
	String blockType;
	BufferedImage spriteSheet;
	
	public int getX() { return x; }
	public int getY() { return y; }
	public String getType() { return blockType; }
	public BufferedImage getSprite() { return spriteSheet; }
	
	public Block(){}
	
	/**
	 * A block constructor that will 
	 * initialize the block as one of 
	 * the various subclasses of the 
	 * Block data type.
	 * @param blockType the string representing
	 * the type of block to initialize this as.
	 * @return the initialized block
	 */
	public static Block create(int x, int y, String blockType) {
		String imageDir = new File("").getAbsolutePath() + "/images/";
		if (blockType == "Background")
			return new StaticBlock(x,y,"Background",null);
		else if (blockType == "Goomba")
			return new MovingBlock(x,y,"Goomba",imageDir + "sprites/goomba.png");
		else if (blockType == "Koopa")
			return new MovingBlock(x,y,"Koopa",imageDir + "sprites/koopa1.png");
		else if (blockType == "Brick")
			return new InteractiveBlock(x,y,"Brick",imageDir + "blocks/brick.png");
		else if (blockType == "Box")
			return new StaticBlock(x,y,"Box",imageDir + "blocks/box.png");
		else if (blockType == "Question")
			return new InteractiveBlock(x,y,"Question",imageDir + "blocks/question.png");
		else if (blockType == "Ground")
			return new StaticBlock(x,y,"Ground",imageDir + "ground/ground_top.png");
		else if (blockType == "Mario")
			return new MovingBlock(x,y,"Mario",imageDir + "sprites/mario.png");

		return new Block();
	}
}
