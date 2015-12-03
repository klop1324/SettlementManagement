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
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javafx.scene.control.ComboBox;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.DefaultListModel;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.border.TitledBorder;

import model.Game;
import model.buildings.AbstractBuilding;
import model.buildings.*;
import model.resources.Resource;
import model.resources.ResourceType;
import model.agents.*;

class SettlementGUI extends JFrame implements Observer {

	private JFrame currentFrame = this;
	private ViewController mapArea;
	private Game game;
	private TextArea notificationArea = new TextArea();
	private JButton collectButton = new JButton("Collect");
	private JButton repairButton = new JButton("Repair");
	private JButton attackButton = new JButton("Attack");
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
	private int selectedEnemyID;
	private int clickX;
	private int clickY;
	private JScrollBar vertical = new JScrollBar();
	private JScrollBar horizontal = new JScrollBar();
	private Point userClick;
	private JLayeredPane backgroundPanel = new JLayeredPane();
	//private String selected = "select agent type";
	private boolean duringTutorial = true;
	String[] agentOrBuilding = {"select one", "create agent", "build building"};
	// add keyListener and mouseMotionListener for the map
	private ArrayList<AbstractBuilding> gameBuildings;

	public static void main(String[] args) {
		(new SettlementGUI()).setVisible(true);
		
	}

