package model.buildings;

import java.awt.Point;

import model.resources.ResourceType;

// Charges Robots
public class ChargingStation extends AbstractBuilding{

	public ChargingStation(String name, int capacity, Point location) {
		super(name, capacity, location, BuildingType.CHARGINGSTATION);
		super.passiveProvider = true;
		setPassiveRate(3.0);
		resources.put(ResourceType.ELECTRICITY, 0);
	}

	@Override
	public void upgrade() {
		// TODO Auto-generated method stub
		
	}




}
