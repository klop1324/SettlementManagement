package model.buildings;

import java.awt.Image;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;

import model.resources.ResourceType;

public enum BuildingType {
	ARMORY("Armory"),
	SOLARPANEL("Solar Panel"),
	CHARGINGSTATION("Charging Station"),
	HOMEDEPOT("Home Depot"),
	JUNKYARD("JunkYard"),
	OILTANK("Oil Tank"),
	OILWELL("Oil Well"),
	WORKSHOP("Workshop");

	// general stuff
	private Image image;
	private String name;
	private Image unBuilt;
	
	
	
	private BuildingType(String name){
		this.name = name;
		try {
			this.image = ImageIO.read(new File("ImageSet/building/"+ name +".png"));
			this.unBuilt = ImageIO.read(new File("ImageSet/building/rubble.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getName(){
		return name;
	}

	public Image getImage() {
		return image;
	}
	
	public Image getUnbuiltImage(){
		return unBuilt;
	}
}
