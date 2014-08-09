package model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import model.Block;

/**
 * A class to describe the level implemented.
 * It holds a complete description of all obstacles,
 * enemies, and other blocks inside the level.
 * @author Will
 *
 */
public class Level extends JPanel {
	private static final long serialVersionUID = 2390320300435663299L;
	
	BufferedImage background;
	Block[][] blocks;
	Pair mario;
	
	/**
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 * @param g the graphics element to paint the board onto
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.setBackground(Color.WHITE);
		g.drawImage(background,0,0,null);
		drawBlocks(g);
	}
	
	/**
	 * A helper function for drawing each of the blocks
	 * representing the elements of the level to the
	 * screen.
	 * @param g the graphics element for the level
	 */
	private void drawBlocks(Graphics g) {
		for (int i = blocks.length-1; i >= 0; i--) {
			for (int j = blocks[0].length-1; j >= 0; j--) {
				boolean collision = checkCollisions(i,j);
				if (collision) {
					blocks[i][j].gravity(collision);
					blocks[i][j].update();
					/**
					g.drawRect(blocks[i][j].getX(),
							   blocks[i][j].getY(),
							   blocks[i][j].getX()+blocks[i][j].spriteSheet.getWidth(),
							   blocks[i][j].getY()+blocks[i][j].spriteSheet.getHeight());
					**/
				}
			
				g.drawImage(blocks[i][j].spriteSheet,
							blocks[i][j].getX(),
							blocks[i][j].getY(),null);
			}
		}
	}
	
	/**
	 * A function that checks the collisions for 
	 * a given block. If none occur, then there
	 * it checks if a swap is necessary.
	 * @param i the block position in the Block[][] array
	 * @param j the block position in the Block[][] array
	 * @return true if the collision occurred,
	 * false otherwise
	 */
	public boolean checkCollisions(int i, int j) {
		Block b = blocks[i][j];
		
		if (inBounds(i+1,j)   && b.collision(blocks[i+1][j])) 		return true;
		if (inBounds(i,j+1)   && b.collision(blocks[i][j+1])) 		return true;
		if (inBounds(i-1,j)   && b.collision(blocks[i-1][j]))		return true;
		if (inBounds(i,j-1)   && b.collision(blocks[i][j-1]))		return true;
		
		return false;
	}
	
	
	/**
	 * If no collision happens (except the ground), 
	 * then we should swap 
	 * the entry at (i,j) with the cell that it
	 * is nearest to.
	 */
	public void swap(int i, int j) {
		Block b = blocks[i][j];
		// TODO: IMPLEMENT THIS FUNCTION, YOU FOOL.
	}
	
	/**
	 * Checks if the given pair (i,j) is in the 
	 * bounds of the Block[][] array.
	 * @param i the x index of the array
	 * @param j the y index of the array
	 * @return true if the indices are within the bounds
	 * false, otherwise
	 */
	public boolean inBounds(int i, int j) {
		return (i < blocks.length) && (i >= 0) && (j < blocks[0].length) && (j >= 0);
	}
	
	public Block[][] getBlocks() { return blocks; }
	
	public BufferedImage getBG() { return background; }
	
	/**
	 * Gets the current location of hero in 
	 * the block array.
	 * @return the (x,y) pair location for the hero 
	 * in the Block[][] array.
	 */
	public Pair getMario() {
		return mario;
	}
	
	/**
	 * Sets the background image for
	 * the level given the image filename.
	 * @param map the type of map to initialize
	 * @return true if the background image was set
	 * false, otherwise.
	 */
	public boolean setBackground(MapType map) {
		try {
			background = ImageIO.read(new File(map.getBackground()));
			return true;
		} catch (IOException e) {
			System.out.println("Background image " +
								map.getBackground() + 
								" does not exist on disk.");
			return false;
		}
	}
	/**
	 * Constructor for a level that
	 * takes the name of a text file
	 * and initializes the level based 
	 * on the contents of the file on disk.
	 * @param map the type of map to initialize
	 */
	public Level(MapType map) {
		readMapFile(map);
		setBackground(map);
	}
	
	/**
	 * A helper function for the level 
	 * constructor that will get a buffered 
	 * reader to read the filename on disk.
	 * 
	 * WARNING: 
	 * We only allow the level to be of height 8,
	 * so we disregard anything after line 8 of 
	 * the file. Also, the first line of the file
	 * sets the length of the level, so any subsequent
	 * line will have only as much length considered as
	 * the first line.
	 * 
	 * @param map the type of map to initialize
	 * @return a buffered reader if the file exists
	 * and null otherwise.
	 */
	private void readMapFile(MapType map) {
		try {
			FileReader fr = new FileReader(map.getMap());
			BufferedReader br = new BufferedReader(fr);
			
			String line = br.readLine();
			if (line == null) {
				br.close();
				return;
			}
			blocks = new Block[line.length()][8];
			int line_num = 0;
			while (line != null) {
				for (int i = 0; i < line.length(); i++) {
					blocks[i][line_num] = initBlock(i*33,line_num*33,line.charAt(i));
				}
				line = br.readLine();
				line_num++;
			}
			br.close();
		} catch (FileNotFoundException e) {
			System.out.println("Map image " +
								map.getMap() + 
								" does not exist on disk.");
		} catch (IOException e) {
			System.out.println("Failed to read the map file." + 
							   "It may be corrupted.");
		}
	}
	
	/**
	 * A helper function that will translate string
	 * characters into block files in the level.
	 * @param x the x location of the block
	 * @param y the y location of the block
	 * @param blockLetter the character value for a block
	 * on the map
	 * @return the correctly initialized block (never null)
	 * a default value if the letter is unrecognized.
	 */
	private Block initBlock(int x, int y, char blockLetter) {
		switch (blockLetter) {
		case '#': return Block.create(x,y,BlockType.BACKGROUND);
		case 'g': return Block.create(x,y,BlockType.GOOMBA);
		case 'k': return Block.create(x,y,BlockType.KOOPA);
		case 'b': return Block.create(x,y,BlockType.BRICK);
		case 'x': return Block.create(x,y,BlockType.BOX);
		case '?': return Block.create(x,y,BlockType.QUESTION);
		case '*': return Block.create(x,y,BlockType.GROUND);
		case 'm':
			mario = new Pair(x/33,y/33);
			return Block.create(x,y,BlockType.MARIO);
		default:
			System.out.println("Unrecognized block letter.");
			return Block.create(x,y,BlockType.BACKGROUND);
		}
	}
}
