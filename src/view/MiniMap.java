package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Polygon;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import model.Game;
import model.Map;
import model.Tile;
import model.agents.AbstractAgent;
import model.agents.BuilderAgent;
import model.agents.Enemy;
import model.agents.SoldierAgent;
import model.agents.WorkerAgent;
import model.buildings.*;
import model.resources.Resource;
import model.resources.ResourceType;

public class MiniMap extends JPanel {
	private Game game;
	private Map map;
	private ArrayList<AbstractAgent> agents;
	private int counter = 0;

	public static void main(String[] args) {
		(new MiniMap()).setVisible(true);
	}

	public MiniMap() {
		this.game = game.getInstance();
		this.setBorder(getBorder());
		counter++;
		repaint();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics g2 = (Graphics2D) g;
		map = game.getMap();
		Tile tile[] = Tile.values();
		int tileNum;
		if (counter == 1) {
			for (int i = 0; i < map.getXLength(); i++) {
				for (int j = 0; j < map.getYLength(); j++) {
					tileNum = tile[map.get(i, j)].getIntRepresentation();
					if (tileNum == 0) {
						g2.setColor(Color.LIGHT_GRAY);
						g2.fillRect(i * 2, j * 2, 2, 2);
					} else if (tileNum == 1) {
						g2.setColor(Color.BLACK);
						g2.fillRect(i * 2, j * 2, 2, 2);
					} else if (tileNum == 2) {
						g2.setColor(Color.DARK_GRAY);
						g2.fillRect(i * 2, j * 2, 2, 2);
					} else if (tileNum == 2) {
						g2.setColor(Color.GRAY);
						g2.fillRect(i * 2, j * 2, 2, 2);
					}
				}
			}
		}
		for (Resource r : game.getResources()) {
			g2.setColor(Color.YELLOW);
			g2.fillOval(r.getLocation().x * 2, r.getLocation().y * 2, 3, 3);
		}

		for (AbstractBuilding b : game.getBuildings()) {
			g2.setColor(Color.RED);
			g2.fillOval(b.getLocation().x * 2, b.getLocation().y * 2, 4, 4);
		}

		for (AbstractAgent a : game.getAgents()) {
			g2.setColor(Color.BLUE);
			g2.fillOval(a.getPosition().x * 2, a.getPosition().y * 2, 4, 4);
		}

		for (Enemy e : game.getEnemies()) {
			g2.setColor(Color.GREEN);
			g2.fillOval(e.getPosition().x * 2, e.getPosition().y * 2, 4, 4);
		}
	}

	// @Override
	// public void update(Observable observable, Object arg1) {
	// game = (Game) observable;
	// agents = game.getAgents();
	// repaint();
	// }
}
