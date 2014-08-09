package model;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import model.block.Block;
import model.block.MovingBlock;
import model.types.BlockType;
import model.types.CollisionType;
import model.types.MapType;

/**
 * A class to describe the level implemented.
 * It holds a complete description of all obstacles,
 * enemies, and other blocks inside the level.
 * @author Will
 *
 */
public class Level extends JPanel {
	private static final long serialVersionUID = 2390320300435663299L;
	
	//TEMPORARY DEBUGGING VARIABLE
	public static boolean AI = false;
	
	public int points = 0;
	public int SCROLL = 0;
	public int BLOCK_SIZE = 33;
	
	public BufferedImage background;
	public Block[][] blocks;
	public Block mario;
	
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
		case 'c': return Block.create(x,y,BlockType.COIN);
		case '*': return Block.create(x,y,BlockType.GROUND);
		case 'm': return mario = Block.create(x,y,BlockType.MARIO);
		default:
			System.out.println("Unrecognized block letter.");
			return Block.create(x,y,BlockType.BACKGROUND);
		}
	}
	
	/**
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 * @param g the graphics element to paint the board onto
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.setBackground(Color.WHITE);
		g.drawImage(background,0,0,null);
		drawBlocks(g);
		drawScore(g);
	}
	
	/**
	 * A helper function for drawing each of the blocks
	 * representing the elements of the level to the
	 * screen.
	 * @param g the graphics element for the level
	 */
	private void drawBlocks(Graphics g) {
		scroll();
		for (int i = blocks.length-1; i >= 0; i--) {
			for (int j = blocks[0].length-1; j >= 0; j--) {
				Vector<CollisionType> collisions = checkCollisions(i,j);
				MovingBlock n = blocks[i][j].isMovingBlock();
				if (n != null) 
					n.update(collisions);
				g.drawImage(blocks[i][j].sprite,
							blocks[i][j].x-SCROLL,
							blocks[i][j].y,null);
				
			}
		}
	}
	
	/**
	 * Scrolls the screen forwards if the
	 * main player sprite passes a certain point
	 * on the screen.
	 */
	public void scroll() {
		if (mario.x > BLOCK_SIZE*5) {
			SCROLL += mario.isMovingBlock().velocity.x;
		}
	}
	
	/**
	 * A helper function for drawing the display
	 * of the player's score for the current level.
	 * @param g the graphics element for the level
	 */
	private void drawScore(Graphics g) {
		g.setColor(Color.BLACK);
		g.setFont(new Font("TimesRoman", Font.BOLD, 18));
		g.drawString("Score: "+points, 472, 15);
		g.setColor(Color.WHITE);
		g.setFont(new Font("TimesRoman", Font.BOLD, 18));
		g.drawString("Score: "+points, 470, 15);
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
	public Vector<CollisionType> checkCollisions(int i, int j) {
		MovingBlock b = blocks[i][j].isMovingBlock();
		
		Vector<CollisionType> collisions = new Vector<CollisionType>();
		
		if (b == null) {
			collisions.add(CollisionType.NONE);
			return collisions;
		}
		
		Pair nearest = b.nearestBlock();
		int x = nearest.x;
		int y = nearest.y;
		
		collectibleCollide(x,y);
		directionalCollide(collisions,b,x,y);
		enemyCollide(collisions,b,x,y);
		
		return collisions;
	}
	
	/**
	 * A function that checks for a collision
	 * with any collectible items, such as coins.
	 * @param x the x coordinate of the current sprite
	 * @param y the y coordinate of the current sprite
	 */
	private void collectibleCollide(int x, int y) {
		if (inBounds(x,y) && blocks[x][y].type == BlockType.COIN) {
			Block b = blocks[x][y];
			blocks[x][y] = Block.create(b.x,b.y,BlockType.BACKGROUND);
			points += 100;
		}
	}
	
	/**
	 * A helper function that checks all four
	 * directional collisions at once for the 
	 * nearest block passed in.
	 * @param collisions the list of collisions to initialize
	 * @param b the block containing the sprite data
	 * @param x the x index of the closest block
	 * @param y the y index of the closest block
	 */
	private void directionalCollide(Vector<CollisionType> collisions, MovingBlock b, int x, int y) {
		if (inBounds(x,y+1) && b.collision(blocks[x][y+1])) {	
			collisions.add(CollisionType.BOTTOM);
			b.y = -b.sprite.getHeight() + blocks[x][y+1].y;
		}
		if (inBounds(x,y-1) && b.collision(blocks[x][y-1])) {		
			collisions.add(CollisionType.TOP);
			b.y = blocks[x][y-1].y + blocks[x][y-1].sprite.getHeight();
		}
		if (inBounds(x+1,y) && b.collision(blocks[x+1][y])) {	
			collisions.add(CollisionType.RIGHT);
			b.x = -b.sprite.getWidth() + blocks[x+1][y].x;
		}
		if (inBounds(x-1,y) && b.collision(blocks[x-1][y]))	{
			collisions.add(CollisionType.LEFT);
			b.x = blocks[x-1][y].x + blocks[x-1][y].sprite.getWidth();
		}
		if (collisions.size() == 0) {
			collisions.add(CollisionType.NONE);
		}
		
		if (b.type == BlockType.KOOPA) {
			System.out.println(collisions);
		}
	}
	
	/**
	 * A function to check collisions between
	 * the enemy units and mario.
	 * @param b the block of the friendly unit
	 * @param x the x index into the closest block
	 * @param y the y index into the closest block
	 * @param collisions the list of collisions with adjacent blocks
	 */
	private void enemyCollide(Vector<CollisionType> collisions,MovingBlock b,int x, int y) {
//		switch (b.type) {
//		case GOOMBA:
//		case KOOPA:
//			if (b.collision(mario)) {
//				points += 30;
//				System.out.println(collisions);
//			}
//			break;
//		default:
//		}
		if (collisions.contains(CollisionType.BOTTOM) 
				&& (blocks[x][y+1].type == BlockType.GOOMBA 
				||	blocks[x][y+1].type == BlockType.KOOPA)) {
			Block n = blocks[x][y+1];
			blocks[x][y+1] = Block.create(n.x, n.y, BlockType.BACKGROUND);
			b.jump();
			points += 30;
		}
		else if (collisions.contains(CollisionType.TOP) 
				&& (blocks[x][y-1].type == BlockType.MARIO)) {
			MovingBlock n = (MovingBlock)blocks[x][y+1];
			blocks[x][y+1] = Block.create(n.x, n.y, BlockType.BACKGROUND);
			n.jump();
			points += 30;
		}
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
}
