package view;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.TextArea;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.Timer;
import javax.swing.border.TitledBorder;

import model.Game;
import model.agents.*;
import model.buildings.*;
import model.resources.*;


public class Stats extends JPanel {

	private int clickX;
	private int clickY;
	private Game game;
	private JLabel agent = new JLabel("Agent: ");
	private JLabel resource = new JLabel("Resource: ");
	private JLabel building = new JLabel("Building: ");
	private JLabel energy = new JLabel("Energy: ");
	private JLabel condition = new JLabel("Condition: ");
	private JLabel oil = new JLabel("Oil: ");
	private JLabel carriedResources = new JLabel("Carried Resources: ");
	private JLabel capacity = new JLabel("Capacity: ");
	private JLabel amount = new JLabel("Amount: ");
	private JLabel amountLeft = new JLabel("Amount Left: ");
	private JLabel blank1 = new JLabel("");
	private JLabel blank2 = new JLabel("");
	private JLabel blank3 = new JLabel("");
	private boolean isAgent = false;
	private boolean isBuilding = false;
	private boolean isResource = false;
	//private Point userClick;
	private ViewController mapArea;
	

	public static void main(String[] args) {
		(new Stats()).setVisible(true);
		
	}

	public Stats() {
		game = Game.getInstance();
		mapArea = new ViewController(game);
		this.setLayout(new GridLayout(1, 5));
		this.setBounds(197, 0, 400, 20);
		this.setBackground(Color.BLACK);
		
		agent.setForeground(Color.CYAN);
		energy.setForeground(Color.CYAN);
		condition.setForeground(Color.CYAN);
		oil.setForeground(Color.CYAN);
		carriedResources.setForeground(Color.CYAN);
		
		building.setForeground(Color.CYAN);
		capacity.setForeground(Color.CYAN);
		amount.setForeground(Color.CYAN);
		
		resource.setForeground(Color.CYAN);
		amountLeft.setForeground(Color.CYAN);
	}
	
	public void update(Point userClick) {
		int resourceAmount = 0;
		for (AbstractAgent a: game.getAgents()){
			if (a.getPosition().equals(userClick)){
				isAgent = true;
				isResource = false;
				isBuilding = false;
				//agent.setText("Agent: " + a.get);
				energy.setText("Energy: " + a.getEnergy());
				condition.setText("Condition: " + a.getCondition());
				oil.setText("Oil: " + a.getOil());
				//carriedResources.setText("Carried Resources: " + a.get);
			}
		}
		for (AbstractBuilding b: game.getBuildings()){
			if (b.getLocation().equals(userClick)){
				isBuilding = true;
				isAgent = false;
				isResource = false;
				//building.setText("Condition: ");
				capacity.setText("Oil: " + b.getCapacity());
				/*resourceAmount += b.getResourceAmount(ResourceType.COAL) +
						b.getResourceAmount(ResourceType.COPPER) +
						b.getResourceAmount(ResourceType.IRON) +
						b.getResourceAmount(ResourceType.GOLD) +
						b.getResourceAmount(ResourceType.OIL) +
						b.getResourceAmount(ResourceType.ELECTRICITY);*/
				amount.setText("Carried Resources: " + resourceAmount);
			}
		}
		for (Resource r: game.getResources()){
			if (r.getLocation().equals(userClick)){
				isResource = true;
				isAgent = false;
				isBuilding = false;
				//resource.setText("Oil: ");
				amountLeft.setText("Carried Resources: " + r.getAmount());
			}
		}
		if (isAgent) {
			this.add(agent);
			this.add(energy);
			this.add(condition);
			this.add(oil);
			this.add(carriedResources);
		}
		else if (isBuilding) {
			this.add(building);
			this.add(blank1);
			this.add(capacity);
			this.add(blank2);
			this.add(amount);
		}
		else if (isResource) {
			this.add(blank1);
			this.add(resource);
			this.add(blank2);
			this.add(amountLeft);
			this.add(blank3);
		}
	}
}