package model.buildings;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.Timer;

import model.resources.ResourceType;



public class Building implements Serializable{
	private String name;
	private BuildingType building;
	private int capacity;
	private boolean isBuilt;
	private boolean passiveProvider = false;
	private double passiveRate;
	private ResourceType passiveResource;
	private Point location;
	private ArrayList<ResourceType> holdableResources;
	private HashMap<ResourceType, Integer> currentAmount;
	private int version;
	private int buildTime;
	private int buildCompletion;
	private HashMap<ResourceType, Integer> cost;

	public Building(BuildingType building, Point location){
		this.capacity = building.getStartingCapacity();
		this.location = location;
		this.building = building;
		this.passiveProvider = building.isPassiveProvider();
		this.currentAmount = new HashMap<ResourceType, Integer>();
		if(this.isPassiveProvider()) 
		isBuilt = false;
		buildCompletion = 0;
		version = 1;
		this.holdableResources = building.getHoldableResources();
		for(int i = 0; i < this.holdableResources.size(); i++){
			currentAmount.put(holdableResources.get(i), 0);
		}
	}
	
	public BuildingType getBuildingType(){
		return building;
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

	public BuildingType getType(){
		return building;
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

	public HashMap<ResourceType, Integer> getCost(){
		return cost;
	}	
	
	public void doFunction(Object o){
		switch(building){
		case ARMORY:
			break;
		case OILTANK:
			break;
		case CHARGINGSTATION:
			break;
		case WORKSHOP:
			break;
		case HOMEDEPOT:
			break;
		}
	}
}
