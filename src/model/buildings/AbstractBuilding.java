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
	protected int remaining = 0; //SUPER SKETCHY HACKY CODE
	protected boolean error = false;
	
	//Build state stuff
	protected int buildTime;
	protected double buildCompletion = 0;
	protected int version = 1;
	protected boolean isBuilt = false;
	protected HashMap<ResourceType, Integer> buildingCost;
	
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
		
		initCostHashMap();
	}

	public int getBuildTime(){
		return buildTime;
	}

	public void incrementCompletionAmount(double n){
		buildCompletion += n;
	}
	
	public boolean isCompleted(){
		if (buildCompletion >= 100) {
			return true;
		}
		else {
			return false;
		}
	}

	public double getCompletionAmount(){
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
	
	public ResourceType getPassiveResource(){
		return passiveResource;
	}
	
	public ArrayList<ResourceType> getResources(){
		return holdableResources;
	}
	
	public void addResource(ResourceType resource, int amount){
		if(!holdableResources.contains(resource)){
			throw new RuntimeException("does not contain This Resource!");
		}
		else{
			if (amount > capacity){
				System.out.println("You must build another building to hold all this!");
				return;
			}
			currentAmount.replace(resource, currentAmount.get(resource) + amount);
		}
	}
	
	public void passiveAddResource(ResourceType resource, double amount){
		remaining += amount;
		if (remaining >= 1){
			int temp = (int) remaining;
			remaining = remaining - temp;
			this.addResource(resource, temp);
			
			if(!holdableResources.contains(resource)){
				System.out.println("does not contain This Resource!");
				return;
			}
			if (remaining > capacity){
				currentAmount.replace(resource, capacity);
			}
			else{
				currentAmount.replace(resource, currentAmount.get(resource) + temp);
				System.out.println(currentAmount);
			}
		}
	}
	
	public void addResource(ResourceType resource, double amount){
		if(amount + currentAmount.get(resource) > capacity){
			error = true;
			return;
		}
		int temp = (int)remaining;
		remaining -= temp;
		if(!holdableResources.contains(resource)){
			System.out.println("does not contain This Resource!");
			return;
		}
		else{
			currentAmount.replace(resource, currentAmount.get(resource) + temp);
		}
	}
	
	public void removeResource(ResourceType resource, int amount){
		if(!holdableResources.contains(resource)){
			System.out.println("does not contain This Resource!");
			return;
		}
		else{
			int result = currentAmount.get(resource) - amount;
			// Comment this out if you need to test creating when you don't have enough resources
			if (result < 0){
				System.out.println("Can't take more than you have.");
				return;
			}
			else{
				currentAmount.replace(resource, result);
			}
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
		if(newResourceAmount < 0){
			System.out.println("Cannot remove that many resources!");
			return;
		}
		else{
			currentAmount.replace(resource, newResourceAmount);
		}
	}

	// Allows agents to add their resource to the building
	public void agentAddCapacity(ResourceType resource, int amount) {
		int newResourceAmount = currentAmount.get(resource) + amount;
		if (newResourceAmount > capacity){
			error = true;
			return;
		}
		currentAmount.replace(resource, newResourceAmount);
	}

	public boolean canInsert(ResourceType resource, double amount){
		double total = currentAmount.get(resource)+ amount;
		if(total > capacity)return false;
		else{
			return true;
		}	
	}
	
	public boolean getErrors(){
		return error;
	}
	
	public void resetErrors(){
		error = false;
	}
	
	public Image getImage(){
		return buildingType.getImage();
	}
	
	public abstract void upgrade();

	public BuildingType getType() {
		return buildingType;
	}
	
	public HashMap<ResourceType, Integer> getCurrentAmount(){
		return currentAmount;
	}
	
	public HashMap<ResourceType, Integer> getCost() {
		return buildingCost;
	}
	
	protected abstract void initCostHashMap();
	
}
