package view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

import model.Game;
import model.Map;
import model.agents.*;

public class ViewController extends JPanel implements Observer {
	private Game game;
	private Map map;
	private Image agent1, agent2, oilTank, charge, oil, solar, ground;
	private Image junkYard, armory, homeDepot, coal, copper, iron, gold;
	private int agentX;
	private int agentY;
	private ArrayList<AbstractAgent> agents;
	private Timer timer = new Timer(50, new TimerListener());
	private int tic = 0;
	
	private boolean isAnimating = false;

	public ViewController() {
		this.setVisible(true);
	}

	public ViewController(Game game) {
		this.game = game;
		try {			
			agent1 = ImageIO.read(new File("./ImageSet/destroyer.png"));
			agent2 = ImageIO.read(new File("./ImageSet/defender.png"));
			oilTank = ImageIO.read(new File("./ImageSet/oilTank.png"));
			charge = ImageIO.read(new File("./ImageSet/basic-accumulator.png"));
			junkYard = ImageIO.read(new File("./ImageSet/junkYard.png"));
			armory = ImageIO.read(new File("./ImageSet/electric-furnace-base.png"));
			homeDepot = ImageIO.read(new File("./ImageSet/market.png"));
			oil = ImageIO.read(new File("./ImageSet/oil.png"));
			solar = ImageIO.read(new File("./ImageSet/solar.png"));
			coal = ImageIO.read(new File("./ImageSet/singleCoal.png"));
			copper = ImageIO.read(new File("./ImageSet/singleCopper.png"));
			iron = ImageIO.read(new File("./ImageSet/singleStone.png"));
			gold = ImageIO.read(new File("./ImageSet/singleIron.png"));
			ground = ImageIO.read(new File("./ImageSet/tile-Plating.png"));
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
			for (AbstractAgent agent: agents){ 
				if(agent.getPosition().equals(agent.getDestination())){
					timer.stop(); // will have to refactor this for multiple agents
					repaint();
				}
			}
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
		for (int i = 0; i < map.getXLength(); i++) {
			for (int j = 0; j < map.getYLength(); j++) {
				if (map.get(i, j) == 0) {
					g2.drawImage(ground, i * 50, j * 50, null);
				} else if (map.get(i, j) == 1) {
					g2.drawImage(ground, i * 50, j * 50, null);
				} else {
					g2.drawImage(ground, i * 50, j * 50, null);
				}
				// g2.drawImage(Tile.values()[map.get(i,j)].getImage(), i, j,
				// null);
				// TODO paint all the tiles
			}
		}
		g2.drawImage(agent1, 11*50, 4*50, null);
		g2.drawImage(oilTank, 10*50, 4*50, null);
		g2.drawImage(charge, 10*50, 5*50, null);
		g2.drawImage(junkYard, 11*50, 3*50, null);
		g2.drawImage(armory, 12*50, 3*50, null);
		g2.drawImage(homeDepot, 13*50, 6*50, null);
		g2.drawImage(oil, 0, 2*50, null);
		g2.drawImage(solar, 0, 0, null);
		g2.drawImage(solar, 0*50, 1*50, null);
		g2.drawImage(coal, 4*50, 7*50, null);
		g2.drawImage(copper, 9*50, 8*50, null);
		g2.drawImage(iron, 8*50, 6*50, null);
		g2.drawImage(gold, 9*50, 6*50, null);


		g2.drawImage(agent2, agentX*50, agentY*50, null);
//		drawBoardWithAnimation();
	}

	@Override
	public void update(Observable observable, Object arg1) {
		// Gets notified from Game and changes the x and y position based on where the
		// robot sprite is
		game = (Game) observable;
		agents = game.getAgents();
		for(AbstractAgent a: agents){
			agentX = a.getPosition().x;
			agentY = a.getPosition().y;
		}
		drawBoardWithAnimation();
	}
	
	private void drawBoardWithAnimation() {
		//tic = 0;
		timer.start();		
	}
}
