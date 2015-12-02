package model.buildings;

import java.awt.Point;
import java.util.HashMap;

import model.resources.ResourceType;

// Create tools
public class WorkShop extends AbstractBuilding{


	public WorkShop(Point location) {
		super(location, 25000, false, 0.0, null, 100, 0, BuildingType.WORKSHOP);
		
	}

	@Override
	public void upgrade() {
		capacity *= 1.1;
		version++;
		
	}

}
