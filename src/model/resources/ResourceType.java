package model.resources;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public enum ResourceType {
	
	COAL(0,true, "coal"),
	COPPER(1,true, "copper"),
	ELECTRICITY(2,true, "solar"),
	GOLD(3,true, "gold"),
	IRON(4,true, "iron"),
	OIL(5,true, "oil"),
	TOOL(6,false);
	
	private int value;
	private boolean spawns;
	private Image image;

	ResourceType(int value, boolean spawns){
		this.value = value;
		this.spawns = spawns;
	}
	ResourceType(int value, boolean spawns, String name){
		this.value = value;
		this.spawns = spawns;
		try {
			this.image = ImageIO.read(new File("ImageSet/resource/"+ name +".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Image getImage(){
		return image;
	}
	
	public int getValue(){
		return value;
	}
	public boolean isSpawnable(){
		return spawns;
	}
	
}
