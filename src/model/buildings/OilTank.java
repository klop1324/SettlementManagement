package model.buildings;

import java.awt.Point;

import model.resources.Oil;

// Holds all oil resources
public class OilTank extends AbstractBuilding{


	public OilTank(String name, int capacity, Point location) {
		super(name, capacity, location);
		name = "Oil Tank";
		resources.put(Oil.class, 0);
	}

	@Override
	public void doBuildingJob() {
		// TODO Auto-generated method stub
		
	}

}
