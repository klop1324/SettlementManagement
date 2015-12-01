package model.buildings;

import java.awt.Point;
import java.util.HashMap;

import model.resources.ResourceType;

// Charges Robots
public class ChargingStation extends AbstractBuilding{

	public ChargingStation(int capacity, Point location) {
		super(capacity, location, BuildingType.CHARGINGSTATION);
		super.passiveProvider = true;
		setPassiveRate(3.0);
		resources.put(ResourceType.ELECTRICITY, 0);
	}

	@Override
	public void upgrade() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public HashMap<ResourceType, Integer> getCost() {
		// TODO Auto-generated method stub
		return null;
	}




}
