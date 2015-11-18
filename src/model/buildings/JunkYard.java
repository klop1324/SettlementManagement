package model.buildings;

import java.awt.Point;
import model.resources.*;


// Holds all metal resources
public class JunkYard extends AbstractBuilding{
	
	
	public JunkYard(String name, int capacity, Point location,
			Object resource) {
		super(name, capacity, location);
		resources.put(Copper.class, 0);
		resources.put(Iron.class, 0);
		resources.put(Gold.class, 0);
		
	}
	

	@Override
	public void doBuildingJob() {
		System.out.println(resources);
		
	}


}
