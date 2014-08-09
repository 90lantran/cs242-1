package model;

import java.io.File;

/**
 * A class defining the different
 * available maps for game play.
 * @author hempy2
 */
public enum MapType {
	TEST1,TEST2,SIMPLE;
	
	/**
	 * Gets the map based on the type given.
	 * @return the file path for the map text file.
	 */
	public String getMap() {
		String map = new File("").getAbsolutePath();
		
		switch (this) {
		default:		map = null;					break;
		case TEST1:		map += "/maps/test1.txt"; 	break;
		case TEST2:		map += "/maps/test2.txt";	break;
		case SIMPLE:	map += "/maps/simple.txt";	break;
		}
		
		return map;
	}
	
	/**
	 * Gets the background based on the map type.
	 * @return the file path for the corresponding background
	 */
	public String getBackground() {
		String background = new File("").getAbsolutePath();
		
		switch (this) {
		default:
		case TEST1:	
		case TEST2:
		case SIMPLE: 	background += "/images/backgrounds/clouds.jpg";	break;
		}
		
		return background;
	}
}
