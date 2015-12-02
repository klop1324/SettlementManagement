package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

public class HelpMenu extends JFrame {

	private JTabbedPane multiPanels = new JTabbedPane();
	private JTextArea gameObje = new JTextArea();
	private JTextArea panelArea = new JTextArea();
	private JTextArea howToArea = new JTextArea();
	private JTextArea robots = new JTextArea();

	public static void main(String[] args) {
		(new HelpMenu()).setVisible(true);
	}

	public HelpMenu() {
		// this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setTitle("Helpful Information");
		this.setMinimumSize(new Dimension(550, 350));
		this.setMaximumSize(new Dimension(550, 350));
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = screensize.width / 2 - this.getSize().width / 2;
		int height = screensize.height / 2 - this.getSize().height / 2;
		this.setLocation(width, height);
		this.setForeground(Color.BLACK);

		// gameObje.setBounds(0, 0, multiPanels.getWidth(),
		// multiPanels.getHeight());
		gameObje.setBackground(Color.BLACK);
		gameObje.setForeground(Color.WHITE);
		gameObje.setEditable(false);
		JScrollPane one = new JScrollPane(gameObje);
		one.setBounds(0, 0, multiPanels.getWidth(), multiPanels.getHeight());

		// panelArea.setBounds(0, 0, multiPanels.getWidth(),
		// multiPanels.getHeight());
		panelArea.setBackground(Color.BLACK);
		panelArea.setForeground(Color.WHITE);
		panelArea.setEditable(false);
		JScrollPane two = new JScrollPane(panelArea);
		two.setBounds(0, 0, multiPanels.getWidth(), multiPanels.getHeight());

		// howToArea.setBounds(0, 0, multiPanels.getWidth(),
		// multiPanels.getHeight());
		howToArea.setBackground(Color.BLACK);
		howToArea.setForeground(Color.WHITE);
		howToArea.setEditable(false);
		JScrollPane three = new JScrollPane(howToArea);
		three.setBounds(0, 0, multiPanels.getWidth(), multiPanels.getHeight());

		// robots.setBounds(0, 0, multiPanels.getWidth(),
		// multiPanels.getHeight());
		robots.setBackground(Color.BLACK);
		robots.setForeground(Color.WHITE);
		robots.setEditable(false);
		JScrollPane four = new JScrollPane(robots);
		four.setBounds(0, 0, multiPanels.getWidth(), multiPanels.getHeight());

		multiPanels.setBackground(Color.BLACK);
		multiPanels.setForeground(Color.WHITE);
		multiPanels.addTab("game objective", one);
		multiPanels.addTab("game panels", two);
		multiPanels.addTab("how to's", three);
		multiPanels.addTab("robots/resources/buildings", four);
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
				+ "The objective of this game is to build up a population of robots who specialize \n"
				+ "in three areas and collect resources to build buildings and tools. To win, you \n"
				+ "must collect enough resources to upgrade the Home Base to level six.\n\n"
				+ "TO LOSE\n"
				+ "The game will be over and you will lose if there are no worker robots left. \n"
				+ "Without them, resources cannot be collected and building materials will get used \n"
				+ "up without a way for more to be collected. This means the last level of the Home \n"
				+ "Base cannot be reached.\n\n"
				+ "TO SAVE\n"
				+ "To save the game, simply click the exit button at the top corner of the window. \n"
				+ "A pop up will appear asking if you would like to save. Click \"Yes\" if you want \n"
				+ "to save your progress or \"No\" if you don't. You will have the option to play \n"
				+ "the saved game when the game is run again.\n");

		panelArea
				.setText("PANELS:\n\n"
						+ "This is a brief overview of the different panels found in the game window.\n\n\n"
						+ "INFORMATION PANEL\n"
						+ "The information panel can be found on the far right side of the window. This \n"
						+ "is where you will perform most of your actions, such as collecting resources, \n"
						+ "building things, and fighting invading robots. In this panel you will also find \n"
						+ "the total amount of each resource held in all the buildings.\n\n"
						+ "NOTIFICATION PANEL\n"
						+ "The notification panel can be found at the bottom of the window. This is where \n"
						+ "you will see any hint messages and updates on your progress.\n\n"
						+ "TOP PANEL\n"
						+ "This panel displays the status of agents, buildings, and resources when you \n"
						+ "click on a specific one.\n\n"
						+ "    AGENTS - for agents, the information displayed is the type of agent, the \n"
						+ "    amount of energy the agent has, the condition it is in, the amount of oil \n"
						+ "    it has, and the amount of resources it is holding.\n"
						+ "    BUILDING - for buildings, the information displayed is the building type, \n"
						+ "    the capacity of the building, and the amount currently stored in it.\n"
						+ "    RESOURCES - for resources, the information displayed is the resource type \n"
						+ "    and the amount left at that location.\n");

