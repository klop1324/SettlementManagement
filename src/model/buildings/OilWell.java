package model.buildings;

import java.awt.Point;
import java.util.HashMap;

import model.resources.ResourceType;

// Extracts resource from map
public class OilWell extends AbstractBuilding{

	public OilWell(Point location) {
		super(location, 25000, true, 15.0, ResourceType.OIL, 100, 0, BuildingType.OILWELL);
		holdableResources.add(ResourceType.OIL);
		currentAmount.put(ResourceType.OIL, 0);

	}

	@Override
	public void upgrade() {
		capacity *= 1.1;
		version++;
		
	}

	@Override
	protected void initCostHashMap() {
		buildingCost = new HashMap<ResourceType, Integer>();
		buildingCost.put(ResourceType.COAL, 500);
		buildingCost.put(ResourceType.IRON, 5000);
		buildingCost.put(ResourceType.ELECTRICITY, 100);		
	}

}
