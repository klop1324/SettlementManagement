package model.buildings;

import java.awt.Point;
import java.util.HashMap;

import model.resources.ResourceType;

public class SolarPanel extends AbstractBuilding{

	public SolarPanel(int capacity, Point location, BuildingType building) {
		super(capacity, location, building);
		// TODO Auto-generated constructor stub
	}

	@Override
	public HashMap<ResourceType, Integer> getCost() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void upgrade() {
		// TODO Auto-generated method stub
		
	}

}
