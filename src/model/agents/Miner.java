package model;

import java.awt.Point;

public class Miner extends AbstractAgent {

	public Miner(Point position) {
		super(position);
		filename = "???";
		textRep = 'M';
	}

	/*
	 * (non-Javadoc)
	 * @see model.AbstractAgent#tic()
	 * Moves towards destination each tic. If destination has been reached,
	 * destination is changed. Current pathfinding: four-way directional
	 * movement, chooses how to get to diagonal target randomly each move.
	 */
	@Override
	public void tic() {
		AI.assessCurrentDestination();
		move();
	}

	@Override
	void decrementEnergy() {
		energy = energy - 3;
		
	}

	@Override
	void decrementCondition() {
		condition--;
		
	}
}
