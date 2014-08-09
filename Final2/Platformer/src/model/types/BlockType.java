package model.types;

import java.io.File;

/**
 * A class for naming the 
 * different types of blocks
 * the level can have.
 * @author hempy2
 *
 */
public enum BlockType {
	BACKGROUND, GOOMBA, KOOPA, 
	BRICK, BOX, QUESTION, GROUND, COIN,
	MARIO;
	
	/**
	 * Returns the image filename for a given
	 * block type. 
	 * @param btype the type of block
	 * @return the string detailing the 
	 * sprite image file location on disk
	 */
	public String getImageFileName() {
		String imageDir = new File("").getAbsolutePath() + "/images/";
		
		switch (this) {
		case BACKGROUND: imageDir += "sprites/blank.png"; 		break;
		case BOX: 		 imageDir += "blocks/box.png"; 			break;
		case GROUND: 	 imageDir += "ground/ground_top.png"; 	break;
		case GOOMBA: 	 imageDir += "sprites/goomba.png"; 		break;
		case KOOPA: 	 imageDir += "sprites/koopa1.png"; 		break;
		case MARIO: 	 imageDir += "sprites/mario.png"; 		break;
		case BRICK: 	 imageDir += "blocks/brick.png"; 		break;
		case QUESTION:	 imageDir += "blocks/question.png"; 	break;
		case COIN:		 imageDir += "blocks/coin.png";			break;
		default:		 imageDir = null;
		}
		
		return imageDir;
	}
}
