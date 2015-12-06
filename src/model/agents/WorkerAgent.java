package model.agents;

import java.awt.Point;

import model.tools.ToolType;

public class WorkerAgent extends AbstractAgent {
	public WorkerAgent(Point position) {
		super(position);
		filename = "???";
		MAX_RESOURCES = 1000;
		tool = ToolType.WELDINGGUN;
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
