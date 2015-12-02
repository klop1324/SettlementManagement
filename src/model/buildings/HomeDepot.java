package model.buildings;

import java.awt.Point;
import java.util.HashMap;

import model.agents.AbstractAgent;
import model.resources.ResourceType;

// Does maintenence on agents
public class HomeDepot extends AbstractBuilding{

	private int maintainAmount = 10;
	private HashMap<ResourceType, Integer> cost = new HashMap<ResourceType, Integer>();

	public HomeDepot(int capacity, Point location) {
		super(capacity, location, BuildingType.HOMEDEPOT);
		resources.put(ResourceType.COAL, 0);
		resources.put(ResourceType.IRON, 0);
		resources.put(ResourceType.GOLD, 0);
	}


	public void maintain(AbstractAgent a) {
		a.setCondition(maintainAmount);
	}


	@Override
	public void upgrade() {
		maintainAmount+=10;
		version += 1;
		buildTime += 100;
		completionAmount = 0;

	}


	@Override
	public HashMap<ResourceType, Integer> getCost() {
		return cost;
	}



}
