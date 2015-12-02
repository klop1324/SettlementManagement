package model.buildings;

import java.awt.Point;
import java.util.HashMap;

import model.resources.ResourceType;

// Holds all oil resources
public class OilTank extends AbstractBuilding{


	public OilTank(Point location) {
		super(location, 25000, false, 0.0, null, 100, 0, BuildingType.OILTANK);
		
	}

	@Override
	public void upgrade() {
		capacity *= 1.1;
		version++;
		
	}

}
