package model.buildings;

import java.awt.Point;
import java.util.HashMap;

import model.resources.*;


// Holds all metal resources
public class JunkYard extends AbstractBuilding{
	
	
	public JunkYard(Point location) {
		super(location, 25000, false, 0.0, null, 100, 0, BuildingType.JUNKYARD);
		holdableResources.add(ResourceType.COAL);
		currentAmount.put(ResourceType.COAL, 0);
		holdableResources.add(ResourceType.IRON);
		currentAmount.put(ResourceType.IRON, 0);
		holdableResources.add(ResourceType.GOLD);
		currentAmount.put(ResourceType.GOLD, 0);
		holdableResources.add(ResourceType.COPPER);
		currentAmount.put(ResourceType.COPPER, 0);
	}

	@Override
	public void upgrade() {
		capacity *= 1.1;
		version++;
		
	}
}
