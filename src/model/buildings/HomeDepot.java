package model.buildings;

import java.awt.Point;

// Builds basic workers and holds small amount of basic resource
public class HomeDepot extends AbstractBuilding{

	public HomeDepot(String name, int capacity, Point location) {
		super(name, capacity, location);
		setPassiveRate(3.5);
	}

	@Override
	public void doBuildingJob() {
		// TODO Auto-generated method stub
		
	}

}
