package controllers;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import model.Game;
import model.Map;
import model.Tile;

public class ViewController extends JPanel{
	Game game;
	
	public ViewController(){
		this.setVisible(true);
	}
	
	public ViewController(Game game){
		this.game = game;
	}
	
	public void PaintComponent(Graphics g){
		paintMap(g, game.getMap());
	}
	
	private void paintMap(Graphics g, Map map){
		for(int i = 0; i < map.getXLength(); i++){
			for(int j = 0; j < map.getYLength(); j++){
				//draws each tile, based on map representation, getting image from the Tile enum
				Graphics g2 = (Graphics2D) g;
				g2.drawImage(Tile.values()[map.get(i,j)].getImage(), i, j, null);
				//TODO paint all the tiles
			}
		}
	}
}
