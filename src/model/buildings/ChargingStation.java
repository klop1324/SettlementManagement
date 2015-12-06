package model.buildings;

import java.awt.Point;
import java.util.HashMap;

import model.resources.ResourceType;

// Charges Robots
public class ChargingStation extends AbstractBuilding{

	public ChargingStation(Point location) {
		super(location, 30000, true, .005, ResourceType.ELECTRICITY, 100, 0.5, BuildingType.CHARGINGSTATION);
		holdableResources.add(ResourceType.ELECTRICITY);
		currentAmount.put(ResourceType.ELECTRICITY, 0);
	}

	@Override
	public void upgrade() {
		capacity *= 1.1;
		version++;
		
	}

	@Override
	protected void initCostHashMap() {
		buildingCost = new HashMap<ResourceType, Integer>();
		buildingCost.put(ResourceType.COAL, 10);
		buildingCost.put(ResourceType.COPPER, 100);
		buildingCost.put(ResourceType.OIL, 5000);
	}






}
