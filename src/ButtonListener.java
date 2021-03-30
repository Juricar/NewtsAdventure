import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * 
 * This class extends ActionListener and handles the two buttons in the Main Menu.
 * If the "New Game" button is pushed, the game begins and all the items on the Main Menu are removed.
 * If the "Quit" button is pressed, the window closes.
 * 
 * @author <Lauren Smiley>
 *
 */

public class ButtonListener implements ActionListener {
	public static final int DELAY = 20;
	private JFrame frame;
	private JPanel panel;
	private JLabel label;
	private int action;
	private JLabel logo;

	public ButtonListener(JFrame frame, JPanel p, JLabel label, int action, JLabel logo) {
		this.frame = frame;
		this.panel = p;
		this.label = label;
		this.action = action;
		this.logo = logo;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(this.action == 1) {
			startGame();
		}
		if(this.action == 2) {
			frame.setVisible(false);
			frame.dispose();
			System.exit(0);
		}
	}
	
	public void startGame() {
		this.frame.remove(this.panel);
		this.frame.remove(this.logo);
		this.frame.remove(this.label);
		GameComponent component = new GameComponent();
		this.frame.add(component);
		this.frame.addKeyListener(component.returnListener());
		
		GameListener gameListener = new GameListener(component, component.getHero());
		this.frame.addKeyListener(gameListener);
		
		Timer timer = new Timer(DELAY, gameListener);
		
		timer.start();
		
		this.frame.setVisible(true);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}

}
