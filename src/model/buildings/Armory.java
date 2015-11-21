package model.buildings;

import java.awt.Point;

public class Armory extends AbstractBuilding{


	public Armory(String name, int capacity, Point location) {
		super(name, capacity, location, BuildingType.ARMORY);
		// TODO Will store weapons
	}

	@Override
	public void doBuildingJob() {
		// TODO Auto-generated method stub
		
	}

}
