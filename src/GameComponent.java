import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * 
 * This class checks for/handles collisions, updates the game every 20 ms, and
 * contains the methods that advance/revert the current level.
 * 
 * @author <Lauren Smiley, Antonio Juric, Elijah Williams>
 *
 */

public class GameComponent extends JComponent {

	private Hero hero;
	private int numTicks;
	private Character[][] level;
	private int levelNum; 
	private HeroListener hl;
	private Graphics2D g2;
	private Integer score;
	private boolean gameWin = false;
	private boolean gameOver = false;
	private ArrayList<Floor> floors = new ArrayList<>();
	private ArrayList<Wall> walls = new ArrayList<>();
	private ArrayList<Entity> enemies = new ArrayList<>();
	private ArrayList<Bubble> bubbles = new ArrayList<>();
	private ArrayList<Rectangle2D.Double> air = new ArrayList<>();
	private ArrayList<Bubble> deadBubbles = new ArrayList<>();
	private ArrayList<Entity> deadEnemies = new ArrayList<>();
	private ArrayList<SlimeBall> slimeBalls = new ArrayList<>();
	private ArrayList<SlimeBall> deadSlimes = new ArrayList<>();
	private ArrayList<ShooterBoi> shooters = new ArrayList<>();
	private ArrayList<Item> items = new ArrayList<>();
	private ArrayList<Item> deadItems = new ArrayList<>();
	private ArrayList<CapturedEnemy> capturedEnemies = new ArrayList<>();

	private static final int GRAVITY = 3;

	public GameComponent() {
		this.score = 0;
		this.levelNum = 1;
		this.level = Level.readLevel("Levels/Level1.txt");
		this.hero = new Hero(50, 50);
		hl = new HeroListener(this.hero);
		setFocusable(true);
		Level.renderLevel(level, g2, hero);
		this.floors.addAll(Level.getFloors(this.level));
		this.walls.addAll(Level.getWalls(this.level));
		this.enemies.addAll(Level.getEnemies(this.level));
		this.shooters.addAll(Level.getShooters(this.level));
		this.air.addAll(Level.getAirBlocks(this.level));
		for (ShooterBoi y : shooters) {
			this.slimeBalls.addAll(y.getSlimes());
		}

	}

	/**
	 * Updates the game-state.
	 */
	public void updateState() {
		if (this.gameWin != true && this.gameOver != true) {
			updatePlayer();
			updateEnemy();
		}
		if (hero.lives() <= 0) {
			this.gameOver = true;
		}
		if (this.enemies.size() == 0 && this.items.size() == 0) {
			advanceLevel();
		}

		this.numTicks++;
	}

	@Override
	public void setFocusable(boolean b) {
		super.setFocusable(b);
	}

	/**
	 * updates screen
	 */
	public void drawScreen() {
		this.repaint();
	}

	public void updatePlayer() {
		hero.jump();
		hero.moveLeft();
		hero.moveRight();
		bubbles.addAll(hero.getBubbles());
		for (Bubble b : bubbles) {
			b.move();
			if (b.isDead() == true) {
				this.deadBubbles.add(b);
				hero.bubblePop(b);
			}
			for (Entity e : enemies) {
				if (b.getHitBox().intersects(e.hitBox())) {
					if (e.isDead != true) {
						CapturedEnemy filled = new CapturedEnemy(e.x, e.y, e.getId());
						this.items.add(filled);
						this.capturedEnemies.add(filled);
						e.die();
						deadEnemies.add(e);
						hero.bubblePop(b);
					}
				}
			}
		}

		for (CapturedEnemy c : this.capturedEnemies) {
			if (c.isPopped() == true) {
				if (c.getTrappedId() == 1) {
					this.enemies.add(new ShooterBoi(c.x, c.y + 30));
				} else {
					this.enemies.add(new MeleeEnemy(c.x, c.y + 30));
				}
				c.isDead = true;
			}
		}
		for (Entity e : enemies) {
			if (e.hitBox().intersects(hero.hitBox())) {
				hero.die();
				hero.resetHero();
			}
		}
		for (SlimeBall s : slimeBalls) {
			if (hero.hitBox().intersects(s.hitBox())) {
				hero.die();
				hero.resetHero();
			}
		}

		for (Bubble u : deadBubbles) {
			this.bubbles.remove(u);
		}

		if (Math.random() > .995 && this.items.size() < 1) {
			this.items.add(new Potion(300, 275));
		}

		for (Item f : this.items) {
			f.move();
			if (this.hero.hitBox().intersects(f.getHitBox())) {
				if (f.isDead != true) {
					this.score += f.collect();
				}
			}
			if (f.isDead == true) {
				this.deadItems.add(f);
			}
		}

		for (Item df : this.deadItems) {
			this.items.remove(df);
			this.capturedEnemies.remove(df);
		}

		for (Wall w : walls) {
			if (hero.hitLeft(w.getWall())) {
				hero.setRight(true);
				hero.moveRight();
				hero.setRight(false);

			}
			if (hero.hitRight(w.getWall())) {
				hero.setLeft(true);
				hero.moveLeft();
				hero.setLeft(false);
			}
			if (hero.hitTop(w.getWall())) {
				hero.setJumping(false);

			}
		}
		for (Rectangle2D.Double a : air) {
			if (hero.isGrounded(a)) {
				if (hero.isJumping() == false) {
					hero.falling(true);
				}
			}
		}
		this.deadBubbles = new ArrayList<>();
		this.deadItems = new ArrayList<>();

	}
	/**
	 * Updates enemy's collisions and location
	 */
	public void updateEnemy() {
		for (Entity e : enemies) {
			e.jump();

			e.move();
			if (Math.random() >= .991) {
				System.out.println("enemy jump!");
				if (e.grounded) {
					e.setJumping(true);
				}

			}
		}
		for (SlimeBall b : slimeBalls) {
			b.move();
			if (b.isDead() == true) {
				this.deadSlimes.add(b);
			}
		}
		for (ShooterBoi y : shooters) {
			if (Math.random() > .991) {
				y.shoot();
				slimeBalls.addAll(y.getSlimes());
			}
		}
		for (SlimeBall u : deadSlimes) {
			this.slimeBalls.remove(u);
			for (ShooterBoi y : shooters) {
				y.getSlimes().clear();
			}

		}

		for (Wall w : walls) {
			for (Entity e : enemies) {
				if (e.hitLeft(w.getWall())) {
					e.switchDirections();
					return;

				}
				if (e.hitRight(w.getWall())) {
					e.switchDirections();
					return;
				}
				if (e.hitTop(w.getWall())) {
					e.setJumping(false);
					return;

				}
			}
		}
		for (Entity e : enemies) {
			for (Rectangle2D.Double r : air) {
				if (e.isGrounded(r)) {
					if (e.isJumping() == false) {
						e.falling(true);
						e.fall();

					}
				}
			}
		}
		for (Entity e : enemies) {
			for (Floor f : floors) {
				if (e.isGrounded(f.getTop())) {
					System.out.println("y");
					e.onGround(true);
					e.falling(false);

//					 e.stopFalling();
				}

			}
		}
		for (Entity e : deadEnemies) {
			enemies.remove(e);
		}

		this.deadEnemies = new ArrayList<>();
		this.deadSlimes = new ArrayList<>();

	}

