package model.buildings;

import java.awt.Point;

import model.resources.Electricity;

// Charges Robots
public class ChargingStation extends AbstractBuilding{

	public ChargingStation(String name, int capacity, Point location) {
		super(name, capacity, location);
		super.passiveProvider = true;
		setPassiveRate(3.0);
		resources.put(Electricity.class, 0);
	}

	@Override
	public void doBuildingJob() {
		// TODO Auto-generated method stub
		
	}


}
