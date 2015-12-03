package model.buildings;

import java.awt.Point;
import java.util.HashMap;

import model.resources.ResourceType;

// Create tools
public class WorkShop extends AbstractBuilding{


	public WorkShop(Point location) {
		super(location, 25000, false, 0.0, null, 100, 0, BuildingType.WORKSHOP);
		holdableResources.add(ResourceType.TOOL);
		currentAmount.put(ResourceType.TOOL, 0);
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
		buildingCost.put(ResourceType.COAL, 1000);
		buildingCost.put(ResourceType.GOLD, 6000);
	}
	
	

}
