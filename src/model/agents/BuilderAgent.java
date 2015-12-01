package model.agents;

import java.awt.Point;

public class BuilderAgent extends AbstractAgent {

	public BuilderAgent(Point position) {
		super(position);
		filename = "???";
		MAX_RESOURCES = 0;
	}

	@Override
	void decrementEnergy() {
		energy -= 2;	
	}

	@Override
	void decrementCondition() {
		condition -= 2;
	}

	@Override
	void decrementOil() {
		oil -= 2;	
	}


}
