package model.buildings;

import java.awt.Point;

import model.resources.Resources;

public class Armory extends AbstractBuilding{

	

	public Armory(String name, int capacity, boolean passiveProvider, double passiveRate, Resources resourceType,
			Point location) {
		super(name, capacity, passiveProvider, passiveRate, resourceType, location);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void doBuildingJob() {
		// TODO Auto-generated method stub
		
	}

}
