package model.agents;

import java.awt.Point;

public class WorkerAgent extends AbstractAgent {
	public WorkerAgent(Point position) {
		super(position);
		filename = "???";
		MAX_RESOURCES = 2000;
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
