package model.buildings;

import java.awt.Point;
import java.util.HashMap;

import model.agents.AbstractAgent;
import model.resources.ResourceType;

// Does maintenence on agents
public class HomeDepot extends AbstractBuilding{

	
	public HomeDepot(Point location){
		super(location, 25000, false, 0.0, null, 100, 0.5, BuildingType.HOMEDEPOT);
		holdableResources.add(ResourceType.COAL);
		currentAmount.put(ResourceType.COAL, 0);
		holdableResources.add(ResourceType.IRON);
		currentAmount.put(ResourceType.IRON, 0);
		holdableResources.add(ResourceType.GOLD);
		currentAmount.put(ResourceType.GOLD, 0);
	}
	
	@Override
	public void upgrade() {
		capacity *= 1.1;
		version++;
	}

	@Override
	protected void initCostHashMap() {
		buildingCost = new HashMap<ResourceType, Integer>();
		buildingCost.put(ResourceType.GOLD, 5000);
		buildingCost.put(ResourceType.ELECTRICITY, 1000);
		buildingCost.put(ResourceType.IRON, 10000);
	}



}
