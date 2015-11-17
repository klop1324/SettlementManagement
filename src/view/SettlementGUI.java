package view;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.TextArea;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

class SettlementGUI extends JFrame implements Observer {

	private GraphicalView mapArea = new GraphicalView();
	// private TextArea mapArea = new TextArea();
	private TextArea notificationArea = new TextArea();
	// ButtonGroup
	private JButton collectEnergy = new JButton("Collect Energy");
	private JButton collectOil = new JButton("Collect Oil");
	private JButton nextButton = new JButton(">>");
	private JLabel energyAmount = new JLabel("Energy: ");
	private JLabel oilAmount = new JLabel("Oil: ");
	// comboBox with container and int size
	// add keyListener and mouseMotionListener for the map

	public static void main(String[] args) {
		(new SettlementGUI()).setVisible(true);
	}

	public SettlementGUI() {

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setTitle("Game Name Goes Here");

		this.setMinimumSize(new Dimension(800, 600));
		this.setMaximumSize(new Dimension(800, 600));
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = screensize.width / 2 - this.getSize().width / 2;
		int height = screensize.height / 2 - this.getSize().height / 2;
		this.setLocation(width, height);

		JLabel mapLabel = new JLabel("the map goes here");
		mapLabel.setLocation(250, 40);
		mapLabel.setSize(150, 50);
		
		JPanel mapPanel = new JPanel();
		mapPanel.setBackground(Color.GRAY);
		mapPanel.add(mapArea);

		JPanel notifierPanel = new JPanel();
		notifierPanel.setBorder(new TitledBorder("Notifications"));
		notifierPanel.setBackground(Color.DARK_GRAY);
		notificationArea.setBackground(Color.BLACK);
		notificationArea.setPreferredSize(new Dimension(700, 150));
		notificationArea.setEditable(false);
		notifierPanel.add(notificationArea);
		//nextButton.setBackground(Color.);
		notifierPanel.add(nextButton);

		JPanel infoPanel = new JPanel();
		infoPanel.setPreferredSize(new Dimension(200, 40));
		infoPanel.setLayout(new GridLayout(3, 1));
		infoPanel.setBorder(new TitledBorder("Information"));
		infoPanel.setBackground(Color.BLACK);
		JPanel collectPanel = new JPanel();
		collectPanel.setBackground(Color.BLACK);
		collectPanel.add(collectEnergy);
		collectPanel.add(collectOil);
		infoPanel.add(collectPanel);
		infoPanel.add(energyAmount);
		infoPanel.add(oilAmount);

		registerListeners();

		this.add(mapLabel);
		this.add(mapPanel);
		this.add(notifierPanel, BorderLayout.SOUTH);
		this.add(infoPanel, BorderLayout.EAST);
	}

	public void registerListeners() {

		collectEnergy.addActionListener(new CollectEnergyListener());
		collectOil.addActionListener(new CollectOilListener());
		nextButton.addActionListener(new NextButtonListener());
	}

	private class CollectEnergyListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {

		}
	}

	private class CollectOilListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {

		}
	}

	private class NextButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {

		}
	}

	@Override
	public void update(Observable o, Object arg) {

	}
}