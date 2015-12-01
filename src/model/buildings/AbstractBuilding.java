package model.buildings;

import java.awt.Point;
import java.io.Serializable;
import java.util.HashMap;
import javax.swing.Timer;

import model.resources.ResourceType;



public abstract class AbstractBuilding implements Serializable{
	protected String name;
	protected BuildingType building;
	protected static int max_capacity = 30;
	protected int capacity;
	protected boolean passiveProvider = false;
	protected double passiveRate;
	protected Point location;
	protected HashMap<ResourceType, Integer> resources;
	protected Object resource;
	protected Timer myTimer;
	protected int version;
	protected int buildTime;
	protected int completionAmount;

	public AbstractBuilding(int capacity, Point location, BuildingType building){
		this.capacity = capacity;
		this.location = location;
		this.building = building;
		completionAmount = 0;
		version = 1;
		resources = new HashMap<ResourceType, Integer>();
		setName(building);
	}
	public int getBuildTime(){
		return buildTime;
	}

	public void incrementCompletionAmount(int n){
		completionAmount+=n;
	}

	public boolean isCompleted(){
		if (completionAmount == 100){
			return true;
		}
		else {
			return false;
		}
	}

	public int getCompletionAmount(){
		return completionAmount;
	}

	public void setName(BuildingType b){
		switch(b){
		case ARMORY:
			name = "Armory";
			break;
		case CHARGINGSTATION:
			name = "Charging Station";
			break;
		case HOMEDEPOT:
			name = "Home Depot";
			break;
		case JUNKYARD:
			name = "JunkYard";
			break;
		case OILTANK:
			name = "Oil Tank";
			break;
		case OILWELL:
			name = "Oil Well";
			break;
		case WORKSHOP:
			name = "Workshop";
			break;
		default:
			break;

		}
	}

	public int getVersion(){
		return version;
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
	public void agentAddCapacity(ResourceType resourceType, int amount) {
		if (resources.containsKey(resourceType)){
			int newResourceAmount = resources.get(resourceType) + amount;
			if (newResourceAmount < capacity){
				resources.replace(resourceType, newResourceAmount);
			}
			else {
				System.out.println("You cannot hold that much! Please upgrade your storage to hold more.");
			}
		}
		else {
			System.out.println("Not the right building!");
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

	public boolean isFull(){
		if (capacity == max_capacity){
			return true;
		}
		else {
			return false;
		}
	}

	public abstract HashMap<ResourceType, Integer> getCost();
	public abstract void upgrade();

}
