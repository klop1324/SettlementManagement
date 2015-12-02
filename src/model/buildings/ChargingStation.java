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
		super(location, 25000, false, 0.0, null, 100, 0.5, BuildingType.CHARGINGSTATION);
		
	}

	@Override
	public void upgrade() {
		capacity *= 1.1;
		version++;
		
	}






}
