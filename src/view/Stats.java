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

	private Game game;
	private JLabel agent = new JLabel("Agent: ");
	private JLabel resource = new JLabel("Resource: ");
	private JLabel building = new JLabel("Building: ");
	private JLabel energy = new JLabel("Energy: ");
	private JLabel condition = new JLabel("Condition: ");
	private JLabel oil = new JLabel("Oil: ");
	private JLabel carriedResources = new JLabel("Carrying: ");
	private JLabel capacity = new JLabel("Capacity: ");
	private JLabel amount = new JLabel("Amount: ");
	private JLabel amountLeft = new JLabel("Amount: ");
	private boolean isAgent = false;
	private boolean isBuilding = false;
	private boolean isResource = false;
	

	public static void main(String[] args) {
		(new Stats()).setVisible(true);
		
	}

	public Stats() {
		game = Game.getInstance();
		this.setLayout(new GridLayout(1, 5));
		this.setBounds(20, 0, 500, 20);
		this.setBackground(Color.BLACK);
		
		Font courier = new Font("Courier", Font.PLAIN, 10);
		
		agent.setForeground(Color.CYAN);
		agent.setFont(courier);
		energy.setForeground(Color.CYAN);
		energy.setFont(courier);
		condition.setForeground(Color.CYAN);
		condition.setFont(courier);
		oil.setForeground(Color.CYAN);
		oil.setFont(courier);
		carriedResources.setForeground(Color.CYAN);
		carriedResources.setFont(courier);
		
		building.setForeground(Color.CYAN);
		building.setFont(courier);
		capacity.setForeground(Color.CYAN);
		capacity.setFont(courier);
		amount.setForeground(Color.CYAN);
		amount.setFont(courier);
		
		resource.setForeground(Color.CYAN);
		resource.setFont(courier);
		amountLeft.setForeground(Color.CYAN);
		amountLeft.setFont(courier);
	}
	
	public void update(Point userClick) {
		int resourceAmount = 0;
		for (AbstractAgent a: game.getAgents()){
			if (a.getPosition().equals(userClick)){
				this.removeAll();
				isAgent = true;
				isResource = false;
				isBuilding = false;
				if (a.getClass().equals(BuilderAgent.class))
					agent.setText("Agent: Builder");
				if (a.getClass().equals(SoldierAgent.class))
					agent.setText("Agent: Soldier");
				if (a.getClass().equals(WorkerAgent.class))
					agent.setText("Agent: Worker");
				energy.setText("Energy: " + a.getEnergy());
				condition.setText("Condition: " + a.getCondition());
				oil.setText("Oil: " + a.getOil());
				carriedResources.setText("Carrying: " + a.getCarriedResources());
			}
		}
		for (AbstractBuilding b: game.getBuildings()){
			if (b.getLocation().equals(userClick)){
				this.removeAll();
				isBuilding = true;
				isAgent = false;
				isResource = false;
				building.setText("Building: " + b.getName());
				capacity.setText("Capacity: " + b.getCapacity());
				for (ResourceType r: b.getResources()){
					if (r == ResourceType.COAL) {
						resourceAmount += b.getResourceAmount(ResourceType.COAL);
					}
					if (r == ResourceType.COPPER) {
						resourceAmount += b.getResourceAmount(ResourceType.COPPER);
					}
					if (r == ResourceType.IRON) {
						resourceAmount += b.getResourceAmount(ResourceType.IRON);
					}
					if (r == ResourceType.GOLD) {
						resourceAmount += b.getResourceAmount(ResourceType.GOLD);
					}
					if (r == ResourceType.OIL) {
						resourceAmount += b.getResourceAmount(ResourceType.OIL);
					}
					if (r == ResourceType.ELECTRICITY) {
						resourceAmount += b.getResourceAmount(ResourceType.ELECTRICITY);
					}
				}
				amount.setText("Carrying: " + resourceAmount);
			}
		}
		for (Resource r: game.getMapResources()){
			if (r.getLocation().equals(userClick)){
				this.removeAll();
				isResource = true;
				isAgent = false;
				isBuilding = false;
				ResourceType type = r.getType();
				if (type == ResourceType.COAL) {
					resource.setText("Resource: Coal");
				}
				else if (type == ResourceType.COPPER) {
					resource.setText("Resource: Copper");
				}
				else if (type == ResourceType.IRON) {
					resource.setText("Resource: Iron");
				}
				else if (type == ResourceType.GOLD) {
					resource.setText("Resource: Gold");
				}
				else if (type == ResourceType.OIL) {
					resource.setText("Resource: Oil");
				}
				else if (type == ResourceType.ELECTRICITY) {
					resource.setText("Resource: Electricity");
				}
				amountLeft.setText("Amount: " + r.getAmount());
			}
		}
		if (isAgent) {
			this.setLayout(new GridLayout(1, 5));
			this.add(agent);
			this.add(energy);
			this.add(condition);
			this.add(oil);
			this.add(carriedResources);
		}
		else if (isBuilding) {
			this.setLayout(new GridLayout(1, 3));
			this.add(building);
			this.add(capacity);
			this.add(amount);
		}
		else if (isResource) {
			this.setLayout(new GridLayout(1, 2));
			this.add(resource);
			this.add(amountLeft);
		}
	}
}