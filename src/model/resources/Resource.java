package model.resources;
import java.awt.Point;

import model.agents.AbstractAgent;

public class Resource {
	protected int resourceAmount;
	protected Point location;
	protected String agentResourceNotify;
	ResourceType resources;

	// Starting amount for resource
	public Resource(int resourceAmount, Point origin_point, ResourceType resources){
		this.resourceAmount = resourceAmount;
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

	public boolean hasResource(){
		if (resourceAmount <= 0){
			return false;
		}
		else {
			return true;
		}
	}

	// Remove amount
	public void removeResource(int removeAmount, AbstractAgent agent){
		if (hasResource()){
			agent.setPickedUpResource(resources);
			resourceAmount-= removeAmount;
			agentResourceNotify = ("Agent removed: " + removeAmount + " " + resources);
			if (!hasResource()){
				agentResourceNotify = ("Agent removed: " + removeAmount + " \nYou have used all of this " + resources + " resource");
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
