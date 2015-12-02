package model.agents;

import java.awt.Point;

import model.buildings.*;

public class BuilderAgent extends AbstractAgent {
	private int buildAmount = 3;
	public BuilderAgent(Point position) {
		super(position);
		filename = "???";
		MAX_RESOURCES = 0;
	}
	
	public void incrementCompletionAmount(AbstractBuilding b){
		if (this.hasWeldingGun()){
			buildAmount += 5;
			b.incrementCompletionAmount(buildAmount);
		}
		else {
			b.incrementCompletionAmount(buildAmount);
		}
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
