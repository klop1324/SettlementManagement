package model.buildings;

import java.awt.Point;
import java.util.HashMap;

import model.resources.*;


// Holds all metal resources
public class JunkYard extends AbstractBuilding{
	
	
	public JunkYard(int capacity, Point location) {
		super(capacity, location, BuildingType.JUNKYARD);
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

	@Override
	public HashMap<ResourceType, Integer> getCost() {
		// TODO Auto-generated method stub
		return null;
	}


}
