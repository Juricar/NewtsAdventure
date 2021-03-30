import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Scanner;

import javax.imageio.ImageIO;

/**
 * 
 * This class reads the Level.txt files, creates lists of the objects necessary to render them,
 * and returns those objects to the GameComponent. It also contains a reset function for the 
 * advancing/reverting of the levels.
 * 
 * @author <Lauren Smiley, Antonio Juric, Elijah Williams>
 *
 */

public class Level {
	private String name;
	private static ArrayList<Floor> floors = new ArrayList<>();
	private static ArrayList<Wall> walls = new ArrayList<>();
	private static ArrayList<Entity> enemies = new ArrayList<>();
	private static ArrayList<ShooterBoi> shooters = new ArrayList<>();
	private static ArrayList<Rectangle2D.Double> air = new ArrayList<>();

	public static void main(String[] args) {
		readLevel("Levels/Level1.txt");
	}
// this is actually be called in Component

	public Level(String name) {
		this.name = name;
	}

	public static Character[][] readLevel(String string) {
		Scanner scan = null;
		Character[][] level = new Character[11][12];
		try {
			scan = new Scanner(new File(string));

		} catch (FileNotFoundException e) {
			System.out.println("could not find file");
			e.printStackTrace();
			return null;
		}

		String title = scan.nextLine();
		while (scan.hasNextLine()) {
			for (int i = 0; i < 11; i++) {
				String line = scan.nextLine();
				for (int x = 0; x < line.length(); x++) {
					level[i][x] = line.charAt(x);
				}
			}
		}
		return level;
	}

	public void writeLevel(String string) {
		PrintWriter pw = null;

		try {
			pw = new PrintWriter(string);
		} catch (FileNotFoundException e) {
			System.out.println("Could not write at that location");
			e.printStackTrace();
			return;
		}
		pw.println("Level");
		pw.println("E____");
		pw.println("_____H");
		pw.println("XXXXXX");
		pw.close();

	}
	public static ArrayList<Floor> getFloors(Character[][] level) {
		return floors;
	}
	public static ArrayList<Wall> getWalls(Character[][] level) {
		return walls;
	}
	public static ArrayList<Entity> getEnemies(Character[][] level) {
		return enemies;
	}
	public static ArrayList<ShooterBoi> getShooters(Character[][] level) {
		return shooters;
	}
	public static ArrayList<Rectangle2D.Double> getAirBlocks(Character[][] level) {
		return air;
	}
		
	public static void resetAll() {
 		floors.clear();
		walls.clear();
		enemies.clear();
		shooters.clear();
		air.clear();
	}

	public static void renderLevel(Character[][] level, Graphics2D g2, Hero h) {
		
		for (int r = 0; r < 11; r++) {
			for (int c = 0; c < 12; c++) {
				if (level[r][c] == 'X') {
					Floor floor = new Floor(c * 50, r * 50, h);
					floors.add(floor);

				}
				if (level[r][c] == 'W') {
					Wall wall = new Wall(c * 50, r * 50, h);
					walls.add(wall);

				}
				if (level[r][c] == '_') {
					Rectangle2D.Double tile = new Rectangle2D.Double(c * 50 + 10, r * 50, 20, 50);
					air.add(tile);
					if (h.isGrounded(tile)) {
						h.falling(true);
						h.fall();
					}

				}
				if(level[r][c] == 'E' ) {
					if(Math.random() < .33) {
						ShooterBoi shooterMans = new ShooterBoi(c* 50, r*50+10);
						enemies.add(shooterMans);
						shooters.add(shooterMans);
					}
					else {
					Entity baddie = new MeleeEnemy(c * 50, r * 50 + 10);
					enemies.add(baddie);
					}
				}
			}
		}

	}
}
