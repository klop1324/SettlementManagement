package model.buildings;

import java.awt.Point;

import model.resources.Resources;

// Extracts resource from map
public class OilWell extends AbstractBuilding{


	public OilWell(String name, int capacity, boolean passiveProvider, double passiveRate, Resources resourceType,
			Point location) {
		super(name, capacity, passiveProvider, passiveRate, resourceType, location);
		name = "Oil Well";
		resourceType = Resources.OIL;
		passiveProvider = true;
	}

	@Override
	public void doBuildingJob() {
		// TODO Auto-generated method stub
		
	}

}
