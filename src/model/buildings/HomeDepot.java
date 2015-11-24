package model.buildings;

import java.awt.Point;

import model.resources.ResourceType;

// Builds basic workers and holds small amount of basic resource
public class HomeDepot extends AbstractBuilding{

	public HomeDepot(String name, int capacity, Point location) {
		super(name, capacity, location, BuildingType.HOMEDEPOT);
		setPassiveRate(3.5);
		resources.put(ResourceType.COAL, 0);
	}

	@Override
	public void doBuildingJob() {
		// TODO Auto-generated method stub
		
	}

}
