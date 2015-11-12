package view;
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

public class GraphicalView extends JPanel implements Observer {

	// private Game game = new Game();
	private Image terrain, water, tree, rock;
	private Timer timer;
	private int tic;

	// add mouseListener and mouseMotionListener

	public GraphicalView() {

		/*try {
			terrain = ImageIO.read(new File("./images/terrain.png"));
			water = ImageIO.read(new File("./images/water.png"));
			tree = ImageIO.read(new File("./images/tree.png"));
			rock = ImageIO.read(new File("./images/rock.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		repaint();
	}

	@Override
	public void update(Observable o, Object arg) {

		// game = (Game) o;
		drawBoardWithAnimation();
	}

	private void drawBoardWithAnimation() {

		tic = 1;
		timer = new Timer(40, new TimerListener());
		timer.start();
	}

	private class TimerListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

		}
	}

	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		for (int i = 0; i < 300; i += 30) {
			for (int j = 0; j < 300; j += 30) {
				//g2.drawImage(terrain, i, j, null);
			}
		}
		// g2.drawImage();
	}
}