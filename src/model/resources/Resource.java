package model.resources;
import java.awt.Point;

public class Resource {
	protected int amount;
	protected Point location;
	ResourceType resources;
	
	// Starting amount for resource
	public Resource(int startAmount, Point origin_point, ResourceType resources){
		this.amount = startAmount;
		this.location = origin_point;
		this.resources = resources;
	}
	
	// Returns the amount
	public int getAmount(){
		return amount;
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
		amount+=addAmount;
	}
	
	// Remove amount
	public void removeResource(int removeAmount){
		amount-=removeAmount;
	}
}
