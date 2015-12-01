package model.buildings;

import java.awt.Point;
import model.resources.*;


// Holds all metal resources
public class JunkYard extends AbstractBuilding{
	
	
	public JunkYard(String name, int capacity, Point location) {
		super(name, capacity, location, BuildingType.JUNKYARD);
		resources.put(ResourceType.COPPER, 0);
		resources.put(ResourceType.IRON, 0);
		resources.put(ResourceType.GOLD, 0);
		resources.put(ResourceType.COAL, 0);
		
	}

	@Override
	public void upgrade() {
		capacity += 10;
		version += 1;
		
	}


}
