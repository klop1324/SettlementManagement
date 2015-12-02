package model;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public enum Tile {
	//===============================
	//==> DO NOT SKIP NUMBERS!!!! <==
	//===============================
	PLATING(0, 'o',5.0, "Plating"),
	OIL(1, ' ', 5.0, "Oil"),
	SHALLOWS(2, 'S', 5.0, "Shallows"),
	GRAVEL(3, 'G', 5.0, "Gravel");
	
	private int intRep;
	private char charRep;
	private Image image;
	private double spawnRate;
	private String name;
	
	Tile(int i, char c, double chance, String name){
		this.intRep = i;
		this.charRep = c;
		this.name = name;
		this.spawnRate = chance;
		try {
			this.image = ImageIO.read(new File("ImageSet/tile-"+ name +".png"));
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
