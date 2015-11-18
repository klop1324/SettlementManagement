package model.buildings;

import java.awt.Point;

import model.resources.ResourceType;

// Extracts resource from map
public class OilWell extends AbstractBuilding{

	public OilWell(String name, int capacity, Point location) {
		super(name, capacity, location);
		passiveProvider = true;
		setPassiveRate(2.3);
		resources.put(ResourceType.OIL, 0);
	}

	@Override
	public void doBuildingJob() {
		// TODO Auto-generated method stub
		
	}

}
