package model.buildings;

import java.awt.Point;

import model.resources.AbstractResource;

// Extracts resource from map
public class OilWell extends AbstractBuilding{

	public OilWell(String name, int capacity, boolean passiveProvider, double passiveRate,
			AbstractResource resourceType, Point location) {
		super(name, capacity, passiveProvider, passiveRate, resourceType, location);
		name = "Oil Well";
	}

	@Override
	public void doBuildingJob() {
		// TODO Auto-generated method stub
		
	}

}
