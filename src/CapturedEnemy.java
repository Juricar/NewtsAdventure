import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * 
 * This class handles the motion of the captured slimes to the top of the screen and 
 * having them "pop" out, which just creates a new enemy object after a certain amount of time.
 * 
 * @author <Lauren Smiley, Antonio Juric>
 *
 */

public class CapturedEnemy extends Item {

	public static final int POINT_VAL = 500;
	private int enemyId;
	private int timeTrapped;
	private boolean popped = false;
	private BufferedImage image;
	private BufferedImageOp observer;

	public CapturedEnemy(double x, double y, int enemyId) {
		super(x, y);
		this.enemyId = enemyId;
		this.timeTrapped = 0;
		try {
			image = ImageIO.read(new File("Contained.png"));
		} catch (IOException e) {
			throw new RuntimeException("Error drawing CapturedEnemy: " + e);
		}

	}

	@Override
	public void drawOn(Graphics2D g) {
		g.drawImage(this.image, this.observer, (int) this.x, (int) this.y - 20);

	}

	@Override
	public int collect() {
		this.isDead = true;
		return POINT_VAL;
	}

	@Override
	public void move() {
		if (this.y > 50) {
			this.y -= 1;
		}
		
		if (this.y <= 50) {
			timeTrapped++;
			if (timeTrapped > 400) {
				this.popped = true;
			}
		}
	}

	@Override
	public Rectangle2D getHitBox() {
		return new Rectangle2D.Double(this.x, this.y, 50, 50);
	}

	public int getTrappedId() {
		return this.enemyId;
	}

	public boolean isDead() {
		return this.popped;
	}

	public boolean isPopped() {
		return this.popped;
	}

}
