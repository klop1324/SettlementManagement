package view;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.TextArea;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javafx.scene.control.ComboBox;

import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.border.TitledBorder;

import model.*;
import model.agents.*;
import model.buildings.Building;
import model.resources.Resource;
import model.resources.ResourceType;

class SettlementGUI extends JFrame implements Observer {

	private ViewController mapArea;
	private Game game;
	private TextArea notificationArea = new TextArea();
	private JButton collectButton = new JButton("Collect Resource");
	private JButton nextButton = new JButton(">>");
	private JButton infoButton = new JButton();
	private JButton notifierButton = new JButton();
	private JButton individualButton = new JButton();
	private JButton createButton = new JButton("Create/Build");
	private JButton helpButton = new JButton("Help");
	private JLabel electricityAmount = new JLabel("Electricity: ");
	private JLabel oilAmount = new JLabel("Oil: ");	
	private JLabel coalAmount = new JLabel("Coal: ");
	private JLabel copperAmount = new JLabel("Copper: ");
	private JLabel ironAmount = new JLabel("Iron: ");
	private JLabel goldAmount = new JLabel("Gold: ");
	private JLabel name = new JLabel("Click Something");
	private JPanel notifierPanel = new JPanel();
	private JPanel infoPanel = new JPanel();
	private JPanel helpPanel = new JPanel();
	private JComboBox selectAgent;
	//private JPanel individual = new JPanel();
	private Stats individual = new Stats();
	private HelpMenu helpMenu = new HelpMenu();
	private int one = 0;
	private int two = 0;
	private int next = 0;
	private int clickX;
	private int clickY;
	private Point agentDest;
	private JLayeredPane backgroundPanel = new JLayeredPane();
	//private String selected = "select agent type";
	private boolean duringTutorial = true;
	String[] agentOrBuilding = {"select one", "create agent", "build building"};
	// add keyListener and mouseMotionListener for the map
	private ArrayList<Building> gameBuildings;

	public static void main(String[] args) {
		(new SettlementGUI()).setVisible(true);
		
	}

	public SettlementGUI() {
		game = Game.getInstance();
		mapArea = new ViewController(game);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setTitle("Game Name Goes Here");
		this.setMinimumSize(new Dimension(800, 600));
		this.setMaximumSize(new Dimension(800, 600));
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = screensize.width / 2 - this.getSize().width / 2;
		int height = screensize.height / 2 - this.getSize().height / 2;
		this.setLocation(width, height);
		
		Font courier = new Font("Courier", Font.PLAIN, 12);
		
		backgroundPanel.setBounds(0, 0, 800, 600);

		TitledBorder noticeBorder = new TitledBorder("Notifications");
		noticeBorder.setTitleColor(Color.WHITE);
		notifierPanel.setOpaque(true);
		notifierPanel.setBorder(noticeBorder);
		notifierPanel.setBackground(Color.DARK_GRAY);
		notifierPanel.setBounds(0, 402, 795, 170);
		notificationArea.setBackground(Color.BLACK);
		notificationArea.setPreferredSize(new Dimension(700, 130));
		notificationArea.setEditable(false);
		notificationArea.setFont(courier);
		notificationArea.setForeground(Color.GREEN);
		notificationArea.setText("Welcome to NAME GOES HERE!\nPlease click on the next " +
				"button to continue this tutorial.\n");
		notifierPanel.add(notificationArea);
		notifierPanel.add(nextButton);
		
		electricityAmount.setForeground(Color.WHITE);
		oilAmount.setForeground(Color.WHITE);
		coalAmount.setForeground(Color.WHITE);
		copperAmount.setForeground(Color.WHITE);
		ironAmount.setForeground(Color.WHITE);
		goldAmount.setForeground(Color.WHITE);
		
		helpButton.setBackground(Color.DARK_GRAY);
		helpButton.setForeground(Color.BLACK);
		helpPanel.setBackground(Color.DARK_GRAY);
		helpPanel.add(helpButton);
		helpPanel.setBounds(750, 542, 40, 25);
		
		selectAgent = new JComboBox(agentOrBuilding);
		selectAgent.setSelectedIndex(0);
		JPanel createPanel = new JPanel();
		JPanel dropPanel = new JPanel();
		createPanel.setBackground(Color.BLACK);
		dropPanel.setBackground(Color.BLACK);
		createPanel.add(createButton);
		dropPanel.add(selectAgent);

		TitledBorder infoBorder = new TitledBorder("Information");
		infoBorder.setTitleColor(Color.WHITE);
		infoPanel.setOpaque(true);
		infoPanel.setBounds(645, 0, 150, 401);
		infoPanel.setLayout(new GridLayout(9, 1));
		infoPanel.setBorder(infoBorder);
		infoPanel.setBackground(Color.BLACK);
		JPanel collectPanel = new JPanel();
		collectPanel.setOpaque(true);
		collectPanel.setBackground(Color.BLACK);
		collectPanel.add(collectButton);
		infoPanel.add(collectPanel);
		infoPanel.add(createPanel);
		infoPanel.add(dropPanel);
		infoPanel.add(electricityAmount);
		infoPanel.add(oilAmount);	
		infoPanel.add(coalAmount);
		infoPanel.add(copperAmount);
		infoPanel.add(ironAmount);
		infoPanel.add(goldAmount);
		
		registerListeners();
		
//		JOptionPane tutorial = new JOptionPane();
//		tutorial.showMessageDialog(this, "Please read the tutorial first.");
//		tutorial.setVisible(false); // set to true
		
		this.setFocusable(false);
		backgroundPanel.setFocusable(false);
		mapArea.setFocusable(true);
		notifierPanel.setFocusable(false);
		infoPanel.setFocusable(false);
		
		JScrollPane cs = new JScrollPane(mapArea);
		cs.setBounds(0, 0, 795, 572);
		JScrollBar vertical = cs.getVerticalScrollBar();
		JScrollBar horizontal = cs.getHorizontalScrollBar();
		/*InputMap imV = vertical.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		InputMap imH = horizontal.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		imV.put(KeyStroke.getKeyStroke("DOWN"), "positiveUnitIncrement");
		imV.put(KeyStroke.getKeyStroke("UP"), "negativeUnitIncrement");
		imH.put(KeyStroke.getKeyStroke("DOWN"), "positiveUnitIncrement");
		imH.put(KeyStroke.getKeyStroke("UP"), "negativeUnitIncrement");*/
		InputMap input = mapArea.getInputMap();
		KeyStroke key = KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, Event.PGDN);
		input.put(key, vertical.getActionForKeyStroke(KeyStroke.getKeyStroke("DOWN")));
		backgroundPanel.add(cs, new Integer(0), 0);
		
