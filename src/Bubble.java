import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * 
 * This is our Bubble class. It creates the Bubble and defines its motion, hitbox, and popping.
 * 
 * @author <Lauren Smiley, Antonio Juric, Elijah Williams>
 *
 */

public class Bubble {
	private int velocity = 7;
	private boolean containsEnemy;
	private BufferedImage image;
	private BufferedImageOp observer;
	private double x;
	private double y;
	private int pressedKey;
	private int traveled = 0;
	private boolean isDead;

	public Bubble(double x, double y, int pressedKey2) {
		this.x = x;
		this.y = y;
		this.containsEnemy = false;
		try {
			image = ImageIO.read(new File("Bubble.png"));
		} catch (IOException e) {
			throw new RuntimeException("Error drawing Bubble: " + e);
		}
		this.pressedKey = pressedKey2;
	}

	public void drawOn(Graphics2D g) {
		g.drawImage(this.image, this.observer, (int) this.x, (int) this.y - 20);
	}

	public void die() {
		this.isDead = true;
	}

	public void atTop() {

	}

	public Rectangle2D getHitBox() {
		return new Rectangle2D.Double(this.x, this.y, 32, 32); 
	}

	public boolean collides() {
		return false;
	}

	public void move() {
		if (this.pressedKey == KeyEvent.VK_LEFT) { 
			if (this.traveled != 100) {
				this.traveled++;
				this.x--;
			}
			if (this.traveled >= 100) {
				this.x += 0;
				this.die();
				this.traveled = 0;
				return;
			}

		}

		if (this.pressedKey == KeyEvent.VK_RIGHT) {
			if (this.traveled != 100) {
				this.traveled++;
				this.x++;
			}
			if (this.traveled >= 100) {
				this.die();
				this.x += 0;
				this.traveled = 0;
				return;

			}
		}

	}

	public boolean isDead() {
		return this.isDead;
	}
}
