package model.buildings;

import java.awt.Point;
import java.util.HashMap;

import model.resources.ResourceType;

// Create tools
public class WorkShop extends AbstractBuilding{


	public WorkShop(int capacity, Point location) {
		super(capacity, location, BuildingType.WORKSHOP);
		// TODO: Will create tools
	}

	@Override
	public void upgrade() {
		capacity+=10;
		version += 1;
		buildTime += 100;
		completionAmount = 0;
	}

	@Override
	public HashMap<ResourceType, Integer> getCost() {
		// TODO Auto-generated method stub
		return null;
	}

}
