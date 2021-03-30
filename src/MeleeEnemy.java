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
 * This class creates/handles the Green enemies, which do not shoot.
 * 
 * @author <Lauren Smiley, Antonio Juric, Elijah Williams>
 *
 */

public class MeleeEnemy extends Entity {
	BufferedImage image;
	private BufferedImageOp observer;

	public MeleeEnemy(double x, double y) {
		super(x, y);
		this.setId(2);
		try {
			image = ImageIO.read(new File("FrankenSlime.png"));
		} catch (IOException e) {
			throw new RuntimeException("Error drawing Hero: " + e);
		}
		this.setVelocity(5);
		if (Math.random() > .6) {
			switchDirections();
		}
		setLives(1);
	}

	@Override
	public void drawOn(Graphics2D g) {

		g.drawImage(this.image, this.observer, (int) this.x, (int) this.y + 5);
		

	}
	// to randomize, make starting velocity a neg or a positive with math.random

}
