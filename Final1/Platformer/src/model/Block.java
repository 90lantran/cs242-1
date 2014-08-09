package model;

import java.awt.image.BufferedImage;

/**
 * A class description used for breaking up
 * the level into small chunks that each represent
 * a portion of the screen. Each level will be made
 * of many blocks.
 * 
 * Collision detection code help from: http://bit.ly/1iuE7mk
 * @author Will
 */
public class Block {
	int x,y;
	int gravity = 4;
	BlockType type;
	BufferedImage spriteSheet;
	boolean solid = true;
	
	public int getX() { return x; }
	public int getY() { return y; }
	public BlockType getType() { return type; }
	public BufferedImage getSprite() { return spriteSheet; }
	
	/**
	 * Moves the block by the given integers.
	 * @param xincr the amount to move in the x direction
	 * @param yincr the amount to move in the y direction
	 */
	public void move(int xincr,int yincr) { 
		MovingBlock block = this.isMovingBlock();
		if (block == null) return;
		block.velocity.x += xincr;
		block.velocity.y += yincr;
	}
	
	/**
	 * Creates an impulse on the block in the
	 * given direction (xdir,ydir) with corresponding
	 * magnitudes.
	 * @param xdir the magnitude of impulse in the x direction
	 * @param ydir the magnitude of impulse in the y direction
	 */
	public void impulse(int xdir,int ydir) {
		MovingBlock block = this.isMovingBlock();
		if (block == null) return;
		block.velocity.x = xdir;
		block.velocity.y = ydir;
	}
	
	/**
	 * A function used to simulate gravity on
	 * moving blocks.
	 * @param collision true if a collision has occurred.
	 */
	public void gravity(boolean collision) {
		MovingBlock block = this.isMovingBlock();
		if (block == null) return;
		
		if (block.velocity.y < 2) {
			block.velocity.y += gravity;
		}
		
		if (block.velocity.y > 0 && collision) {
			block.velocity.y = 0;
			return;
		}
	}
	
	/**
	 * A function that updates the moving blocks
	 * in the array given their velocities.
	 */
	public void update() {
		MovingBlock block = this.isMovingBlock();
		if (block == null) return;
		block.x += block.velocity.x;
		block.y += block.velocity.y;
	}
	
	/**
	 * Returns true if this block would collide
	 * with the other block.
	 * @param other the other Block
	 * @return true if there would be a collision.
	 * false, otherwise.
	 */
	public boolean collision(Block other) {
		boolean status = false;
		
		if (this.solid && other.solid) {
			int rec1Top    = this.y;
			int rec1Bottom = this.y + this.spriteSheet.getHeight();
			int rec1Left   = this.x;
			int rec1Right  = this.x + this.spriteSheet.getWidth();
			
			int rec2Top	   = other.y;
			int rec2Bottom = other.y + other.spriteSheet.getHeight();
			int rec2Left   = other.x;
			int rec2Right  = other.x + other.spriteSheet.getWidth();
			
			if (!(rec1Bottom < rec2Top ||
				  rec1Top > rec2Bottom ||
				  rec1Left > rec2Right ||
				  rec1Right < rec2Left))
				status = true;
		}
		
		return status;
	}
	
	
	/**
	 * Checks if a given block is a movingBlock,
	 * and casts it to a movingBlock object if it
	 * is.
	 * @return a type-casted block representing the original
	 * null, if the object is not a moving block
	 */
	public MovingBlock isMovingBlock() {
		if (this.getClass() == MovingBlock.class) {
			return (MovingBlock)this;
		}
		return null;
	}
	
	public Block(){}
	
	/**
	 * A block constructor that will 
	 * initialize the block as one of 
	 * the various subclasses of the 
	 * Block data type.
	 * @param blockType the type of block to initialize
	 * @return the initialized block
	 */
	public static Block create(int x, int y, BlockType btype) {
		String imageDir = btype.getImageFileName();
		
		switch (btype) {
		case BACKGROUND:
		case BOX:
		case GROUND:
			return new StaticBlock(x,y,btype,imageDir);
		case GOOMBA:
		case KOOPA:
		case MARIO:
			return new MovingBlock(x,y,btype,imageDir);
		case BRICK:
		case QUESTION:
			return new InteractiveBlock(x,y,btype,imageDir);
		default:
			return new Block();	
		}
	}
	
	
}
