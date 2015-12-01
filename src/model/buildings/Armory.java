package model.buildings;

import java.awt.Point;
import java.util.HashMap;

import model.resources.ResourceType;

public class Armory extends AbstractBuilding{


	public Armory(int capacity, Point location) {
		super(capacity, location, BuildingType.ARMORY);
		// TODO Will store weapons?
	}

	@Override
	public void upgrade() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public HashMap<ResourceType, Integer> getCost() {
		// TODO Auto-generated method stub
		return null;
	}


}