		howToArea
				.setText("HOW TO:\n\n"
						+ "This is a brief overview of how to perform specific actions.\n\n\n"
						+ "COLLECT RESOURCES\n"
						+ "To collect a resource, you must click on the resource and then press the \n"
						+ "\"Collect Resource\" button at the top of the information panel.\n\n"
						+ "DEFEAT ENEMY ROBOTS\n"
						+ "To defeat an enemy robot, you must click on the enemy robot you want to attack \n"
						+ "and then press the \"Attack\" button at the top of the information panel.\n\n"
						+ "PERFORM MAINTENANCE\n"
						+ "To perform maintenance on a robot, you must click on the robot you want to \n"
						+ "perform maintenance on and then click the \"Fix\" button at the top of the \n"
						+ "information panel.\n\n"
						+ "CREATE AGENT\n"
						+ "To create an agent, you must select \"agents\" from the drop down menu in the \n"
						+ "information panel and then select the type of agent you want to create. Once you \n"
						+ "have selected the agent type, you must click the \"Create/Build\" button.\n"
						+ "    Note: if you want to go back to the agents/buildings selection, click the \n"
						+ "    \"BACK TO MAIN\" button.\n\n"
						+ "BUILD BUILDINGS\n"
						+ "To build a building, you must select \"buildings\" from the drop down menu in the \n"
						+ "information panel and then select the type of building you want to create. Once \n"
						+ "you have selected the building type, you must click the \"Create/Build\" button.\n"
						+ "    Note: if you want to go back to the agents/buildings selection, click the \n"
						+ "    \"BACK TO MAIN\" button.\n");

		robots.setText("ROBOTS/RESOURCES/BUILDINGS\n\n\n"
				+ "ROBOTS\n"
				+ "Robots have different one of the following four purposes. When non-enemy robots \n"
				+ "are idle or active, there is a deduction on their health.\n\n"
				+ "    BUILDER\n"
				+ "    Builder robots are responsible for building all of the building that store \n"
				+ "    the resources.\n"
				+ "    SOLDIER\n"
				+ "    Soldier robots are responsible for fighting off enemy robots.\n"
				+ "    WORKER\n"
				+ "    Worker robots are responsible for collecting all the resources.\n"
				+ "    ENEMY\n"
				+ "    Enemy robots steal resources from the buildings and will continue to do so \n"
				+ "    unless soldier robots attack and defeat them. They do not have health \n"
				+ "    restrictions.\n\n"
				+ "RESOURCES\n"
				+ "    Resources are in set places around the game map and can be collected by \n"
				+ "    worker robots for various uses. The resources available are:\n"
				+ "    ELECTRICITY/ENERGY*, OIL*, COAL, IRON, COPPER, GOLD\n"
				+ "    *These resources are a part of robot health.\n\n"
				+ "BUILDINGS\n"
				+ "Buildings, with the exception of a few, are places to store resources. It takes \n"
				+ "a certain amount of resources to build each.\n\n"
				+ "    JUNKYARD\n"
				+ "    Junkyards hold all of the metal resources as well as coal.\n"
				+ "    OIL TANK\n"
				+ "    Oil tank hold oil.\n"
				+ "    OIL WELL*\n"
				+ "    Oil wells need to be built by oil in order for the oil to be collected by \n"
				+ "    worker robots.\n"
				+ "    WORK SHOP*\n"
				+ "    Work shops take resources and transform them into tools that help robots do \n"
				+ "    their tasks with more efficiency.\n"
				+ "        TOOLS\n"
				+ "        List types of tools.\n"
				+ "    CHARGING STATION\n"
				+ "    Charging stations hold electricity/energy.\n"
				+ "    HOME BASE*\n"
				+ "    The Home Base can only be built once. It is can be placed at the start of \n"
				+ "    the game when you have acquired enough resources. The Home Base is where \n"
				+ "    agents are created and is the building that needs to be upgraded in order to \n"
				+ "    win the game. It can be upgraded when you have the necessary amount of \n"
				+ "    resources.\n"
				+ "*These buildings do not strictly store resources.\n");

	}
}
