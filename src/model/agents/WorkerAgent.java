package model.agents;

import java.awt.Point;
import model.agents.AbstractAgent.AgentLogic;

public class WorkerAgent extends AbstractAgent {
	public WorkerAgent(Point position) {
		super(position);
		AI = new AgentLogic(WorkerAgent.class);
		MAX_RESOURCES = 5000;
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
