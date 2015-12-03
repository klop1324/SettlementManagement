package model.buildings;

import java.awt.Point;
import java.util.HashMap;

import model.resources.ResourceType;

public class SolarPanel extends AbstractBuilding{

	public SolarPanel(Point location) {
		super(location, 25000, true, 3.0, ResourceType.ELECTRICITY, 100, 0, BuildingType.SOLARPANEL);
		
	}

	@Override
	public void upgrade() {
		capacity *= 1.1;
		version++;
		
	}

	@Override
	protected void initCostHashMap() {
		buildingCost = new HashMap<ResourceType, Integer>();
		buildingCost.put(ResourceType.COPPER, 15000);
		buildingCost.put(ResourceType.IRON, 1000);		
	}
	

}
