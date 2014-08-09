package model.block;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import model.types.BlockType;

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
	 * @param type the type of block
	 * @param sprite the sprite sheet filename
	 */
	public StaticBlock(int x, int y, BlockType type, String sprite) {
		this.x = x;
		this.y = y;
		this.type = type;
		
		if (type == BlockType.BACKGROUND)  {
			this.solid = false;
		}
		
		if (sprite == null) {
			this.sprite = null;
			return;
		}
		
		try {
			this.sprite = ImageIO.read(new File(sprite));
		} catch (IOException e) {
			System.out.println("Sprite file for '" + String.valueOf(type) + 
							   "' not found at: '" + sprite + "' .");
		}
	}
}
