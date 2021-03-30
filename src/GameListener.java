import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.Timer;

/**
 * 
 * This class handles the U and D keys to update the current level, as well as updating the game.
 * 
 * @author <Lauren Smiley, Antonio Juric, Elijah Williams>
 *
 */

public class GameListener implements ActionListener, KeyListener {
	private Hero h;
	private GameComponent component;

	public GameListener(GameComponent component, Hero hero) {
		this.component = component;
		this.h = hero;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		gameAdvance();
	}

	public void gameAdvance() {
		this.component.updateState();
		this.component.drawScreen();
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		if (arg0.getKeyCode() == KeyEvent.VK_U) {
			this.component.advanceLevel();
			System.out.println("Calling advanceLevel()");
		}
		if (arg0.getKeyCode() == KeyEvent.VK_D) {
			this.component.revertLevel();
			System.out.println("Calling revertLevel()");
		}

	}

	@Override
	public void keyReleased(KeyEvent arg0) {

	}

	@Override
	public void keyTyped(KeyEvent arg0) {

	}

}
