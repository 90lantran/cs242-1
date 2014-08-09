package model.block;

import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

import javax.imageio.ImageIO;

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

	static int MAX_X_VELOCITY = 6;
	
	public Pair velocity;
	public int preX,preY;
	public boolean jump = false;
	public int walking = 0;
	public int xTravel = 0;
	
	public MovingBlock() {}
	
	/**
	 * A constructor for a block.
	 * @param x the x position of the block
	 * @param y the y position of the block
	 * @param type the type of block
	 * @param sprite the sprite sheet filename
	 */
	public MovingBlock(int x, int y, BlockType type, String sprite) {
		this.preX = x;
		this.preY = y;
		this.x = x;
		this.y = y;
		this.type = type;
		this.velocity = new Pair(0,0);
		
		try {
			this.spriteSheet = ImageIO.read(new File(sprite));
		} catch (IOException e) {
			System.out.println("Sprite file for '" + String.valueOf(type) + 
							   "' not found at: '" + sprite + "' .");
		}
		
		switch (type) {
		default:
		case GOOMBA:
			this.sprite = spriteSheet;
			break;
		case KOOPA:
			this.sprite = spriteSheet.getSubimage(0,0,33,33);
			break;
		case MARIO:
			this.sprite = spriteSheet.getSubimage(0,0,20,32);
			break;
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
			velocity.y = -55;
		}
	}
	
	/**
	 * A function to update the position of the
	 * moving block as well as check for collisions
	 * in the process
	 * @param collisions a list of collisions currently occurring
	 */
	public void update(Vector<CollisionType> collisions) {
		collide(collisions);
		
		enemyAI(collisions);
		
		preX = x;
		preY = y;
		x += velocity.x;
		y += velocity.y;
		
		xTravel += x - preX;
	
		animate();
	}
	
	/**
	 * A function that updates the sprite velocity
	 * if there are collisions in any given direction.
	 * @param collisions the list of collisions
	 */
	private void collide(Vector<CollisionType> collisions) {
		for (int i = 0; i < collisions.size(); i++) {
			CollisionType collision = collisions.elementAt(i);
			
			switch(collision) {
			case BOTTOM:
				if (velocity.y >= 0) {
					velocity.y = 0;
				}
					jump = false;
					GRAVITY = GRAVITY_DEFAULT;
				
				break;
			case TOP:
				if (velocity.y < 0)
					velocity.y = 0;
				gravity();
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
	}
	
	/**
	 * Animates the sprite sheet for the given
	 * block based on current position and the
	 * previous position.
	 */
	private void animate() {
		switch (type) {
		case GOOMBA:
			if (Math.abs(xTravel) % 20 == 0 && x - preX != 0) flipSprite();
			break;
		case KOOPA:
		default:
			if (Math.abs(xTravel) % 20 == 0 && x - preX != 0) {
				walking = (walking + 1) % 2;
			}
			sprite = spriteSheet.getSubimage(walking*33,0,33,33);
			if (x - preX > 0) flipSprite();
			break;
		case MARIO:
			if (preY != y) sprite = spriteSheet.getSubimage(68,0,20,32);
			else {
				if (xTravel > 0 && Math.abs(xTravel) % 10 == 0 && x - preX != 0) {
					walking = (walking + 1) % 3; 
				}
				sprite = spriteSheet.getSubimage((int)(walking*22.5),0,20,32);
			}
			if (x - preX < 0) flipSprite();
			break;
		}
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
			break;
		}
	}
	
	/**
	 * A function that will vertically flip the
	 * sprite for when the sprite changes 
	 * directions.
	 */
	public void flipSprite() {
		AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
		tx.translate(-sprite.getWidth(null), 0);
		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
		sprite = op.filter(sprite, null);
	}
}
