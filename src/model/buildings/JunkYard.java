package model.buildings;

import java.awt.Point;
import model.resources.*;


// Holds all metal resources
public class JunkYard extends AbstractBuilding{
	
	
	public JunkYard(String name, int capacity, Point location) {
		super(name, capacity, location);
		resources.put(ResourceType.COPPER, 0);
		resources.put(ResourceType.IRON, 0);
		resources.put(ResourceType.GOLD, 0);
		
	}
	

	@Override
	public void doBuildingJob() {
		System.out.println(resources);
		
	}


}
