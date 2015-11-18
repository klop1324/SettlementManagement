package model.buildings;

import java.awt.Point;

import model.resources.Resources;

// Create tools
public class WorkShop extends AbstractBuilding{


	public WorkShop(String name, int capacity, boolean passiveProvider, double passiveRate, Resources resourceType,
			Point location) {
		super(name, capacity, passiveProvider, passiveRate, resourceType, location);
		name = "Home Depot";
	}

	@Override
	public void doBuildingJob() {
		// TODO Auto-generated method stub
		
	}

}
