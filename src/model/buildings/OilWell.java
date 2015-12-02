package model.buildings;

import java.awt.Point;
import java.util.HashMap;

import model.resources.ResourceType;

// Extracts resource from map
public class OilWell extends AbstractBuilding{

	public OilWell(Point location) {
		super(location, 25000, true, 1.0, ResourceType.OIL, 100, 0, BuildingType.OILWELL);
		
	}

	@Override
	public void upgrade() {
		capacity *= 1.1;
		version++;
		
	}

}
