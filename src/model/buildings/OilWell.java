package model.buildings;

import java.awt.Point;
import java.util.HashMap;

import model.resources.ResourceType;

// Extracts resource from map
public class OilWell extends AbstractBuilding{

	public OilWell(Point location) {
		super(location, 25000, false, 0.0, null, 100, 0, BuildingType.OILWELL);
		
	}

	@Override
	public void upgrade() {
		capacity *= 1.1;
		version++;
		
	}

}
