package model.block;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import model.Pair;
import model.types.BlockType;

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
	public int x,y;
	public BlockType type;
	public BufferedImage spriteSheet;
	public BufferedImage sprite; 	//TODO: spriteSheet.getSubimage(x, y, w, h)
	public boolean solid = true;
	
	/** Place holder for declaring a 2D array **/
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
		case COIN:
			return new InteractiveBlock(x,y,btype,imageDir);
		default:
			return new Block();	
		}
	}
	
	/**
	 * Checks if a given block is a movingBlock,
	 * and casts it to a movingBlock object if it
	 * is.
	 * @return a type-casted block representing the original
	 * null, if the object is not a moving block
	 */
	public MovingBlock isMovingBlock() {
		if (this.getClass() == MovingBlock.class) 
			return (MovingBlock)this;
		else
			return null;
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
		
		if (this.x == other.x && this.y == other.y) return false;
		
		if (this.solid && other.solid) {
			int rec1Top    = this.y;
			int rec1Bottom = this.y + this.sprite.getHeight();
			int rec1Left   = this.x;
			int rec1Right  = this.x + this.sprite.getWidth();
			
			int rec2Top	   = other.y;
			int rec2Bottom = other.y + other.sprite.getHeight();
			int rec2Left   = other.x;
			int rec2Right  = other.x + other.sprite.getWidth();
			
			if (!(rec1Bottom < rec2Top ||
				  rec1Top > rec2Bottom ||
				  rec1Left > rec2Right ||
				  rec1Right < rec2Left))
				status = true;
		}
		
		return status;
	}
	
	/**
	 * Rounds the block off to the nearest block
	 * in the array based on the coordinates
	 * of the center of the spriteSheet.
	 * @return the pair representing the closest location
	 * in the block array to the sprite.
	 */
	public Pair nearestBlock() {
		Pair center = new Pair(0,0);
		center.x = x+(sprite.getWidth())/2;
		center.y = y+(sprite.getHeight())/2;
		
		int x = (int)Math.floor(center.x/33);
		int y = (int)Math.floor(center.y/33);
		
		return new Pair(x,y);
	}
	
	/**
	 * A function that will vertically flip the
	 * sprite for when the sprite changes 
	 * directions.
	 */
	public void flipSprite() {
		AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
		tx.translate(-sprite.getWidth(null), 0);
		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
		sprite = op.filter(sprite, null);
	}
}
