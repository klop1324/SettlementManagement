package view;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.TextArea;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javafx.scene.control.ComboBox;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;

import model.*;

class SettlementGUI extends JFrame implements Observer {

	private ViewController mapArea = new ViewController(new Game());
	private TextArea notificationArea = new TextArea();
	// ButtonGroup
	private JButton collectButton = new JButton("Collect Resource");
	private JButton nextButton = new JButton(">>");
	private JButton oneButton = new JButton(">");
	private JButton twoButton = new JButton("v");
	private JLabel electricityAmount = new JLabel("Electricity: ");
	private JLabel oilAmount = new JLabel("Oil: ");	
	private JLabel coalAmount = new JLabel("Coal: ");
	private JLabel copperAmount = new JLabel("Copper: ");
	private JLabel ironAmount = new JLabel("Iron: ");
	private JLabel goldAmount = new JLabel("Gold: ");
	JPanel notifierPanel = new JPanel();
	JPanel infoPanel = new JPanel();
	private int one = 0;
	private int two = 0;
	// comboBox with container and int size
	// add keyListener and mouseMotionListener for the map

	public static void main(String[] args) {
		(new SettlementGUI()).setVisible(true);
	}

	public SettlementGUI() {

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setTitle("Game Name Goes Here");
		//this.setLayout(null);
		this.setMinimumSize(new Dimension(800, 600));
		this.setMaximumSize(new Dimension(800, 600));
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = screensize.width / 2 - this.getSize().width / 2;
		int height = screensize.height / 2 - this.getSize().height / 2;
		this.add(new JLabel("Hi"));
		this.setLocation(width, height);
		
		Font courier = new Font("Courier", Font.PLAIN, 12);

		//JPanel notifierPanel = new JPanel();
		TitledBorder noticeBorder = new TitledBorder("Notifications");
		noticeBorder.setTitleColor(Color.WHITE);
		notifierPanel.setOpaque(true);
		notifierPanel.setBorder(noticeBorder);
		notifierPanel.setBackground(Color.DARK_GRAY);
		notifierPanel.setPreferredSize(new Dimension(800, 190));
		notifierPanel.setLocation(0, 440);
		notificationArea.setBackground(Color.BLACK);
		notificationArea.setPreferredSize(new Dimension(700, 150));
		notificationArea.setEditable(false);
		notificationArea.setFont(courier);
		notificationArea.setForeground(Color.GREEN);
		notificationArea.setText("solar panel = solar panel \nblood = oil \nslime pit" + ""
				+ " = oil tank \nslime = charging station \nwumpus = soldier agent \n" + 
				"hunter = worker agent");
		notifierPanel.add(notificationArea);
		notifierPanel.add(nextButton);
		
		electricityAmount.setForeground(Color.WHITE);
		oilAmount.setForeground(Color.WHITE);
		coalAmount.setForeground(Color.WHITE);
		copperAmount.setForeground(Color.WHITE);
		ironAmount.setForeground(Color.WHITE);
		goldAmount.setForeground(Color.WHITE);

		//JPanel infoPanel = new JPanel();
		TitledBorder infoBorder = new TitledBorder("Information");
		infoBorder.setTitleColor(Color.WHITE);
		infoPanel.setOpaque(true);
		infoPanel.setPreferredSize(new Dimension(150, 400));
		infoPanel.setLayout(new GridLayout(7, 1));
		infoPanel.setBorder(infoBorder);
		infoPanel.setBackground(Color.BLACK);
		infoPanel.setLocation(650, 0);
		JPanel collectPanel = new JPanel();
		collectPanel.setOpaque(true);
		collectPanel.setBackground(Color.BLACK);
		collectPanel.add(collectButton);
		infoPanel.add(collectPanel);
		infoPanel.add(electricityAmount);
		infoPanel.add(oilAmount);	
		infoPanel.add(coalAmount);
		infoPanel.add(copperAmount);
		infoPanel.add(ironAmount);
		infoPanel.add(goldAmount);
		
		registerListeners();
		
		JScrollPane cs = new JScrollPane(mapArea);
		cs.setPreferredSize(new Dimension(this.getSize()));
		this.add(cs);
		
		//mapArea.repaint();

		//this.add(mapArea);
		//this.add(oneButton);
		//this.add(twoButton);
		//this.add(notifierPanel);
		//this.add(infoPanel);
		this.add(notifierPanel, BorderLayout.SOUTH);
		this.add(infoPanel, BorderLayout.EAST);
	}

	public void registerListeners() {

		collectButton.addActionListener(new CollectButtonListener());
		nextButton.addActionListener(new NextButtonListener());
		oneButton.addActionListener(new OneButtonListener());
		twoButton.addActionListener(new TwoButtonListener());
	}
	
	private class OneButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			one++;
			if (one % 2 != 0) {
				infoPanel.setVisible(false);
				oneButton.setText("<");
			}
			else {
				infoPanel.setVisible(true);
				oneButton.setText(">");
			}
		}
	}
	
	private class TwoButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			two++;
			if (two % 2 != 0) {
				notifierPanel.setVisible(false);
				twoButton.setText("^");
			}
			else {
				notifierPanel.setVisible(true);
				twoButton.setText("v");
			}
		}
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