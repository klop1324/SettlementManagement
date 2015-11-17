package model.buildings;

import java.awt.Point;

import model.resources.Resources;

// Charges Robots
public class ChargingStation extends AbstractBuilding{

	public ChargingStation(String name, int capacity, boolean passiveProvider, double passiveRate,
			Resources resourceType, Point location) {
		super(name, capacity, passiveProvider, passiveRate, resourceType, location);
		name = "Charging Station";
		resourceType = Resources.ELECTRICITY;
	}

	@Override
	public void doBuildingJob() {
		// TODO Auto-generated method stub
		
	}

}
