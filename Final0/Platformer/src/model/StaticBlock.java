package model;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * A class to describe blocks which are
 * static and unable to move. They are simply
 * a part of the background or landscape. 
 * @author Will
 *
 */
public class StaticBlock extends Block {
	
	public StaticBlock() {}
	
	/**
	 * A constructor for a block.
	 * @param x the x position of the block
	 * @param y the y position of the block
	 * @param blockType the type of block
	 * @param spriteSheet the sprite sheet filename
	 */
	public StaticBlock(int x, int y, String blockType, String spriteSheet) {
		this.x = x;
		this.y = y;
		this.blockType = blockType;
		if (spriteSheet == null) return;
		try {
			this.spriteSheet = ImageIO.read(new File(spriteSheet));
		} catch (IOException e) {
			System.out.println("Sprite file not found.");
		}
	}
}
