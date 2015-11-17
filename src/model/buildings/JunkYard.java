package model.buildings;

import java.awt.Point;

import model.resources.AbstractResource;

// Holds all metal resources
public class JunkYard extends AbstractBuilding{


	public JunkYard(String name, int capacity, boolean passiveProvider, double passiveRate,
			AbstractResource resourceType, Point location) {
		super(name, capacity, passiveProvider, passiveRate, resourceType, location);
		name = "Junk Yard";
		}

	@Override
	public void doBuildingJob() {
		// TODO Auto-generated method stub
		
	}

}
