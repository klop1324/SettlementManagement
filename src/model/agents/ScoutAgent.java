package model.agents;

import java.awt.Point;

public class ScoutAgent extends AbstractAgent {

	public ScoutAgent(Point position) {
		super(position);
		filename = "???";
		textRep = 's';
		MAX_RESOURCES = 500;
	}

	@Override
	void decrementEnergy() {
		energy--;	
	}

	@Override
	void decrementCondition() {
		condition--;
	}

	@Override
	void decrementOil() {
		oil--;	
	}


}
