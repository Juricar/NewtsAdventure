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
 * This class extends the Item class and defines its hitbox and what happens when 
 * it is collected by the Hero.
 * 
 * @author <Lauren Smiley, Antonio Juric, Elijah Williams>
 *
 */

public class Potion extends Item{
	
	private final static int POINT_VAL = 100;
	private Rectangle2D hitbox;
	private BufferedImage image;
	private BufferedImageOp observer;

	public Potion(double x, double y) {
		super(x, y);
		this.isDead = false;
		this.hitbox = new Rectangle2D.Double(this.x, this.y, 25, 25);
		try {
			image = ImageIO.read(new File("PotionItem.png"));
		} catch (IOException e) {
			throw new RuntimeException("Error drawing Potion: " + e);
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
	
	public Rectangle2D getHitBox() {
		return this.hitbox;
	}

	@Override
	public void move() {		
	}
	
	

}
