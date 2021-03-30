import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.Timer;

/**
 * 
 * This class creates the Hero object and extends the Entity class. It calls bubbles, 
 * resets, and tracks the Hero's lives.
 * 
 * @author <Lauren Smiley, Antonio Juric, Elijah Williams>
 *
 */

public class Hero extends Entity {
	BufferedImage image;
	private BufferedImageOp observer;
	private ArrayList<Bubble> bubbles;
	private Graphics2D g;

	public Hero(double x, double y) {
		super(x, y);
		bubbles = new ArrayList<>();
		try {
			image = ImageIO.read(new File("NewtDefault.png"));
		} catch (IOException e) {
			throw new RuntimeException("Error drawing Hero: " + e);
		}
		setLives(3);
	}

	public void resetHero() {
		this.x = 50;
		this.y = 50;
		this.bubbles = new ArrayList<>();

	}

	@Override
	public void drawOn(Graphics2D g) {
		this.g = g;
		g.drawImage(this.image, this.observer, (int) this.x, (int) this.y - 20);
	}
	
	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public void spawnBubble(int pressedKey) {
		Bubble b = new Bubble(this.x, this.y + 22.5, pressedKey);
		bubbles.add(b);

	}

	public ArrayList<Bubble> getBubbles() {
		return bubbles;
	}
	
	public void bubblePop(Bubble b) {
		this.bubbles.remove(b);
	}
	
	public int lives() {
		return this.lives;
	}
}