	public SettlementGUI() {
		
		try {
			readFromSaveState();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
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
		Font courier2 = new Font("Courier", Font.PLAIN, 6);
		
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
		
		collectButton.setPreferredSize(new Dimension(65, 15));
		collectButton.setFont(courier2);
		repairButton.setPreferredSize(new Dimension(60, 15));
		repairButton.setFont(courier2);
		attackButton.setPreferredSize(new Dimension(60, 15));
		attackButton.setFont(courier2);

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
		collectPanel.add(repairButton);
		collectPanel.add(attackButton);
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
		mapArea.setFocusable(false);
		mapArea.setEnabled(true);
		notifierPanel.setFocusable(false);
		infoPanel.setFocusable(false);
		
		JPanel temp = new JPanel();
		temp.setBackground(Color.BLUE);
		//JScrollPane cs = new JScrollPane(mapArea);
		JScrollPane cs = new JScrollPane();
		cs.setViewportView(mapArea);
		cs.setBounds(0, 0, 795, 572);
		temp.setBounds(cs.getX(), cs.getY(), 2000, 2000);
		vertical = cs.getVerticalScrollBar();
		horizontal = cs.getHorizontalScrollBar();
		cs.setVerticalScrollBarPolicy(cs.VERTICAL_SCROLLBAR_ALWAYS);
		cs.setHorizontalScrollBarPolicy(cs.HORIZONTAL_SCROLLBAR_ALWAYS);
		/*InputMap imV = vertical.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		InputMap imH = horizontal.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		imV.put(KeyStroke.getKeyStroke("DOWN"), "positiveUnitIncrement");
		imV.put(KeyStroke.getKeyStroke("UP"), "negativeUnitIncrement");
		imH.put(KeyStroke.getKeyStroke("RIGHT"), "positiveUnitIncrement");
		imH.put(KeyStroke.getKeyStroke("LEFT"), "negativeUnitIncrement");*/
		InputMap input = mapArea.getInputMap();
		KeyStroke key = KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, Event.PGDN);
		input.put(key, vertical.getActionForKeyStroke(KeyStroke.getKeyStroke("DOWN")));
		backgroundPanel.add(cs, new Integer(0), 0);
		
		infoButton.setBounds(635, 20, 10, 40);
		notifierButton.setBounds(20, 392, 40, 10);
		
		//this.setupKeyBinding();
				
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
	
	private void readFromSaveState() throws IOException{
		int userSelection = JOptionPane.showConfirmDialog(this,"Load Previous State?", null, JOptionPane.YES_NO_CANCEL_OPTION);
		if(userSelection == JOptionPane.YES_OPTION) {
			try{
			FileInputStream input;
			input = new FileInputStream("SettlementManagement");
			ObjectInputStream objectStream = new ObjectInputStream(input);
			try {
				Game.onLoad((Game) objectStream.readObject());
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			input.close();
			objectStream.close();
			}
			finally{
				game.getInstance();
			}
		}
		if(userSelection == JOptionPane.CANCEL_OPTION){
			System.exit(0);
		}
	}

	public void registerListeners() {
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				// closes, saves, or doesnt close based on user input
				int userSelection = JOptionPane.showConfirmDialog(currentFrame,"Save Data?", null, JOptionPane.YES_NO_CANCEL_OPTION);
				if(userSelection == JOptionPane.YES_OPTION) {
					try {
						FileOutputStream outputStream = new FileOutputStream("SettlementManagement");
						ObjectOutputStream objectStream = new ObjectOutputStream(outputStream);
						objectStream.writeObject(Game.getInstance());
						objectStream.close();
						outputStream.close();
						
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				}
				
				System.exit(0);
			}});
		collectButton.addActionListener(new CollectButtonListener());
		repairButton.addActionListener(new RepairButtonListener());
		attackButton.addActionListener(new AttackButtonListener());
		nextButton.addActionListener(new NextButtonListener());
		infoButton.addActionListener(new InfoButtonListener());
		notifierButton.addActionListener(new NotifierButtonListener());
		createButton.addActionListener(new CreateListener());
		selectAgent.addActionListener(new DropDownListener());
		helpButton.addActionListener(new HelpButtonListener());
		mapArea.addMouseListener(new ClickerListener());
		this.addKeyListener(new ArrowKeyListener());
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
				helpPanel.setVisible(false);
			}
			else {
				notifierPanel.setVisible(true);
				notifierButton.setBounds(20, 392, 40, 10);
				helpPanel.setVisible(true);
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
				selectAgent.addItem("home depot");
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
				if (userClick != null){
					if(game.canBuildBuilding(userClick, new Armory(userClick))){
						game.createBuilding(userClick, new Armory(userClick));
						System.out.println(selected);
					}
					else{
						System.out.println("You dont have enough resources to build a(n)"+selected);
					}
					return;
				}
			}
			else if (selected.equals("charging station")) {
				if (userClick != null){
					if(game.canBuildBuilding(userClick, new ChargingStation(userClick))){
						game.createBuilding(userClick, new ChargingStation(userClick));
						System.out.println(selected);
					}
					else{
						System.out.println("You dont have enough resources to build a(n)"+selected);
					}
					return;
				}
			}
			else if (selected.equals("home depot")) {
				if (userClick != null){
					if(game.canBuildBuilding(userClick, new HomeDepot(userClick))){
						game.createBuilding(userClick, new HomeDepot(userClick));
						System.out.println(selected);
					}
					else{
						System.out.println("You dont have enough resources to build a(n)"+selected);
					}
					return;
				}
			}
			else if (selected.equals("junkyard")) {
				if (userClick != null){
					if(game.canBuildBuilding(userClick, new JunkYard(userClick))){
						game.createBuilding(userClick, new JunkYard(userClick));
						System.out.println(selected);
					}
					else{
						System.out.println("You dont have enough resources to build a(n)"+selected);
					}
					return;
				}
			}
			else if (selected.equals("oil tank")) {
				if (userClick != null){
					if(game.canBuildBuilding(userClick, new OilTank(userClick))){
						game.createBuilding(userClick, new OilTank(userClick));
						System.out.println(selected);
					}
					else{
						System.out.println("You dont have enough resources to build a(n)"+selected);
					}
					return;
				}
			}
			else if (selected.equals("oil well")) {
				if (userClick != null){
					if(game.canBuildBuilding(userClick, new OilWell(userClick))){
						game.createBuilding(userClick, new OilWell(userClick));
						System.out.println(selected);
					}
					else{
						System.out.println("You dont have enough resources to build a(n)"+selected);
					}
					return;
				}
			}
			else if (selected.equals("workshop")) {
				if (userClick != null){
					if(game.canBuildBuilding(userClick, new Workshop(userClick))){
						game.createBuilding(userClick, new Workshop(userClick));
						System.out.println(selected);
					}
					else{
						System.out.println("You dont have enough resources to build a(n)"+selected);
					}
					return;
				}
			}
		}
	}
	
	private class ClickerListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) { // Gets coordinates of mouse clicks according to game points.
			clickX = (int) Math.floor(e.getPoint().x/50);
			clickY = (int) Math.floor(e.getPoint().y/50);
			userClick = new Point(clickX, clickY);
			
			for(Enemy m : game.getEnemies()) {
				if(m.getPosition().equals(userClick))
					selectedEnemyID = m.getID();
			}
			
			System.out.println(userClick);
			
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
	
	private class ArrowKeyListener implements KeyListener{

		@Override
		public void keyPressed(KeyEvent e) {
			if (e.isActionKey())
				System.out.println("hello");
			InputMap imV = vertical.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
			InputMap imH = horizontal.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
			
			int key = e.getKeyCode();
			if (key == KeyEvent.VK_UP) {
				//System.out.println("up");
				imV.put(KeyStroke.getKeyStroke("UP"), "negativeUnitIncrement");
				System.out.println("up");
			}
			else if (key == KeyEvent.VK_DOWN) {
				imV.put(KeyStroke.getKeyStroke("DOWN"), "positiveUnitIncrement");
				System.out.println("down");
			}
			else if (key == KeyEvent.VK_RIGHT) {
				imH.put(KeyStroke.getKeyStroke("RIGHT"), "positiveUnitIncrement");
				System.out.println("right");
			}
			else if (key == KeyEvent.VK_LEFT) {
				imH.put(KeyStroke.getKeyStroke("LEFT"), "negativeUnitIncrement");
				System.out.println("left");
			}
		}

		@Override
		public void keyReleased(KeyEvent arg0) {
			
		}

		@Override
		public void keyTyped(KeyEvent arg0) {
			
		}
		
	}
	
	private class CollectButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.out.println(userClick);
			for (Resource r: game.getResources()){
				if (r.getLocation().equals(userClick)){
					game.agentToResource(userClick);
				}
				else {
					//notificationArea.append("Please choose a resource");
				}
			}
		}
	}
	
	private class RepairButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
		}
	}
	
	private class AttackButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.out.println(userClick);
			if (selectedEnemyID != 0){
				game.agentToEnemy(selectedEnemyID);
			}
			else {
				//notificationArea.append("You have not chosen an enemy to attack");
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

		//SUPER HACKY CODE
		ArrayList<JLabel> labels = new ArrayList<JLabel>();
		labels.add(electricityAmount);
		labels.add(copperAmount);
		labels.add(coalAmount);
		labels.add(goldAmount);
		labels.add(ironAmount);
		labels.add(oilAmount);
		
		// l.setText(l.getText() + "0");
		for(JLabel l: labels){
			l.setText("0");
		}
		
		for (AbstractBuilding b: gameBuildings){
			ArrayList<ResourceType> resources = b.getResources();
			for(int i = 0; i < resources.size(); i++){
				int currAmount = 0;
				ResourceType resource = resources.get(i);
				switch(resource){
				case ELECTRICITY:
					currAmount = Integer.parseInt(electricityAmount.getText());
					electricityAmount.setText(b.getResourceAmount(resource)+currAmount+"");
					break;
				case COPPER:
					currAmount = Integer.parseInt(copperAmount.getText());
					copperAmount.setText(b.getResourceAmount(resource)+currAmount+"");
					break;
				case COAL:
					currAmount = Integer.parseInt(coalAmount.getText());
					coalAmount.setText(b.getResourceAmount(resource)+currAmount+"");
					break;
				case GOLD:
					currAmount = Integer.parseInt(goldAmount.getText());
					goldAmount.setText(b.getResourceAmount(resource)+currAmount+"");
					break;
				case IRON:
					currAmount = Integer.parseInt(ironAmount.getText());
					ironAmount.setText(b.getResourceAmount(resource)+currAmount+"");
					break;
				case OIL:
					currAmount = Integer.parseInt(oilAmount.getText());
					oilAmount.setText(b.getResourceAmount(resource)+currAmount+"");
					break;
				}
			}
		// END SUPER HACKY CODE
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