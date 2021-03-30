import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

/**
 * 
 * This abstract class defines the necessary methods and fields for an Item object.
 * 
 * @author <Lauren Smiley, Antonio Juric, Elijah Williams>
 *
 */

public abstract class Item {
	
	protected double x;
	protected double y;
	protected boolean isDead;
	
	public Item(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public abstract void drawOn(Graphics2D g);
	
	public abstract int collect();
	
	public abstract void move();
	
	public abstract Rectangle2D getHitBox();
	
	

}
