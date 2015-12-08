package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.border.TitledBorder;

import model.buildings.BuildingType;

public class HelpMenu extends JFrame {

	private JTabbedPane multiPanels = new JTabbedPane();
	private JTextArea gameObje = new JTextArea();
	private JTextArea panelArea = new JTextArea();
	private JTextArea howToArea = new JTextArea();
	private JTextArea robots = new JTextArea();
	private JTextPane costs = new JTextPane();

	public static void main(String[] args) {
		(new HelpMenu()).setVisible(true);
	}

	public HelpMenu() {
		// this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setTitle("Quick Guide");
		this.setMinimumSize(new Dimension(500, 350));
		this.setMaximumSize(new Dimension(500, 350));
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = screensize.width / 2 - this.getSize().width / 2;
		int height = screensize.height / 2 - this.getSize().height / 2;
		this.setLocation(width, height);
		this.setForeground(Color.BLACK);

		gameObje.setBackground(Color.BLACK);
		gameObje.setForeground(Color.WHITE);
		gameObje.setEditable(false);
		gameObje.setWrapStyleWord(true);
		gameObje.setLineWrap(true);
		JScrollPane one = new JScrollPane(gameObje);
		one.setBounds(0, 0, multiPanels.getWidth(), multiPanels.getHeight());

		panelArea.setBackground(Color.BLACK);
		panelArea.setForeground(Color.WHITE);
		panelArea.setEditable(false);
		panelArea.setWrapStyleWord(true);
		panelArea.setLineWrap(true);
		JScrollPane two = new JScrollPane(panelArea);
		two.setBounds(0, 0, multiPanels.getWidth(), multiPanels.getHeight());

		howToArea.setBackground(Color.BLACK);
		howToArea.setForeground(Color.WHITE);
		howToArea.setEditable(false);
		howToArea.setWrapStyleWord(true);
		howToArea.setLineWrap(true);
		JScrollPane three = new JScrollPane(howToArea);
		three.setBounds(0, 0, multiPanels.getWidth(), multiPanels.getHeight());

		robots.setBackground(Color.BLACK);
		robots.setForeground(Color.WHITE);
		robots.setEditable(false);
		robots.setWrapStyleWord(true);
		robots.setLineWrap(true);
		JScrollPane four = new JScrollPane(robots);
		four.setBounds(0, 0, multiPanels.getWidth(), multiPanels.getHeight());
		
		costs.setBackground(Color.BLACK);
		costs.setForeground(Color.WHITE);
		costs.setEditable(false);
		JScrollPane five = new JScrollPane(costs);
		five.setBounds(0, 0, multiPanels.getWidth(), multiPanels.getHeight());

		multiPanels.setBackground(Color.BLACK);
		multiPanels.setForeground(Color.WHITE);
		multiPanels.addTab("game objective", one);
		multiPanels.addTab("game panels", two);
		multiPanels.addTab("how to's", three);
		multiPanels.addTab("more info", four);
		multiPanels.addTab("costs", five);
		multiPanels.setSize(680, 480);
		multiPanels.setLocation(10, 10);
		this.add(multiPanels);

		update();
	}

