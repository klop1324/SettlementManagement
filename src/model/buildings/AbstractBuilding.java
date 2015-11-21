package model.buildings;

import java.awt.Point;
import java.util.HashMap;

import javax.swing.Timer;

import model.agents.AbstractAgent;
import model.resources.ResourceType;



public abstract class AbstractBuilding {
	protected String name;
	protected BuildingType building;
	protected int capacity;
	protected boolean passiveProvider = false;
	protected double passiveRate;
	protected Point location;
	protected HashMap<ResourceType, Integer> resources;
	protected Object resource;
	protected Timer myTimer;

	public AbstractBuilding(String name, int capacity, Point location, BuildingType building){
		this.name = name;
		this.capacity = capacity;
		this.location = location;
		this.building = building;
		resources = new HashMap<ResourceType, Integer>();
	}
	
	public BuildingType getType(){
		return building;
	}

	public void passiveCheck(){
		if (passiveProvider){
			// Gathers the HashMap keys and turns them into an array
			// Will then grab first index (passive providers should
			// only have one resource) which is the key, and then
			// that variable it's stored in can be used to get the value.
			resource = resources.keySet().toArray()[0];
			System.out.println(resource);
		}
		else {
			return;
		}
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
	
	// Can set rate if passive provider
	public void setPassiveRate(double rate){
		if(isPassiveProvider()){
			passiveRate = rate;
		}
		else {
			System.out.println("Not a passive provider");
		}
	}

	public double getPassiveRate(){
		return passiveRate;
	}

	public Point getLocation(){
		return location;
	}

	public HashMap<ResourceType, Integer> getResources(){
		return resources;
	}
	
	// Allows agents to remove amount of resources from building
	public void agentRemoveCapacity(ResourceType o, int amount) {
		int newResourceAmount = resources.get(o) - amount;
		if (newResourceAmount > 0){
			resources.replace(o, newResourceAmount);
		}
		else {
			System.out.println("You don't have that many resources!");
		}
	}

	// Allows agents to add their resource to the building
	public void agentAddCapacity(ResourceType resourceType, int amount, AbstractAgent a) {
		int newResourceAmount = resources.get(resourceType) + amount;
		if (newResourceAmount < capacity){
			resources.replace(resourceType, newResourceAmount);
			a.setCarriedResources(0);
		}
		else {
			System.out.println("You cannot hold that much! Please upgrade your storage to hold more.");
		}
	}

	// Goes through each resource and turns it into a string
	public String resourcesToString(){
		String allResources = "";
		for (int i = 0; i < resources.size(); i++){
			ResourceType key = (ResourceType) resources.keySet().toArray()[i];
			allResources += key.toString() + ": " + resources.get(key).toString()+ "\n";
		}
		return allResources;
	}
	
	public abstract void doBuildingJob();

}
