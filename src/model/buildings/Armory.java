package model.buildings;

import java.awt.Point;

import model.resources.AbstractResource;

public class Armory extends AbstractBuilding{


	public Armory(String name, int capacity, boolean passiveProvider, double passiveRate, AbstractResource resourceType,
			Point location) {
		super(name, capacity, passiveProvider, passiveRate, resourceType, location);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void doBuildingJob() {
		// TODO Auto-generated method stub
		
	}

}
