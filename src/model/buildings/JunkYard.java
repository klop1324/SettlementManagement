package model.buildings;

import java.awt.Point;
import java.util.HashMap;

import model.resources.*;


// Holds all metal resources
public class JunkYard extends AbstractBuilding{
	
	
	public JunkYard(Point location) {
		super(location, 25000, false, 0.0, null, 100, 0, BuildingType.JUNKYARD);
		
	}

	@Override
	public void upgrade() {
		capacity *= 1.1;
		version++;
		
	}
}
