package model.agents;

import java.awt.Point;

public class SoldierAgent extends AbstractAgent{
	private int attackPoints = 5;
	public SoldierAgent(Point position) {
		super(position);
		filename = "???";
		MAX_RESOURCES = 0;
	}
	
	// Added this method for tool testing, can change later
	public void attack(){
		if (this.hasSpear()){
			attackPoints += 10;
		}
	}
	
	public int getAttackPoints(){
		return attackPoints;
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
