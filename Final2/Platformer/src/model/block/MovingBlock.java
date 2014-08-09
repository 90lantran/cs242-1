package model.block;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

import javax.imageio.ImageIO;

import model.Level;
import model.Pair;
import model.types.BlockType;
import model.types.CollisionType;

/**
 * A class to describe blocks that are able
 * to move, such as the player character sprite,
 * and the enemies.
 * @author Will
 *
 */
public class MovingBlock extends Block {
	static final int GRAVITY_DEFAULT = 5;
	static int GRAVITY = GRAVITY_DEFAULT;
	// TODO: static double FRICTION = 0.99;
	static int MAX_X_VELOCITY = 5;
	public Pair velocity;
	public boolean jump = false;
	
	public MovingBlock() {}
	
	private void copy(Block other) {
		x = other.x;
		y = other.y;
		type = other.type;
		spriteSheet = other.spriteSheet;
		sprite = other.sprite;
		solid = other.solid;
	}
	
	/**
	 * A constructor for a block.
	 * @param x the x position of the block
	 * @param y the y position of the block
	 * @param type the type of block
	 * @param sprite the sprite sheet filename
	 */
	public MovingBlock(int x, int y, BlockType type, String sprite) {
		this.x = x;
		this.y = y;
		this.type = type;
		this.velocity = new Pair(0,0);
		
		try {
			this.sprite = ImageIO.read(new File(sprite));
		} catch (IOException e) {
			System.out.println("Sprite file for '" + String.valueOf(type) + 
							   "' not found at: '" + sprite + "' .");
		}
	}
	
	/**
	 * A function that will move the block in
	 * the specified directions (x,y) by 
	 * increasing the velocity by that amount.
	 * @param key the key representing the key pressed
	 */
	public void move(int key) {
		int vx = 0,incr = 10;
		
		switch (key){ 
		case KeyEvent.VK_LEFT:
			vx = velocity.x - incr;
			break;
		case KeyEvent.VK_RIGHT:
			vx = velocity.y + incr;
			break;
		}
		
		velocity.x = limit(vx,MAX_X_VELOCITY);
	}
	
	/**
	 * Limits the velocity from
	 * being greater than max in
	 * either the positive or negative 
	 * direction.
	 * @param v the velocity to limit
	 * @return the limited velocity value
	 */
	public int limit(int v, int max) {
		if (v > max) v = max;
		if (v < -max) v = -max;
		return v;
	}
	
	public void gravity() {
		if (velocity.y < 2) {
			GRAVITY += 3;
			velocity.y += GRAVITY;
		}
	}
	
	public void jump() {
		if (jump == false) {
			jump = true;
			velocity.y = -100;
		}
	}
	
	public void die() {
		Block empty = Block.create(x, y, BlockType.BACKGROUND);
		copy(empty);
	}
	
	/**
	 * A function to update the position of the
	 * moving block as well as check for collisions
	 * in the process
	 * @param collisions a list of collisions currently occurring
	 */
	public void update(Vector<CollisionType> collisions) {
		for (int i = 0; i < collisions.size(); i++) {
			CollisionType collision = collisions.elementAt(i);
			
			switch(collision) {
			case BOTTOM:
				if (velocity.y > 0) {
					velocity.y = 0;
					jump = false;
					GRAVITY = GRAVITY_DEFAULT;
				}
				break;
			case TOP:
				if (velocity.y < 0)
					velocity.y = 0;
				break;
			case LEFT:
				if (velocity.x < 0)
					velocity.x = 0;
				break;
			case RIGHT:
				if (velocity.x > 0)
					velocity.x = 0;
				break;
			case NONE:
				gravity();
				break;
			default: break;
			}
		}
		
		if (Level.AI)
		enemyAI(collisions);
		
		x += velocity.x;
		y += velocity.y;
	}
	
	/** 
	 * A function that updates the enemy positions
	 * in a very simple way to emulate the impression
	 * of artificial intelligence.
	 * @param collisions
	 */
	public void enemyAI(Vector<CollisionType> collisions) {
		switch (type){ 
		case GOOMBA:
		case KOOPA:
			if (collisions.contains(CollisionType.LEFT)) {
				if (velocity.x == 0)
					flipSprite();
				velocity.x = 2;
			}
			else if (collisions.contains(CollisionType.RIGHT)) {
				if (velocity.x == 0)
					flipSprite();
				velocity.x = -2;
			}
			else if (velocity.x == 0) {
				velocity.x = -2;
			}
			break;
		default:
		}
	}
}
