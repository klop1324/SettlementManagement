package model.buildings;

import java.awt.Point;
import java.util.HashMap;

import javax.swing.Timer;

import model.agents.AbstractAgent;
import sun.management.Agent;



public abstract class AbstractBuilding {
	protected String name;
	protected int capacity;
	protected boolean passiveProvider = false;
	protected double passiveRate;
	protected Point location;
	protected HashMap<Object, Integer> resources;
	protected Object resource;
	protected Timer myTimer;

	public AbstractBuilding(String name, int capacity, Point location){
		this.name = name;
		this.capacity = capacity;
		this.location = location;
		resources = new HashMap<Object, Integer>();
	}

	public void passiveCheck(){
		if (passiveProvider){
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

	public HashMap<Object, Integer> getResources(){
		return resources;
	}
	public void agentRemoveCapacity(Object o, int amount) {
		int newResourceAmount = resources.get(o) - amount;
		if (newResourceAmount > 0){
			resources.put(o, resources.get(o) - amount);
		}
		else {
			System.out.println("You don't have that many resources!");
		}
	}

	public void agentAddCapacity(Object o, int amount, AbstractAgent agent) {
		if (agent.getPosition().equals(getLocation())){
			int newResourceAmount = resources.get(o) + amount;
			if (newResourceAmount < capacity){
				resources.put(o, resources.get(o) + amount);
			}
			else {
				System.out.println("You cannot hold that much! Please upgrade your storage to hold more.");
			}
		}
		else {
			System.out.println("Not here");
		}
	}


	public abstract void doBuildingJob();

}
