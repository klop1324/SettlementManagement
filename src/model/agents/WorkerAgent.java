package model.agents;

import java.awt.Point;
import model.agents.AbstractAgent.AgentLogic;
import model.resources.ResourceType;

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

	@Override
	public void initCostHashMap() {
		agentCost.put(ResourceType.ELECTRICITY, 4000);
		agentCost.put(ResourceType.IRON, 4000);
		
	}

}
