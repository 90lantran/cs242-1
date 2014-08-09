package model;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * A class that describes blocks that
 * are unable to move, but are interactive in
 * that the player can access them through certain
 * actions.
 * @author Will
 *
 */
public class InteractiveBlock extends Block {
	
	public InteractiveBlock() {}
	
	/**
	 * A constructor for a block.
	 * @param x the x position of the block
	 * @param y the y position of the block
	 * @param type the type of block
	 * @param spriteSheet the sprite sheet filename
	 */
	public InteractiveBlock(int x, int y, BlockType type, String spriteSheet) {
		this.x = x;
		this.y = y;
		this.type = type;
		try {
			this.spriteSheet = ImageIO.read(new File(spriteSheet));
		} catch (IOException e) {
			System.out.println("Sprite file for '" + String.valueOf(type) + 
							   "' not found at: '" + spriteSheet + "' .");
		}
	}
}
