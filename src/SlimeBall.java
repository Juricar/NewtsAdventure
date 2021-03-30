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
 * This class handles the balls shot by the red slimes. These are different from the 
 * bubble class because they're created randomly and are slightly slower.
 * 
 * @author <Lauren Smiley, Antonio Juric, Elijah Williams>
 *
 */

public class SlimeBall {
	private double x;
	private double y;
	private double traveled;
	private double velocity = 18;
	private boolean isDead;
	private BufferedImage image;
	private BufferedImageOp observer;

	SlimeBall(double x, double y) {
		this.x = x;
		this.y = y;
		try {
			image = ImageIO.read(new File("SlimeBall.png"));
		} catch (IOException e) {
			throw new RuntimeException("Error drawing SlimeBall: " + e);
		}
	}

	public void drawOn(Graphics2D g) {
		g.drawImage(this.image, this.observer, (int) this.x, (int) this.y - 20);
	}

	public void move() {
		if (this.traveled < 200) { // when "if" replaced with "while", the bubble renders in the spot 100 from
									// spawn
			this.traveled++;
			this.x += this.velocity;

		}
		if (this.traveled >= 200) {
			this.x += 0;
			this.die();
			this.traveled = 0;
		}
		if (this.x <= 0) {
			this.x += 0;
			this.die();
			this.traveled = 0;
		}

	}

	public void die() {
		System.out.println("slime is dead");
		this.isDead = true;
	}

	public boolean isDead() {
		return this.isDead;
	}

	public void setVelocity(double v) {
		this.velocity = v;
	}
	public Rectangle2D.Double hitBox() {
		return new Rectangle2D.Double(this.x, this.y, 30, 30);
	}
}
