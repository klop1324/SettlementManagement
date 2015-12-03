package model.buildings;

import java.awt.Point;

public class Armory extends AbstractBuilding {
	public Armory(Point location){
	super(location, 25000, false, 0.0, null, 100, 0, BuildingType.SOLARPANEL);
		
	}
	
	@Override
	public void upgrade() {
		capacity *= 1.1;
		version++;
		
	}
}
	
