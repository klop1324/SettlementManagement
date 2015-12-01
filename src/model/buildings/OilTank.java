package model.buildings;

import java.awt.Point;
import java.util.HashMap;

import model.resources.ResourceType;

// Holds all oil resources
public class OilTank extends AbstractBuilding{


	public OilTank(int capacity, Point location) {
		super(capacity, location, BuildingType.OILTANK);
		name = "Oil Tank";
		resources.put(ResourceType.OIL, 0);
	}

	@Override
	public void upgrade() {
		capacity += 10;
		version += 1;
	}

	@Override
	public HashMap<ResourceType, Integer> getCost() {
		// TODO Auto-generated method stub
		return null;
	}

}
