package model.buildings;

import java.awt.Point;
import java.util.HashMap;

import model.agents.AbstractAgent;
import model.resources.ResourceType;

// Does maintenence on agents
public class HomeDepot extends AbstractBuilding{

	
	public HomeDepot(Point location){
		super(location, 25000, false, 0.0, null, 100, 0.5, BuildingType.HOMEDEPOT);
	}
	
	@Override
	public void upgrade() {
		capacity *= 1.1;
		version++;
	}



}
