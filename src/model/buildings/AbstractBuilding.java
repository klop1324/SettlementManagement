package model.buildings;

import java.awt.Point;

import model.resources.Resources;

public abstract class AbstractBuilding {
	protected String name;
	protected int capacity;
	protected boolean passiveProvider;
	protected double passiveRate;
	protected Resources resourceType;
	protected Point location;
	
	public AbstractBuilding(String name, int capacity, boolean passiveProvider, double passiveRate, Resources resourceType, Point location){
		this.name = name;
		this.capacity = capacity;
		this.passiveProvider = passiveProvider;
		this.passiveRate = passiveRate;
		this.resourceType = resourceType;
		this.location = location;
	}
	
	public String getName(){
		return name;
	}
	
	public int getCapacity(){
		return capacity;
	}
	
	public boolean isPassiveProvider(){
		return passiveProvider;
	}
	
	public double getPassiveRate(){
		return passiveRate;
	}
	
	public Point getLocation(){
		return location;
	}
	
	public Resources getResourceType(){
		return resourceType;
	}
	
	public abstract void doBuildingJob();
	
	public void removeCapacity(int amount){
		capacity -= amount;
	}
	
	public void addCapacity(int amount){
		capacity += amount;
	}
	
}
