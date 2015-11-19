package view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

import model.Game;
import model.Map;
import model.Tile;
import model.buildings.*;
import model.agents.*;
import model.resources.*;

public class ViewController extends JPanel implements Observer {
	private Game game;
	private Map map;
	private Image agent1, agent2, building1, building2, oil, solar, ground;
	private int x;
	private int y;
	private Timer timer = new Timer(50, new TimerListener());
	private int tic = 0;
	private ChargingStation charge = new ChargingStation("Charge", 1000, new Point(10, 5));
	private OilTank oilTank = new OilTank("Oil", 1000, new Point(10, 4));
	private Resource electric = new Resource(20, new Point(0, 0), ResourceType.ELECTRICITY);
	private Resource oils = new Resource(20, new Point(0, 2), ResourceType.OIL);
	private SoldierAgent firstAgent = new SoldierAgent(new Point(11,4));
	private WorkerAgent secondAgent = new WorkerAgent(new Point(6, 6));
	private boolean isAnimating = false;

	public ViewController() {
		this.setVisible(true);
	}

	public ViewController(Game game) {
		this.game = game;
		try {
			agent1 = ImageIO.read(new File("./ImageSet/agent1.png"));
			agent2 = ImageIO.read(new File("./ImageSet/agent2.png"));
			building1 = ImageIO.read(new File("./ImageSet/building1.png"));
			building2 = ImageIO.read(new File("./ImageSet/building2.png"));
			oil = ImageIO.read(new File("./ImageSet/oil.png"));
			solar = ImageIO.read(new File("./ImageSet/solar.png"));
			ground = ImageIO.read(new File("./ImageSet/0.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		repaint();
	}

	private class TimerListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			isAnimating = true;
			tic++;
			if (secondAgent.getPosition().x == secondAgent.getDestination().x &&
					secondAgent.getPosition().y == secondAgent.getDestination().y) {
				timer.stop();
				if (secondAgent.getDestination().x == electric.getLocation().x &&
						secondAgent.getDestination().y == electric.getLocation().y)
					secondAgent.setDestination(charge.getLocation());
				else if (secondAgent.getDestination().x == charge.getLocation().x &&
						secondAgent.getDestination().y == charge.getLocation().y)
					secondAgent.setDestination(electric.getLocation());
				repaint();
				//return;
			}
			else
				secondAgent.move();
			repaint();
		}
	}
	
	public boolean isAnimating() {
		return isAnimating;
	}

	public void paintComponent(Graphics g) {
		// draws each tile, based on map representation, getting image from the
		// Tile enum
		Graphics g2 = (Graphics2D) g;
		map = game.getMap();
		game.addBuilding(charge, new Point(0, 0));
		game.addBuilding(oilTank, new Point(0, 2));
		int agentX = secondAgent.getPosition().x;
		int agentY = secondAgent.getPosition().y;
		for (int i = 0; i < map.getXLength(); i++) {
			for (int j = 0; j < map.getYLength(); j++) {
				if (map.get(i, j) == 0) {
					g2.drawImage(ground, i * 50, j * 50, null);
				} else if (map.get(i, j) == 1) {
					g2.drawImage(ground, i * 50, j * 50, null);
				} else {
					g2.drawImage(ground, i * 50, j * 50, null);
				}
				g2.drawImage(agent1, 11*50, 4*50, null);
				g2.drawImage(building1, 10*50, 4*50, null);
				g2.drawImage(building2, 10*50, 5*50, null);
				g2.drawImage(oil, 0, 2*50, null);
				g2.drawImage(solar, 0, 0, null);
				// g2.drawImage(Tile.values()[map.get(i,j)].getImage(), i, j,
				// null);
				// TODO paint all the tiles
			}
		}
			g2.drawImage(agent2, agentX*50, agentY*50, null);
			drawBoardWithAnimation();
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		drawBoardWithAnimation();
	}

	private void drawBoardWithAnimation() {
		//tic = 0;
		timer.start();
	}
}
