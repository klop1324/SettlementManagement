package model.buildings;

import java.awt.Image;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;

import model.resources.ResourceType;

public enum BuildingType {
	ARMORY("Armory", 0, false, 0.0, null, 20*20,ResourceType.ELECTRICITY, .1),
	SOLARPANEL("Solar Panel", 0, true, 2.0, ResourceType.ELECTRICITY, 10*10,null, 0.0),
	CHARGINGSTATION("Charging Station", 25000, false, 0.0, null, 20*10,null, 0.0, ResourceType.ELECTRICITY),
	HOMEDEPOT("Home Depot", 25000, true, 1.0, ResourceType.ELECTRICITY, 20*60,ResourceType.ELECTRICITY, .2, ResourceType.COAL, ResourceType.IRON, ResourceType.GOLD),
	JUNKYARD("JunkYard", 50000, false, 0.0, null, 20*10,null, 0.0, ResourceType.COAL, ResourceType.COPPER, ResourceType.GOLD, ResourceType.IRON),
	OILTANK("Oil Tank", 25000, false, 0.0, null, 20*20,ResourceType.ELECTRICITY, 0.0, ResourceType.OIL),
	OILWELL("Oil Well", 0, true, 1.0, ResourceType.OIL, 20*60,ResourceType.ELECTRICITY, 1.0),
	WORKSHOP("Workshop", 500, false, 0.0, null, 20*20,ResourceType.ELECTRICITY, .1, ResourceType.TOOL);

	// general stuff
	private String name;
	private ArrayList<ResourceType>holdableResources;
	private int startingCapacity;
	private int buildTime;
	private ResourceType maintCostType;
	private double maintCost;
	//passive provider stuffs
	private boolean isPassiveProvider;
	private double passiveRate;
	private ResourceType passiveResource;
	
	
	
	BuildingType(String name, int startingCapacity, boolean isPassiveProvider,
			double passiveRate, ResourceType passiveResource, int buildTime,ResourceType maintCostType, double maintCost, ResourceType... holdableResources){
		this.name = name;
		this.startingCapacity = startingCapacity;
		this.isPassiveProvider = isPassiveProvider;
		this.passiveRate = passiveRate;
		this.passiveResource = passiveResource;
		this.buildTime = buildTime;
		this.maintCostType = maintCostType;
		this.maintCost = maintCost;
		this.holdableResources = new ArrayList<ResourceType>();
		for(ResourceType resource : holdableResources){
			this.holdableResources.add(resource);
		}
		
		
	}
	
	public String getName(){
		return name;
	}
	
	public int getInitialBuildTime(){
		return buildTime;
	}
	
	public double getInitialPassiveRate(){
		return passiveRate;
	}
	
	public ResourceType getPassiveResource(){
		return passiveResource;
	}
	
	public ArrayList<ResourceType> getHoldableResources(){
		return holdableResources;
	}

	public int getStartingCapacity() {
		return startingCapacity;
	}

	public boolean isPassiveProvider() {
		return isPassiveProvider;
	}
	
	public double getInitialMaitenenceCost(){
		return maintCost;
	}
	public ResourceType getMaintCostType(){
		return maintCostType;
	}

	public Image getImage() {
		// TODO Auto-generated method stub
		return null;
	}
}
