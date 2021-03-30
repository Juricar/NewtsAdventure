import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

/**
 * 
 * This class handles Hero and the Enemies' motion, falling, and collisions.
 * 
 * @author <Lauren Smiley, Antonio Juric, Elijah Williams>
 *
 */

import javax.swing.Timer;

public abstract class Entity {
	protected double y;
	protected double x;
	private int velocity = 8;
	protected boolean grounded = true;
	private int airTime = 0;
	private boolean isJumping = false;
	protected boolean isFalling = false;
	protected int lives;
	private boolean left = false;
	private boolean right = false;
	protected boolean isDead;
	protected int id;

	public Entity(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public void setVelocity(int v) {
		this.velocity = v;
	}

	// check velocity
	public void moveRight() {
		if (right == true) {
			this.x += velocity;
		} else {
			return;
		}
	}

	public void setLeft(boolean b) {
		left = b;
	}

	public void setRight(boolean b) {
		right = b;
	}

	public void moveLeft() {
		if (left == true) {
			this.x -= velocity;
		} else {
			return;
		}
	}

	public void switchDirections() {
		this.velocity = -this.velocity;
	}

	// jumping-related

	public void fall() {
		if (isFalling == false) {
			y += 0;
		}
		if (grounded == true) {
			y += 0;
		}
		if (isFalling == true) {
			y += 2.5;
			airTime++;
			return;
		}
	}

	public void falling(boolean b) {
		isFalling = b;
		this.fall();

	}

	public void stopFalling() {
		y += 0;
	}

	public void jump() {
		if (isJumping) {
			this.grounded = false;
			this.y -= 2.5;
			airTime++;
		}

		if (airTime == 50) {
			isJumping = false;
		}
		if (airTime != 0 && isJumping == false) {
			airTime -= 3;
			this.y += 2.5;
			if (this.grounded) {
				airTime = 0;
				y += 0;
			}
		}

	}

	public int getAirTime() {
		return airTime;
	}

	public boolean isJumping() {
		return isJumping;
	}

	public void setJumping(boolean b) {
		isJumping = b;
	}

	public void onGround(boolean b) {
		this.grounded = b;

	}

	// returns hitbox

	public Rectangle2D.Double hitBox() {
		return new Rectangle2D.Double(this.x - 10, this.y - 5, 65, 50);
	}

	// initializes hitboxes

	public Rectangle2D.Double bottom() {
		return new Rectangle2D.Double(this.x + 5, this.y + 45, 35, 6);
	}

	public Rectangle2D.Double hitBoxLeft() {
		return new Rectangle2D.Double(this.x - 5, this.y + 10, 45, 40);
	}

	public Rectangle2D.Double hitBoxRight() {
		return new Rectangle2D.Double(this.x + 45, this.y + 10, 5, 45);
	}

	public Rectangle2D.Double hitBoxUp() {
		return new Rectangle2D.Double(this.x, this.y - 5, 45, 40);
	}

	// collisions

	public boolean isGrounded(Rectangle2D.Double r) {
		return r.intersects(bottom());
	}

	public boolean hitTop(Rectangle2D.Double r) {
		return r.intersects(hitBoxUp());
	}

	public boolean hitRight(Rectangle2D.Double r) {
		return r.intersects(hitBoxRight());
	}

	public boolean hitLeft(Rectangle2D.Double r) {
		return r.intersects(hitBoxLeft());
	}

	public void move() {
		this.x += velocity;

		return;

	}

	public void die() {
		System.out.println("Dead");
		this.lives--;
		if (this.lives == 0) {
			this.isDead = true;
		}

	}

	public void setLives(int l) {
		this.lives = l;
	}

	public abstract void drawOn(Graphics2D g);

	/**
	 * Used to set an id for each type of Monster. Shooters are 1, and Melee are 2.
	 * 
	 */
	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

}
