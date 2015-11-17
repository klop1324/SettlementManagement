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
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;

class SettlementGUI extends JFrame implements Observer {

	//private GraphicalView mapArea = new GraphicalView();
	// private TextArea mapArea = new TextArea();
	private ViewController mapArea = new ViewController();
	private TextArea notificationArea = new TextArea();
	// ButtonGroup
	private JButton collectButton = new JButton("Collect Resource");
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
		//mapPanel.add(mapArea);

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
		collectPanel.add(collectButton);
		infoPanel.add(collectPanel);
		infoPanel.add(energyAmount);
		infoPanel.add(oilAmount);
		
		JScrollPane cs = new JScrollPane(mapArea);
		cs.setPreferredSize(new Dimension(this.getSize()));
		cs.setVisible(false);
		this.add(cs);

		registerListeners();

		//this.add(mapLabel);
		//this.add(mapPanel);
		//this.add(mapArea);
		this.add(notifierPanel, BorderLayout.SOUTH);
		this.add(infoPanel, BorderLayout.EAST);
	}

	public void registerListeners() {

		collectButton.addActionListener(new CollectButtonListener());
		nextButton.addActionListener(new NextButtonListener());
	}

	private class CollectButtonListener implements ActionListener {

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