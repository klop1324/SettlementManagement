package model.buildings;

import java.awt.Point;

import model.resources.AbstractResource;

// Create tools
public class WorkShop extends AbstractBuilding{


	public WorkShop(String name, int capacity, boolean passiveProvider, double passiveRate,
			AbstractResource resourceType, Point location) {
		super(name, capacity, passiveProvider, passiveRate, resourceType, location);
		name = "Work Shop";
	}

	@Override
	public void doBuildingJob() {
		// TODO Auto-generated method stub
		
	}

}
