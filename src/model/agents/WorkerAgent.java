package model.agents;

import java.awt.Point;

public class WorkerAgent extends AbstractAgent {

	public WorkerAgent(Point position) {
		super(position);
		filename = "???";
		textRep = 'M';
		MAX_RESOURCES = 2000;
	}

	@Override
	void decrementEnergy() {
		energy = energy - 2;		
	}

	@Override
	void decrementCondition() {
		condition = condition - 2;		
	}

	@Override
	void decrementOil() {
		oil = oil - 2;	
	}
}
