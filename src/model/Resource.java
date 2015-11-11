package model;
import java.awt.Point;

public abstract class Resource {
	protected int amount;
	protected Point location;
	
	// Starting amount for resource
	public Resource(int startAmount, Point origin_point){
		this.amount = startAmount;
		this.location = origin_point;
	}
	
	// Returns the amount
	public int getAmount(){
		return amount;
	}
	
	// Returns location of resource
	public Point getLocation(){
		return location;
	}
	
	// Void method to use each resource
	public abstract void use();
	
	// Add amount to resource
	public void addResource(int addAmount){
		amount+=addAmount;
	}
	
	// Remove amount
	public void removeResource(int removeAmount){
		amount-=removeAmount;
	}
}