		infoButton.setBounds(635, 20, 10, 40);
		notifierButton.setBounds(20, 392, 40, 10);
		
		this.add(backgroundPanel);
		backgroundPanel.add(individual, new Integer(1), 0);
		backgroundPanel.add(infoButton, new Integer(1), 0);
		backgroundPanel.add(notifierButton, new Integer(1), 0);
		backgroundPanel.add(notifierPanel, new Integer(1), 0);
		backgroundPanel.add(infoPanel, new Integer(1), 0);
		backgroundPanel.add(helpPanel, new Integer(2), 0);
	}
	
	public void addObservers(){ // Adds observers to game
		game.addObserver(mapArea);
		game.addObserver(this);
	}

	public void registerListeners() {

		collectButton.addActionListener(new CollectButtonListener());
		nextButton.addActionListener(new NextButtonListener());
		infoButton.addActionListener(new InfoButtonListener());
		notifierButton.addActionListener(new NotifierButtonListener());
		createButton.addActionListener(new CreateListener());
		selectAgent.addActionListener(new DropDownListener());
		helpButton.addActionListener(new HelpButtonListener());
		mapArea.addMouseListener(new ClickerListener());
		addObservers();
	}
	
	private class InfoButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			one++;
			if (one % 2 != 0) {
				infoPanel.setVisible(false);
				infoButton.setBounds(785, 20, 10, 40);
			}
			else {
				infoPanel.setVisible(true);
				infoButton.setBounds(635, 20, 10, 40);
			}
		}
	}
	
	private class NotifierButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			two++;
			if (two % 2 != 0) {
				notifierPanel.setVisible(false);
				notifierButton.setBounds(20, 562, 40, 10);
			}
			else {
				notifierPanel.setVisible(true);
				notifierButton.setBounds(20, 392, 40, 10);
			}
		}
	}
	
	private class DropDownListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			String selected = "" + selectAgent.getSelectedItem();
			if (selected.equals("create agent")) {
				selectAgent.removeAllItems();
				selectAgent.addItem("select agent type");
				selectAgent.addItem("builder");
				selectAgent.addItem("soldier");
				selectAgent.addItem("worker");
				selectAgent.addItem("BACK TO MAIN");
			}
			else if (selected.equals("build building")) {
				selectAgent.removeAllItems();
				selectAgent.addItem("select building type");
				selectAgent.addItem("armory");
				selectAgent.addItem("charging station");
				selectAgent.addItem("junkyard");
				selectAgent.addItem("oil tank");
				selectAgent.addItem("oil well");
				selectAgent.addItem("workshop");
				selectAgent.addItem("BACK TO MAIN");
			}
			else if (selected.equals("BACK TO MAIN")) {
				System.out.println(selected);
				selectAgent.removeAllItems();
				selectAgent.addItem("select one");
				selectAgent.addItem("create agent");
				selectAgent.addItem("build building");
			}
		}
	}
	
	private class CreateListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			String selected = "" + selectAgent.getSelectedItem();
			selectAgent.removeAllItems();
			selectAgent.addItem("select one");
			selectAgent.addItem("create agent");
			selectAgent.addItem("build building");
			if (selected.equals("select agent type"))
				return;
			else if (selected.equals("builder")) {
				System.out.println(selected);
				return;
			}
			else if (selected.equals("soldier")) {
				System.out.println(selected);
				return;
			}
			else if (selected.equals("worker")) {
				System.out.println(selected);
				return;
			}
			else if (selected.equals("armory")) {
				System.out.println(selected);
				return;
			}
			else if (selected.equals("charging station")) {
				System.out.println(selected);
				return;
			}
			else if (selected.equals("junkyard")) {
				System.out.println(selected);
				return;
			}
			else if (selected.equals("oil tank")) {
				System.out.println(selected);
				return;
			}
			else if (selected.equals("oil well")) {
				System.out.println(selected);
				return;
			}
			else if (selected.equals("workshop")) {
				System.out.println(selected);
				return;
			}
		}
	}
	
	private class ClickerListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) { // Gets coordinates of mouse clicks according to game points.
			clickX = (int) Math.floor(e.getPoint().x/50);
			clickY = (int) Math.floor(e.getPoint().y/50);
			agentDest = new Point(clickX, clickY);
			System.out.println(agentDest);
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	private class CollectButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.out.println(agentDest);
			for (Resource r: game.getResources()){
				if (r.getLocation().equals(agentDest)){
					game.agentToResource(agentDest);
				}
				else {
					//notificationArea.append("Please choose a resource");
				}
			}
		}
	}

	private class NextButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			//next++;
			if (next == 1)
				notificationArea.append("The panel at the top shows information about " +
						"selected agents, buildings, and resources.\n");
			if (next == 2) {
				notificationArea.append("The panel to the right shows the amount of each resource " +
						"collected.\nTo collect a resource, click on a resource and then " +
						"the \"Collect Resource\" button.\nThe drop down menu shows all the active agents.");
				duringTutorial = false;
			}
			if (next == 3 && !duringTutorial)
				notificationArea.append("solar panel = solar panel \nblood = oil \nslime pit" + ""
						+ " = oil tank \nslime = charging station \nwumpus = soldier agent \n" + 
						"hunter = worker agent");
		}
	}
	
	private class HelpButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			helpMenu.setVisible(true);
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		game = (Game) o;
		gameBuildings = game.getBuildings();
		for (Building b: gameBuildings){
			switch (b.getType()) { // Checks for each building in the arraylist and set's its info.
			case ARMORY:
				break;
			case CHARGINGSTATION:
				electricityAmount.setText("Electricity: " + b.getResources().get(ResourceType.ELECTRICITY));
				repaint();
				break;
			case HOMEDEPOT:
				break;
			case JUNKYARD:
				copperAmount.setText("Copper: " + b.getResources().get(ResourceType.COPPER));
				goldAmount.setText("Gold: " + b.getResources().get(ResourceType.GOLD));
				ironAmount.setText("Iron: " + b.getResources().get(ResourceType.IRON));
				coalAmount.setText("Coal: " + b.getResources().get(ResourceType.COAL));
				break;
			case OILTANK:
				oilAmount.setText("Oil: " + b.getResources().get(ResourceType.OIL));
				break;
			case OILWELL:
				break;
			case WORKSHOP:
				break;
			default:
				break;

			}
		}
		String resourceNotification = "";
		for (Resource r: game.getResources()) {
			if (r.getNotification() != null){
				resourceNotification += r.getNotification() + "\n";
				notificationArea.setText(resourceNotification);
				notificationArea.repaint();
			}
		}
		repaint();
	}
}