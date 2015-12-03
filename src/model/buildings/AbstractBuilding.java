package model.buildings;

import java.awt.Image;
import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import model.resources.ResourceType;



public abstract class AbstractBuilding implements Serializable{
	
	// general stuff
	protected String name;
	protected int capacity;
	protected Point location;
	protected ArrayList<ResourceType> holdableResources;
	protected HashMap<ResourceType, Integer> currentAmount;
	
	//Build state stuff
	protected int buildTime;
	protected int buildCompletion = 0;
	protected int version = 1;
	protected boolean isBuilt = false;
	
	//Maintenence stuff
	protected ResourceType maintCostType;
	protected double maintCost;
	
	//passive provider stuffs
	protected boolean isPassiveProvider;
	protected double passiveRate;
	protected ResourceType passiveResource;
	
	//type of building for image getting
	protected BuildingType buildingType;
	
	public AbstractBuilding(Point location, int startingCapacity, boolean isPassiveProvider,
			double passiveRate, ResourceType passiveResource, int buildTime, double maintCost, BuildingType buildingType){
	
		this.buildingType = buildingType;
		this.name = buildingType.getName();
		this.capacity = startingCapacity;
		this.location = location;
		this.isPassiveProvider = isPassiveProvider;
		this.passiveRate = passiveRate;
		this.passiveResource = passiveResource;
		this.buildTime = buildTime;
		this.holdableResources = new ArrayList<ResourceType>();
		this.currentAmount = new HashMap<ResourceType, Integer>();
		
		this.maintCostType = ResourceType.ELECTRICITY;
		this.maintCost = maintCost;
		
	}
	

	public int getBuildTime(){
		return buildTime;
	}

	public void incrementCompletionAmount(int n){
		buildCompletion+=n;
	}

	public boolean isCompleted(){
		if (buildCompletion == 100){
			return true;
		}
		else {
			return false;
		}
	}

	public int getCompletionAmount(){
		return buildCompletion;
	}
	
	public int getVersion(){
		return version;
	}

	public String getName(){
		return name;
	}

	public int getCapacity(){
		return capacity;
	}

	public boolean isPassiveProvider(){
		return isPassiveProvider;
	}

	public double getPassiveRate(){
		return passiveRate;
	}

	public Point getLocation(){
		return location;
	}
	
	public ArrayList<ResourceType> getResources(){
		return holdableResources;
	}
	
	public void addResource(ResourceType resource, int amount){
		if(!holdableResources.contains(resource)){
			throw new RuntimeException("does not contain This Resource!");
		}
		else{
			currentAmount.replace(resource, currentAmount.get(resource) + amount);
		}
	}
	public void removeResource(ResourceType resource, int amount){
		if(!holdableResources.contains(resource)){
			throw new RuntimeException("does not contain This Resource!");
		}
		else{
			int result = currentAmount.get(resource) - amount;
			if(result > capacity) throw new RuntimeException("Cannot Add Resource! We would go over Capacity!");
			currentAmount.replace(resource, result);
		}
	}
	
	public int getResourceAmount(ResourceType resource){
		if(currentAmount.get(resource)== null)throw new RuntimeException("This Building does not hold this Resource!");
		else{
			return currentAmount.get(resource);
		}
	}

	// Allows agents to remove amount of resources from building
	public void agentRemoveCapacity(ResourceType resource, int amount) {
		int newResourceAmount = currentAmount.get(resource) - amount;
		if(newResourceAmount < 0)throw new RuntimeException("Cannot remove that many resources!");
		else{
			currentAmount.replace(resource, newResourceAmount);
		}
	}

	// Allows agents to add their resource to the building
	public void agentAddCapacity(ResourceType resource, int amount) {
		int newResourceAmount = currentAmount.get(resource) + amount;
		if(newResourceAmount < 0)throw new RuntimeException("Added too much resources!");
		else{
			currentAmount.replace(resource, newResourceAmount);
		}
	}


	public boolean canInsert(ResourceType resource, int amount){
		int total = currentAmount.get(resource)+ amount;
		if(total > capacity)return false;
		else{
			return true;
		}	
	}
	
	public Image getImage(){
		return buildingType.getImage();
	}
	
	public abstract void upgrade();


	public BuildingType getType() {
		return buildingType;
	}
}
