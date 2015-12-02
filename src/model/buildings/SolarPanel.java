package model.buildings;

import java.awt.Point;
import java.util.HashMap;

import model.resources.ResourceType;

public class SolarPanel extends AbstractBuilding{

	public SolarPanel(int capacity, Point location, BuildingType building) {
		super(capacity, location, building);
	}

	@Override
	public HashMap<ResourceType, Integer> getCost() {
		return null;
	}

	@Override
	public void upgrade() {
		capacity += 10;
		setPassiveRate(passiveRate += 4.0);
		version +=1;
		buildTime += 100;
		completionAmount = 0;
	}

}
