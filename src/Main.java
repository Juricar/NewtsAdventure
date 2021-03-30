import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;


/**
 * This is our Main Method. The frame for our game is created here and the image, buttons,
 * and label for the Main Menu are also instantiated here.
 * @param args
 */
public class Main {
	
	public static final int DELAY = 20;

	
	public static void main(String[] args) {
		new Main();
	}
	
	public Main() {
		
		JFrame frame = new JFrame("Newt's Adventure");
		frame.setSize((50*12 + 16), (50*11 + 39));
		frame.setFocusable(true);
		
		JButton buttonA = new JButton("New Game");
		JButton buttonB = new JButton("Quit");
		JLabel label = new JLabel("A Re-Imagination of Bubble Bobble!");
		label.setFont(new Font("Comic Sans MS", Font.ITALIC,35));
		
		
		
		BufferedImage image = null;
		
		try {
			image = ImageIO.read(new File("Logo.png"));
		} catch (IOException e) {
			throw new RuntimeException("Error drawing Logo: " + e);
		}
		JLabel logo = new JLabel(new ImageIcon(image));
		frame.setIconImage(image);
		
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		panel.add(buttonA, BorderLayout.EAST);
		panel.add(buttonB, BorderLayout.EAST);
		//panel.image
		
		ActionListener a = new ButtonListener(frame, panel, label, 1, logo);
		buttonA.addActionListener(a);
		
		ActionListener b = new ButtonListener(frame, panel, label, 2, logo);
		buttonB.addActionListener(b);
		
		frame.add(label, BorderLayout.SOUTH);
		frame.add(logo, BorderLayout.WEST);
		frame.add(panel);
		
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}

}


