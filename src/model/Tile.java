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
	OIL(1, ' ', 5.0, "Oil", false),
	SHALLOWS(2, 'S', 5.0, "Shallows", true),
	GRAVEL(3, 'G', 5.0, "Gravel", false);
	
	private int intRep;
	private char charRep;
	private Image image;
	private double spawnRate;
	private String name;
	private Boolean isPassable;
	
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
}