	public void update() {
		int tabNumber = multiPanels.getSelectedIndex();
		String tabSelected = "" + multiPanels.getTitleAt(tabNumber);

		gameObje.setText("GAME OBJECTIVE:\n\n\n"
				+ "TO WIN\n"
				+ "The objective of this game is to build up a population of robots who specialize "
				+ "in three areas and collect resources to build buildings and tools. To win, you "
				+ "must collect enough resources to build the Victory Monument.\n\n"
				+ "TO LOSE\n"
				+ "The game will be over and you will lose if there are no worker robots left. "
				+ "Without them, resources cannot be collected and building materials will get used "
				+ "up without a way for more to be collected. This means the Victory Monument cannot be built.\n\n"
				+ "TO SAVE\n"
				+ "To save the game, simply click the exit button at the top corner of the window. "
				+ "A pop up will appear asking if you would like to save. Click \"Yes\" if you want "
				+ "to save your progress or \"No\" if you don't. You will have the option to play "
				+ "the saved game when the game is run again.\n");

		panelArea
				.setText("PANELS:\n\n"
						+ "This is a brief overview of the different panels found in the game window.\n\n\n"
						+ "INFORMATION PANEL\n"
						+ "The information panel can be found on the far right side of the window. This "
						+ "is where you will perform most of your actions, such as collecting resources, "
						+ "building things, and fighting invading robots. In this panel you will also find "
						+ "the total amount of each resource held in all the buildings.\n\n"
						+ "NOTIFICATION PANEL\n"
						+ "The notification panel can be found at the bottom of the window. This is where "
						+ "you will see any hint messages and updates on your progress.\n\n"
						+ "TOP PANEL\n"
						+ "This panel displays the status of agents, buildings, and resources when you "
						+ "click on a specific one.\n\n"
						+ "\tAGENTS - for agents, the information displayed is the type of agent, \t\tthe "
						+ "amount of energy the agent has, the condition it is in, the amount \t\tof oil "
						+ "it has, and the amount of resources it is holding.\n"
						+ "\tBUILDING - for buildings, the information displayed is the building \t\ttype, "
						+ "the capacity of the building, and the amount currently stored in it.\n"
						+ "\tRESOURCES - for resources, the information displayed is the \t\t\tresource type "
						+ "and the amount left at that location.\n\n"
						+ "MINI MAP PANEL\n"
						+ "This panel displays a miniature version of the map. The black square is the map "
						+ "area that is currently visible to you in the game window. PLATING is light gray. "
						+ "GRAVEL is black. SAND is dark gray. SHALLOWS are gray. NORMAL ROBOTS are blue. "
						+ "BUILDINGS are red. RESOURCES are yellow. ENEMY ROBOTS are green.\n"
						+ "NOTE: this panel can only be accessed when the notification panel is not visible.\n");

		howToArea
				.setText("HOW TO:\n\n"
						+ "This is a brief overview of how to perform specific actions.\n\n\n"
						+ "COLLECT RESOURCES\n"
						+ "To collect a resource, click on the resource and then press the "
						+ "\"Collect Resource\" button at the top of the information panel.\n\n"
						+ "DEFEAT ENEMY ROBOTS\n"
						+ "To defeat an enemy robot, click on the enemy robot you want to attack "
						+ "and then press the \"Attack\" button at the top of the information panel.\n\n"
						+ "CREATE AGENT\n"
						+ "To create an agent, select \"agents\" from the drop down menu in the "
						+ "information panel and then select the type of agent you want to create. Once you "
						+ "have selected the agent type, click the \"Create/Build\" button.\n"
						+ "NOTE: if you want to go back to the buildings/agents/tools selection, click the "
						+ "\"BACK TO MAIN\" button.\n\n"
						+ "BUILD BUILDINGS\n"
						+ "To build a building, select \"buildings\" from the drop down menu in the "
						+ "information panel and then select the type of building you want to create. Once "
						+ "you have selected the building type, click the \"Create/Build\" button.\n"
						+ "NOTE: if you want to go back to the buildings/agents/tools selection, click the "
						+ "\"BACK TO MAIN\" button.\n\n"
						+ "BUILD TOOLS\n"
						+ "To build a too, select \"tools\" from the drop down menu in the information panel "
						+ "and then select the type of tool you want to create. Once you have selected the "
						+ "tool type, click the \"Create/Build\" button.\n"
						+ "NOTE: if you want to go back to the buildings/agents/tools selection, click the "
						+ "\"BACK TO MAIN\" button.\n");

		robots.setText("ROBOTS/RESOURCES/BUILDINGS:\n\n\n"
				+ "ROBOTS\n"
				+ "Robots have one of the following four purposes. Non-enemy robots "
				+ "have health costs whether they are idle or active. Enemy robots "
				+ "have no health restrictions.\n\n"
				+ "\tBUILDER\n"
				+ "\tBuilder robots are responsible for building all of the buildings that \t\tstore "
				+ "the resources.\n"
				+ "\tSOLDIER\n"
				+ "\tSoldier robots are responsible for fighting off enemy robots.\n"
				+ "\tWORKER\n"
				+ "\tWorker robots are responsible for collecting all the resources.\n"
				+ "\tENEMY\n"
				+ "\tEnemy robots steal resources from the buildings and will continue to \t\tdo so "
				+ "unless soldier robots attack and defeat them.\n\n"
				+ "RESOURCES\n"
				+ "Resources are in set places around the game map and can be collected by \n"
				+ "worker robots for various uses. The resources available are:\n"
				+ "ELECTRICITY*, OIL*, COAL, IRON, COPPER, GOLD\n"
				+ "*These resources are a part of robot health.\n\n"
				+ "BUILDINGS\n"
				+ "Buildings, with the exception of a few, are places to store resources. It takes \n"
				+ "a certain amount of resources to build each.\n\n"
				+ "\tARMORY\n"
				+ "\tArmories hold coal and iron.\n"
				+ "\tJUNKYARD\n"
				+ "\tJunkyards hold all of the metal resources as well as coal.\n"
				+ "\tOIL TANK\n"
				+ "\tOil Tanks hold oil.\n"
				+ "\tOIL WELL*\n"
				+ "\tOil Wells hold oil.\n"
				+ "\tWORKSHOP\n"
				+ "\tWorkshops hold tools.\n"
				+ "\tCHARGING STATION*\n"
				+ "\tCharging Stations hold electricity.\n"
				+ "\tHOME DEPOT\n"
				+ "\tThe Home Depots hold coal, iron, and gold.\n"
				+ "*These buildings do not strictly store resources.\n\n"
				+ "TOOLS\n"
				+ "Tools help robots do their tasks more efficiently. The tools "
				+ "available are:\n\n"
				+ "\tARMOR - Armor decreases the damage done by enemies.\n"
				+ "\tPICKAXE - Pickaxes allow worker robots to gather more resources.\n"
				+ "\tROCKETS - Rockets increase the robots’ speed.\n"
				+ "\tWELDING GUN - Welding guns increase the build rate of "
				+ "buildings.\n");
		
		costs.setText("COSTS:\n"
				+ "This shows the amount of each resource it takes to build or create something.\n\n\n"
				+ "Builder - 3,000 electricity and 4,000 iron\n\n"
				+ "Soldier - 3,000 electricity and 7,000 iron\n\n"
				+ "Worker - 4,000 electricity and 4,000 iron\n\n"
				+ "Enemy - randomly generated\n\n"
				+ "Armory - 5,000 iron and 100 electricity\n\n"
				+ "Charging Station - 10 coal, 100 copper, and 5,000 oil\n\n"
				+ "Home Depot - 5,000 gold, 1,000 electricity, and 10,000 iron\n\n"
				+ "Junkyard - 20,000 iron and 5,000 coal\n\n"
				+ "Oil Tank - 9,000 iron\n\n"
				+ "Oil Well - 500 coal, 5,000 iron, and 100 electricity\n\n"
				+ "Victory Monument - 100,000 every\n\n"
				+ "Workshop - 5,000 iron, 1,000 coal, and 6,000 gold\n\n"
				+ "Armor - 200 iron\n\n"
				+ "Pickaxe - 100 iron\n\n"
				+ "Rockets - 400 oil\n\n"
				+ "Welding Gun - 100 copper\n");
		costs.setSelectionStart(87);
		costs.insertIcon(new ImageIcon("./ImageSet/defender.png"));
		costs.setSelectionStart(132);
		costs.insertIcon(new ImageIcon("./ImageSet/distractor3.png"));
		costs.setSelectionStart(177);
		costs.insertIcon(new ImageIcon("./ImageSet/destroyer.png"));
		costs.setSelectionStart(221);
		costs.insertIcon(new ImageIcon("./ImageSet/Agent1.png"));
		costs.setSelectionStart(250);
		costs.insertIcon(new ImageIcon(BuildingType.ARMORY.getImage()));
		costs.setSelectionStart(292);
		costs.insertIcon(new ImageIcon(BuildingType.CHARGINGSTATION.getImage()));
		costs.setSelectionStart(348);
		costs.insertIcon(new ImageIcon(BuildingType.HOMEDEPOT.getImage()));
		costs.setSelectionStart(410);
		costs.insertIcon(new ImageIcon(BuildingType.JUNKYARD.getImage()));
		costs.setSelectionStart(450);
		costs.insertIcon(new ImageIcon(BuildingType.OILTANK.getImage()));
		costs.setSelectionStart(474);
		costs.insertIcon(new ImageIcon(BuildingType.OILWELL.getImage()));
		costs.setSelectionStart(529);
		costs.insertIcon(new ImageIcon(BuildingType.VICTORYMONUMENT.getImage()));
		costs.setSelectionStart(564);
		costs.insertIcon(new ImageIcon(BuildingType.WORKSHOP.getImage()));
//		costs.setSelectionStart(632);
//		costs.insertIcon(new ImageIcon("./ImageSet/defender.png"));
//		costs.setSelectionStart(632);
//		costs.insertIcon(new ImageIcon("./ImageSet/defender.png"));
//		costs.setSelectionStart(653);
//		costs.insertIcon(new ImageIcon("./ImageSet/defender.png"));
//		costs.setSelectionStart(673);
//		costs.insertIcon(new ImageIcon("./ImageSet/defender.png"));
	}
}
