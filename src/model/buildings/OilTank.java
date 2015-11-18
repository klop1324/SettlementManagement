package model.buildings;

import java.awt.Point;

import model.resources.Resources;

// Holds all oil resources
public class OilTank extends AbstractBuilding{

	public OilTank(String name, int capacity, boolean passiveProvider, double passiveRate, Resources resourceType,
			Point location) {
		super(name, capacity, passiveProvider, passiveRate, resourceType, location);
		name = "Oil Tank";
		resourceType = Resources.OIL;
	}

	@Override
	public void doBuildingJob() {
		// TODO Auto-generated method stub
		
	}

}
