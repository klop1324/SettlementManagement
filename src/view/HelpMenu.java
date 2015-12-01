package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

public class HelpMenu extends JFrame {

	private JTabbedPane multiPanels = new JTabbedPane();
	private JTextArea gameObje = new JTextArea();
	private JTextArea infoArea = new JTextArea();
	private JTextArea notifyArea = new JTextArea();

	public static void main(String[] args) {
		(new HelpMenu()).setVisible(true);
	}

	public HelpMenu() {
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setTitle("Game Name Goes Here");
		this.setMinimumSize(new Dimension(700, 500));
		this.setMaximumSize(new Dimension(700, 500));
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = screensize.width / 2 - this.getSize().width / 2;
		int height = screensize.height / 2 - this.getSize().height / 2;
		this.setLocation(width, height);
		this.setForeground(Color.BLACK);

		gameObje.setBounds(0, 0, multiPanels.getWidth(),
				multiPanels.getHeight());
		gameObje.setBackground(Color.BLACK);
		gameObje.setForeground(Color.WHITE);
		gameObje.setEditable(false);
		//gameObje.setLineWrap(true);

		infoArea.setBounds(0, 0, multiPanels.getWidth(),
				multiPanels.getHeight());
		infoArea.setBackground(Color.BLACK);
		infoArea.setForeground(Color.WHITE);
		infoArea.setEditable(false);

		notifyArea.setBounds(0, 0, multiPanels.getWidth(),
				multiPanels.getHeight());
		notifyArea.setBackground(Color.BLACK);
		notifyArea.setForeground(Color.WHITE);
		notifyArea.setEditable(false);

		multiPanels.setBackground(Color.BLACK);
		multiPanels.setForeground(Color.WHITE);
		multiPanels.addTab("game objective", gameObje);
		multiPanels.addTab("information panel", infoArea);
		multiPanels.addTab("notification panel", notifyArea);
		multiPanels.setSize(680, 480);
		multiPanels.setLocation(10, 10);
		this.add(multiPanels);

		update();
	}

	public void update() {
		int tabNumber = multiPanels.getSelectedIndex();
		String tabSelected = "" + multiPanels.getTitleAt(tabNumber);

		gameObje.setText("GAME OBJECTIVE:\n\nThe objective of this game is to grow your " + 
		"population and gather \nresources until you are able to build the final version of" + 
				" the building.");
	}
}
