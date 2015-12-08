package model.buildings;

import java.awt.Point;
import java.util.HashMap;

import model.resources.ResourceType;

public class Armory extends AbstractBuilding {
	public Armory(Point location){
	super(location, 25000, false, 0.0, null, 100, 0, BuildingType.ARMORY);
	holdableResources.add(ResourceType.COAL);
	currentAmount.put(ResourceType.COAL, 0);
	holdableResources.add(ResourceType.IRON);
	currentAmount.put(ResourceType.IRON, 0);
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
	
