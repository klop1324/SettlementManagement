package model.resources;
import java.awt.Point;
import java.io.Serializable;

import model.agents.AbstractAgent;

public class Resource implements Serializable{
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

	// When creating buildings and using up resources in that process
	public void spendResource(int n){
		if ((resourceAmount - n) >= 0){
			resourceAmount -=n;
		}
		else {
			System.out.println("You don't have enough resources to build that building!");
			return;
		}
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
	public void removeResource(int removeAmount){
		if (hasResources()){
			resourceAmount-= removeAmount;
			agentResourceNotify = ("Agent removed: " + removeAmount + " " + resources);
			if (!hasResources()){
				agentResourceNotify = ("Agent removed: " + removeAmount + " " + resources + " \nYou have used all of this " + resources + " resource");
			}
			else {
				//agentResourceNotify = ("You've used up all of this resource. D:");
			}
		}
		else {
			System.out.println("No more resource");
		}
	}

	public String getNotification(){
		return agentResourceNotify;
	}
}
