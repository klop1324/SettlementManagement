package view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

import model.Game;
import model.Map;
import model.Tile;

public class ViewController extends JPanel implements Observer{
	private Game game;
	private Image image;
	private int x;
	private int y;
	private Timer timer = new Timer(50, new TimerListener());
	private int tic;
	
	public ViewController(){
		this.setVisible(true);
	}
	
	public ViewController(Game game){
		this.game = game;

		try {
			image = ImageIO.read(new File("./ImageSet/0.png"));
			System.out.println("This is running");
		} catch (IOException e) {
			e.printStackTrace();
		}
		repaint();
	}
	
	private class TimerListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			repaint();
		}
	}
	
	public void PaintComponent(Graphics g){
		paintMap(g, game.getMap());
	}
	
	private void paintMap(Graphics g, Map map){
		//draws each tile, based on map representation, getting image from the Tile enum
		Graphics g2 = (Graphics2D) g;
		for(int i = 0; i < map.getXLength(); i++){
			for(int j = 0; j < map.getYLength(); j++){
				//g2.drawImage(Tile.values()[map.get(i,j)].getImage(), i, j, null);
				g2.drawImage(image, i*50, j*50, null);
				//TODO paint all the tiles
			}
		}
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		drawBoardWithAnimation();
	}
	
	private void drawBoardWithAnimation() {
		tic = 0;
		timer.start();
	}	
}
