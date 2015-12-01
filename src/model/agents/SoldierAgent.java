package model.agents;

import java.awt.Point;

public class SoldierAgent extends AbstractAgent{

	public SoldierAgent(Point position) {
		super(position);
		filename = "???";
		MAX_RESOURCES = 0;
	}

	@Override
	void decrementEnergy() {
		energy = energy - 3;		
	}

	@Override
	void decrementCondition() {
		condition = condition - 3;	
	}

	@Override
	void decrementOil() {
		oil = oil - 3;
	}

}
