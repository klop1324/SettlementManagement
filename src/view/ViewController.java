package view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
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
import model.buildings.AbstractBuilding;
import model.resources.Resource;

public class ViewController extends JPanel implements Observer {
	private Game game;
	private Map map;
	private Image agent1, enemy, oilTank, charge, oil, solar, ground, water, sand, grass;
	private Image junkYard, armory, homeDepot, coal, copper, iron, gold, oilWell, workShop, 
	agent2, agent3, cursor;
	private ArrayList<AbstractAgent> agents;
	private Point cursorLocation;
	private int tic = 0;

	private boolean isAnimating = false;

	public ViewController() {
		this.setVisible(true);
	}

	public ViewController(Game game) {
		this.game = game;
		try {			
			agent1 = ImageIO.read(new File("./ImageSet/defender.png"));
			agent2 = ImageIO.read(new File("./ImageSet/distractor3.png"));
			agent3 = ImageIO.read(new File("./ImageSet/destroyer.png"));
			enemy = ImageIO.read(new File("./ImageSet/Agent1.png"));
			cursor = ImageIO.read(new File("./ImageSet/cursor.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		repaint();
	}

	public boolean isAnimating() {
		return isAnimating;
	}
	
	public void setCursorLocation(Point p) {
		this.cursorLocation = p;
	}

	public void paintComponent(Graphics g) {
		// draws each tile, based on map representation, getting image from the
		// Tile enum
		Graphics g2 = (Graphics2D) g;
		map = game.getMap();
		Tile tile[] = Tile.values();
		for (int i = 0; i < map.getXLength(); i++) {
			for (int j = 0; j < map.getYLength(); j++) {
				g2.drawImage(tile[map.get(i, j)].getImage(), i*Tile.getTileSize(), j*Tile.getTileSize(), null);
			}
		}
		
		for (Resource r: game.getResources()){
			g2.drawImage(r.getType().getImage(), r.getLocation().x *50, r.getLocation().y*50, null);
		}
		
		for (AbstractBuilding b: game.getBuildings()){
			g2.drawImage(b.getImage(), b.getLocation().x*50, b.getLocation().y*50, null);
		}
		for(AbstractBuilding b: game.getBuildingsInProcess()){
			g2.drawImage(b.getType().getUnbuiltImage(), b.getLocation().x*50, b.getLocation().y*50, null);
		}
		
		for (AbstractAgent a: game.getAgents()){
			if (a.getClass().equals(BuilderAgent.class)){
				g2.drawImage(agent1, a.getPosition().x*50, a.getPosition().y*50, null);
			}
			if (a.getClass().equals(WorkerAgent.class)){
				g2.drawImage(agent2, a.getPosition().x*50, a.getPosition().y*50, null);
			}
			if (a.getClass().equals(SoldierAgent.class)){
				g2.drawImage(agent3, a.getPosition().x*50, a.getPosition().y*50, null);
			}
		}
		
		for (Enemy e : game.getEnemies()) {
			g2.drawImage(enemy, e.getPosition().x*50, e.getPosition().y*50, null);
		}
		
		// Draw cursor at the end
		if(cursorLocation != null)
			g2.drawImage(cursor, cursorLocation.x*50, cursorLocation.y*50, null);
	}

	@Override
	public void update(Observable observable, Object arg1) {
		// Gets notified from Game and changes the x and y position based on where the
		// robot sprite is
		game = (Game) observable;
		agents = game.getAgents();
		repaint();
	}
}
