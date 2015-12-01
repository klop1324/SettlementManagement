package model.buildings;

import java.awt.Point;
import java.util.HashMap;

import model.resources.ResourceType;

// Extracts resource from map
public class OilWell extends AbstractBuilding{

	public OilWell(int capacity, Point location) {
		super(capacity, location, BuildingType.OILWELL);
		passiveProvider = true;
		setPassiveRate(2.3);
		resources.put(ResourceType.OIL, 0);
	}

	@Override
	public void upgrade() {
		capacity += 10;
		setPassiveRate(passiveRate += 4.0);
		version +=1;
		
	}

	@Override
	public HashMap<ResourceType, Integer> getCost() {
		// TODO Auto-generated method stub
		return null;
	}

}
