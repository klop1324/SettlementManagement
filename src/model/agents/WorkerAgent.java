package model.agents;

import java.awt.Point;

public class WorkerAgent extends AbstractAgent {
	public WorkerAgent(Point position) {
		super(position);
		filename = "???";
		MAX_RESOURCES = 100;
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
