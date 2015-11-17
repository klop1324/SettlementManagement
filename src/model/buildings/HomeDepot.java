package model.buildings;

import java.awt.Point;

import model.resources.AbstractResource;

// Builds basic workers and holds small amount of basic resource
public class HomeDepot extends AbstractBuilding{


	public HomeDepot(String name, int capacity, boolean passiveProvider, double passiveRate,
			AbstractResource resourceType, Point location) {
		super(name, capacity, passiveProvider, passiveRate, resourceType, location);
		name = "Home Depot";
	}

	@Override
	public void doBuildingJob() {
		// TODO Auto-generated method stub
		
	}

}
