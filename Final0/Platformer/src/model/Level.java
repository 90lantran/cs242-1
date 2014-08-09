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
		for (int i = 0; i < blocks.length; i++) {
			for (int j = 0; j < blocks[0].length; j++) {
				g.drawImage(blocks[i][j].spriteSheet,i*33,j*33,null);
			}
		}
	}
	
	/**
	 * Gets the array of blocks representing
	 * the level structure. They can be of
	 * type InteractiveBlock,StaticBlock, MovingBlock,
	 * etc. 
	 * @return the underlying Block[][] array for the level
	 */
	public Block[][] getBlocks() {
		return blocks;
	}
	
	/**
	 * Gets the background image of a level.
	 * @return an Image object containing the background
	 */
	public BufferedImage getBG() {
		return background;
	}
	
	/**
	 * Sets the background image for
	 * the level given the image filename.
	 * @param filename the image pathname of the file on disk
	 * @return true if the background image was set
	 * false, otherwise.
	 */
	public boolean setBackground(String filename) {
		try {
			background = ImageIO.read(new File(filename));
			return true;
		} catch (IOException e) {
			System.out.println("Background image does " +
							   "not exist on disk.");
			return false;
		}
	}
	/**
	 * Constructor for a level that
	 * takes the name of a text file
	 * and initializes the level based 
	 * on the contents of the file on disk.
	 * @param filename
	 */
	public Level(String filename) {
		readMapFile(filename);
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
	 * @param filename the full pathname of the file on disk
	 * @return a buffered reader if the file exists
	 * and null otherwise.
	 */
	private void readMapFile(String filename) {
		try {
			FileReader fr = new FileReader(filename);
			BufferedReader br = new BufferedReader(fr);
			
			String line = br.readLine();
			if (line == null)
				return;
			blocks = new Block[line.length()][8];
			int line_num = 0;
			while (line != null) {
				for (int i = 0; i < line.length(); i++) {
					blocks[i][line_num] = initBlock(i,line_num,line.charAt(i));
				}
				line = br.readLine();
				line_num++;
			}
		} catch (FileNotFoundException e) {
			System.out.println("Map file doesn't exist on disk.");
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
		case '#':
			return Block.create(x,y,"Background");
		case 'g': 
			return Block.create(x,y,"Goomba");
		case 'k': 
			return Block.create(x,y,"Koopa");
		case 'b':
			return Block.create(x,y,"Brick");
		case 'x':
			return Block.create(x,y,"Box");
		case '?':
			return Block.create(x,y,"Question");
		case '*':
			return Block.create(x,y,"Ground");
		case 'm':
			return Block.create(x,y,"Mario");
		default:
			System.out.println("Unrecognized block letter.");
			return Block.create(x,y,"Background");
		}
	}
}
