import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * 
 * This class handles the Hero-specific keys. These keys shoot bubbles, move left
 * and right, and jump. Also, when the left or right are pressed, the Hero's graphic 
 * turns in that direction.
 * 
 * @author <Lauren Smiley, Antonio Juric, Elijah Williams>
 *
 */

public class HeroListener implements KeyListener {
	private Hero h;
	private int lastPressed = KeyEvent.VK_RIGHT;

	public HeroListener(Hero hero) {
		this.h = hero;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			h.setLeft(true);
			lastPressed = KeyEvent.VK_LEFT;
			BufferedImage image = null;
			try {
				image = ImageIO.read(new File("NewtFacingLeft.png"));
			} catch (IOException e1) {
				throw new RuntimeException("Error drawing Hero: " + e1);
			}
			h.setImage(image);
			if (e.getKeyCode() == KeyEvent.VK_UP) {
				h.setJumping(true);
			}
			if (e.getKeyCode() == KeyEvent.VK_UP) {
				h.setJumping(true);
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			h.setRight(true);
			lastPressed = KeyEvent.VK_RIGHT;
			BufferedImage image = null;
			try {
				image = ImageIO.read(new File("NewtDefault.png"));
			} catch (IOException e1) {
				throw new RuntimeException("Error drawing Hero: " + e1);
			}
			h.setImage(image);
			if (e.getKeyCode() == KeyEvent.VK_UP) {
				h.setJumping(true);
			}
		}

		if (e.getKeyCode() == KeyEvent.VK_UP) {
			h.setJumping(true);
			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				h.setLeft(true);
			}
			if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				h.setRight(true);
				System.out.println("Go Right");
			}

		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			h.setLeft(false);
			
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			h.setRight(false);
			

		}
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			h.spawnBubble(this.lastPressed);
		}

	}

	@Override
	public void keyTyped(KeyEvent e) {

	}
}