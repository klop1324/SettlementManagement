package model.buildings;

import java.awt.Point;
import java.util.HashMap;

import model.resources.ResourceType;

// Holds all oil resources
public class OilTank extends AbstractBuilding{


	public OilTank(Point location) {
		super(location, 25000, false, 0.0, null, 100, 0, BuildingType.OILTANK);
		holdableResources.add(ResourceType.OIL);
		currentAmount.put(ResourceType.OIL, 0);
	}

	@Override
	public void upgrade() {
		capacity *= 1.1;
		version++;
		
	}

}
