import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

/**
 * 
 * This class deals with the red enemy, which shoots at the Hero based on its location
 * in relation to the enemy.
 * 
 * @author <Lauren Smiley, Antonio Juric, Elijah Williams>
 *
 */

public class ShooterBoi extends Entity {
	BufferedImage image;
	private BufferedImageOp observer;
	private ArrayList<SlimeBall> slimeBalls = new ArrayList<>();
	private boolean heroPos = false;
	private int running = 0;

	public ShooterBoi(double x, double y) {
		super(x, y);
		this.setId(1);
		BufferedImage img = null;
		try {
			image = ImageIO.read(new File("SpladRed.png"));
		} catch (IOException e) {
			throw new RuntimeException("Error drawing Hero: " + e);
		}
		this.setVelocity(5);
		if (Math.random() > .6) {
			switchDirections();
		}
		this.shoot();
		setLives(1);
	}

	@Override
	public void drawOn(Graphics2D g) {
		g.drawImage(this.image, this.observer, (int) this.x, (int) this.y + 5);
	}
	
	public void slimePop(SlimeBall s) {
		this.slimeBalls.remove(s);
	}

	public void heroLocation(boolean b) {
		heroPos = b;
	}

	public void shoot() {
		SlimeBall slime = new SlimeBall(this.x, this.y + 22.5);
		slimeBalls.add(slime);
		if (heroPos == true) {
			slime.setVelocity(.5);
		} else if (heroPos == false) {
			slime.setVelocity(-.5);
		}
	}
	
	public ArrayList<SlimeBall> getSlimes(){
		return slimeBalls;
	}
}
