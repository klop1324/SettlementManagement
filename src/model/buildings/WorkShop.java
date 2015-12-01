package model.buildings;

import java.awt.Point;

// Create tools
public class WorkShop extends AbstractBuilding{


	public WorkShop(String name, int capacity, Point location) {
		super(name, capacity, location, BuildingType.WORKSHOP);
		// TODO: Will create tools
	}

	@Override
	public void upgrade() {
		capacity += 10;
		version +=1;
	}

}
