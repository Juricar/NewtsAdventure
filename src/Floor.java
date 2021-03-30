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
 * This class created the Floor objects in the game window. It returns its hitbox to help with collisions.
 * 
 * @author <Lauren Smiley, Antonio Juric, Elijah Williams>
 *
 */

public class Floor {
	protected int x;
	protected int y;
	protected Hero h;
	BufferedImage image;
	private BufferedImageOp observer;

	
	public Floor(int x, int y, Hero h) {
		this.x = x;
		this.y = y;
		this.h = h;
		try {
			image = ImageIO.read(new File("Wall.png"));
		} catch (IOException e) {
			throw new RuntimeException("Error drawing Wall: " + e);
		}
	}
	public void drawOn(Graphics2D g2) {
		g2.setColor(Color.GRAY);
		Rectangle2D.Double tile = 
				new Rectangle2D.Double(this.x,this.y, 50, 50);
		g2.fill(tile);
		Rectangle2D.Double top = new Rectangle2D.Double(this.x + 3, this.y + 3, 50, 3);
		if(h.isGrounded(top)) {
			h.onGround(true);
			h.falling(false);
			}
		g2.drawImage(this.image, this.observer, (int) this.x, (int) this.y);
		
	}
	public Rectangle2D.Double getFloor(){
		return new Rectangle2D.Double(this.x,this.y, 50, 50);
	}
	public Rectangle2D.Double getTop() {
		return new Rectangle2D.Double(this.x + 3, this.y-3, 47, 3);
	}
	

}
