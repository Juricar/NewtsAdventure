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
 * This class defines the walls around the Game Window. These are different from
 * the Floor Objects because these have collisions on all sides.
 * 
 * @author <Lauren Smiley, Antonio Juric, Elijah Williams>
 *
 */

public class Wall extends Floor {

	public Wall(int x, int y, Hero h) {
		super(x, y, h);

	}

	@Override
	public void drawOn(Graphics2D g2) {
		g2.setColor(Color.GRAY);
		Rectangle2D.Double tile = new Rectangle2D.Double(x, y, 50, 50);
		g2.fill(tile);
		
	
	}
	public Rectangle2D.Double getWall() {
		return new Rectangle2D.Double(x, y, 50, 50);
	}

}
