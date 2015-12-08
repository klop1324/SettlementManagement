package model.buildings;

import java.awt.Point;
import java.util.HashMap;

import model.resources.ResourceType;

public class VictoryMonument extends AbstractBuilding {

	public VictoryMonument(Point location){
	super(location, 0, false, 0.0, null, 600, 0, BuildingType.VICTORYMONUMENT);
	}
	@Override
	public void upgrade() {
		//does nothing, the victory monument is the win condition
	}

	@Override
	protected void initCostHashMap() {
		buildingCost = new HashMap<ResourceType, Integer>();
		// inits 100k of every resource to build
		for(ResourceType r: ResourceType.values()){
			buildingCost.put(r, 100000);
		}
	}

}
