package model.buildings;

import java.awt.Point;
import java.util.HashMap;

import model.resources.ResourceType;

public class Armory extends AbstractBuilding {
	public Armory(Point location){
	super(location, 25000, false, 0.0, null, 100, 0, BuildingType.ARMORY);
		
	}
	
	@Override
	public void upgrade() {
		capacity *= 1.1;
		version++;
		
	}

	@Override
	protected void initCostHashMap() {
		buildingCost = new HashMap<ResourceType, Integer>();
		buildingCost.put(ResourceType.IRON, 5000);
		buildingCost.put(ResourceType.ELECTRICITY, 100);
	}

	
}
	
