package model;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public enum Tile {
	//===============================
	//==> DO NOT SKIP NUMBERS!!!! <==
	//===============================
	PLATING(0, 'o',5.0, "Plating",true),
	GRAVEL(1, 'G', 5.0, "Gravel", true),
	QUICKSAND(2, ' ', 2.0, "Sand", true),
	SHALLOWS(3, 'S', 5.0, "Shallows", false);

	
	private int intRep;
	private char charRep;
	private Image image;
	private double spawnRate;
	private String name;
	private Boolean isPassable;
	private static int tileSize = 50;
	
	Tile(int i, char c, double chance, String name, Boolean isPassible){
		this.intRep = i;
		this.charRep = c;
		this.name = name;
		this.spawnRate = chance;
		this.isPassable = isPassible;
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
	public Boolean isPassible(){
		return this.isPassable;
	}
	public static int getTileSize(){
		return tileSize;
	}
}
