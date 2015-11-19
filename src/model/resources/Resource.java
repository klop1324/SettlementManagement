package model.resources;
import java.awt.Point;

import model.agents.AbstractAgent;

public class Resource {
	protected int startAmount;
	protected Point location;
	ResourceType resources;

	// Starting amount for resource
	public Resource(int startAmount, Point origin_point, ResourceType resources){
		this.startAmount = startAmount;
		this.location = origin_point;
		this.resources = resources;
	}

	// Returns the amount
	public int getAmount(){
		return startAmount;
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
		startAmount+=addAmount;
	}

	public boolean checkResource(){
		if (startAmount <= 0){
			return false;
		}
		else {
			return true;
		}
	}

	// Remove amount
	public void removeResource(int removeAmount, AbstractAgent agent){
		if (checkResource()){
			agent.pickedUpResource(removeAmount);
			startAmount-=removeAmount;
		}
		else {
			System.out.println("You've used up all of this resource. D:");
			agent.pickedUpResource(0);
		}
	}
}
