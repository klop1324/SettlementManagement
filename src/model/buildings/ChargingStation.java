package model.buildings;

import java.awt.Point;

import model.resources.AbstractResource;

// Charges Robots
public class ChargingStation extends AbstractBuilding{


	public ChargingStation(String name, int capacity, boolean passiveProvider, double passiveRate,
			AbstractResource resourceType, Point location) {
		super(name, capacity, passiveProvider, passiveRate, resourceType, location);
		name = "Charging Station";
	}

	@Override
	public void doBuildingJob() {
		// TODO Auto-generated method stub
		
	}

}
