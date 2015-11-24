package model.resources;
import java.awt.Point;

import model.agents.AbstractAgent;

public class Resource {
	protected int resourceAmount;
	protected Point location;
	protected String agentResourceNotify;
	ResourceType resources;

	// Starting amount for resource

	public Resource(int startAmount, Point origin_point, ResourceType resources){
		this.resourceAmount = startAmount;
		this.location = origin_point;
		this.resources = resources;
	}

	// Returns the amount
	public int getAmount(){
		return resourceAmount;
	}

	public ResourceType getType(){
		return resources;
	}

	// Returns location of resource
	public Point getLocation(){
		return location;
	}

	// Add amount to resource
	public void addResource(int addAmount){
		resourceAmount+=addAmount;
	}

	public boolean hasResources(){
		if (resourceAmount <= 0){
			return false;
		}
		else {
			return true;
		}
	}

	// Remove amount
	public void removeResource(int removeAmount, AbstractAgent agent){
		if (hasResources()){

			agent.setPickedUpResource(resources);
			resourceAmount-= removeAmount;
			agentResourceNotify = ("Agent removed: " + removeAmount + " " + resources);
			if (!hasResources()){
				agentResourceNotify = ("Agent removed: " + removeAmount + " \nYou have used all this " + resources + " resource");
			}
		}
		else {
			agentResourceNotify = ("You've used up all of this resource. D:");
		}
	}
	
	public String getNotification(){
		return agentResourceNotify;
	}
}
