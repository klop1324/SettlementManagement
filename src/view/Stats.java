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
import model.buildings.AbstractBuilding;

public class Stats extends JPanel {

	private int clickX;
	private int clickY;
	private Game game = new Game();
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
	private boolean isAgent = true;
	private boolean isBuilding = false;
	private boolean isResource = false;
	

	public static void main(String[] args) {
		(new Stats()).setVisible(true);
		
	}

	public Stats() {
		game = new Game();
		//this.setResizable(false);
		//this.setLayout(new GridLayout(3, 2));
		//this.setTitle("Information on Selected");
		//this.setMinimumSize(new Dimension(300, 150));
		//this.setMaximumSize(new Dimension(300, 150));
		//Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
		//int width = screensize.width / 2 - this.getSize().width / 2;
		//int height = screensize.height / 2 - this.getSize().height / 2;
		//this.setLocation(width, height-200);
		this.setBackground(Color.BLACK);
		this.setForeground(Color.WHITE);
		
		update();
	}
	
	public void update() {
		if (isAgent) {
			this.add(agent);
			this.add(energy);
			this.add(condition);
			this.add(oil);
			this.add(carriedResources);
		}
		else if (isBuilding) {
			this.add(building);
			this.add(capacity);
			this.add(amount);
		}
		else if (isResource) {
			this.add(resource);
			this.add(amountLeft);
		}
	}
}