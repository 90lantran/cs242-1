package model;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * A class to describe blocks that are able
 * to move, such as the player character sprite,
 * and the enemies.
 * @author Will
 *
 */
public class MovingBlock extends Block {
	protected Pair velocity;
	
	public MovingBlock() {}
	
	/**
	 * A constructor for a block.
	 * @param x the x position of the block
	 * @param y the y position of the block
	 * @param type the type of block
	 * @param spriteSheet the sprite sheet filename
	 */
	public MovingBlock(int x, int y, BlockType type, String spriteSheet) {
		this.x = x;
		this.y = y;
		this.type = type;
		this.velocity = new Pair(0,0);
		try {
			this.spriteSheet = ImageIO.read(new File(spriteSheet));
		} catch (IOException e) {
			System.out.println("Sprite file for '" + String.valueOf(type) + 
							   "' not found at: '" + spriteSheet + "' .");
		}
	}
}