	public Hero getHero() {
		return this.hero;
	}
	/**
	 * 
	 * advances the level when "U" pressed
	 * 
	 */
	public void advanceLevel() {
		if (this.levelNum == 5) {
			this.endGame();
			return;
		}
		this.levelNum++;
		this.level = Level.readLevel("Levels/Level" + this.levelNum + ".txt");
		this.floors.removeAll(this.floors);
		this.enemies.clear();
		this.air.clear();
		this.slimeBalls.clear();
		this.walls.clear();
		Level.resetAll();
		this.repaint();
		Level.renderLevel(level, g2, hero);
		this.floors.addAll(Level.getFloors(this.level));
		this.enemies.addAll(Level.getEnemies(level));
		this.air.addAll(Level.getAirBlocks(level));
		this.walls.addAll(Level.getWalls(level));
		hero.resetHero();
		this.repaint();
	}
	/**
	 * 
	 * reverts the level backwards one when "D" pressed
	 */
	public void revertLevel() {
		if (this.levelNum == 1) {
			System.out.println("You are at the Lowest Level!");
			return;
		}
		this.levelNum -= 1;
		this.level = Level.readLevel("Levels/Level" + this.levelNum + ".txt");
		this.enemies.clear();
		this.air.clear();
		this.walls.clear();
		this.floors.clear();
		this.slimeBalls.clear();
		Level.resetAll();
		this.repaint();
		Level.renderLevel(this.level, g2, this.hero);
		this.floors.addAll(Level.getFloors(this.level));
		this.enemies.addAll(Level.getEnemies(level));
		this.air.addAll(Level.getAirBlocks(level));
		this.walls.addAll(Level.getWalls(level));
		hero.resetHero();

		this.repaint();
	}

	public void endGame() {
		this.gameWin = true;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.g2 = (Graphics2D) g;
		if (this.gameWin == true) {
			g2.setColor(Color.WHITE);
			g2.fillRect(0, 0, 1000, 1000);
			g2.setColor(Color.BLACK);
			g2.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
			g2.drawString("You Win!!!", 240, 250);
			g2.drawString("Your Score was: " + this.score.toString(), 150, 300);
			return;
		}
		if (this.gameOver == true) {
			g2.setColor(Color.WHITE);
			g2.fillRect(0, 0, 1000, 1000);
			g2.setColor(Color.RED);
			g2.setFont(new Font("Comic Sans MS", Font.PLAIN, 30));
			g2.drawString("Oh no! You've Died!", 150, 200);
			g2.drawString("Your Score was: " + this.score.toString(), 150, 300);
			return;
		}
		hero.drawOn(g2);
		
		for (Bubble b : bubbles) {
			b.drawOn(g2);
		}
		for (SlimeBall s : this.slimeBalls) {
			s.drawOn(g2);
		}
		for (Entity e : enemies) {
			e.drawOn(g2);
		}
		for (Floor f : floors) {
			f.drawOn(g2);
		}
		for (Wall w : walls) {
			w.drawOn(g2);
		}
		for (Item f : this.items) {
			f.drawOn(g2);
		}
		
		g2.setColor(Color.BLACK);
		g2.setFont(new Font("Comic Sans MS", Font.PLAIN, 30));
		g2.drawString("Score : " + this.score.toString(), 400, 30);
		g2.setColor(Color.GRAY);

	}

	public HeroListener returnListener() {
		return hl;
	}

}
