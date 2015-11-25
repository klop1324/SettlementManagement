package model;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public enum Tile {
	//===============================
	//==> DO NOT SKIP NUMBERS!!!! <==
	//===============================
	PLATING(0, 'o',10.0), OIL(1, ' ', 1.0), GRAVEL(2, 'G', 5.0);
	
	private int intRep;
	private char charRep;
	private Image image;
	private double spawnRate;
	
	Tile(int i, char c, double chance){
		this.intRep = i;
		this.charRep = c;
		this.spawnRate = chance;
		try {
			this.image = ImageIO.read(new File("ImageSet/tile-"+ this.intRep +".png"));
		} catch (IOException e) {
			e.printStackTrace();
			//Tile.values()[int]
		}
		
	}
	public int getIntRepresentation(){
		return intRep;
	}
	public char getCharRepresentation(){
		return charRep;
	}
	public Image getImage(){
		return image;
	}
	public double getSpawnRate(){
		return spawnRate;
	}
}
