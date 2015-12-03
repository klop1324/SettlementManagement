package model.buildings;

import java.awt.Point;
import java.util.HashMap;

import model.resources.ResourceType;

// Charges Robots
public class ChargingStation extends AbstractBuilding{

	public ChargingStation(Point location) {
		///CHARGINGSTATION("Charging Station", 25000, false, 0.0, null, 20*10,null, 0.0, ResourceType.ELECTRICITY);
		//(Point location, String name, int startingCapacity, boolean isPassiveProvider,
		//		double passiveRate, ResourceType passiveResource, int buildTime,ResourceType maintCostType, double maintCost, BuildingType buildingType)
		super(location, 30000, false, 0.0, null, 100, 0.5, BuildingType.CHARGINGSTATION);
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
		//buildingCost.put(ResourceType.COPPER, 100);
		//buildingCost.put(ResourceType.OIL, 5000);
	}






}
