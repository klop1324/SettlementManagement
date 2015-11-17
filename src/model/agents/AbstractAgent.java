package model.agents;

import java.awt.Point;

public abstract class AbstractAgent {
	int energy, condition, carriedResources;
	Point position, destination;
	AgentLogic AI;
	boolean switchFlag;
	String filename;
	char textRep;
	
	public AbstractAgent(Point position) {
		energy = 2000;
		condition = 2000;
		carriedResources = 0;
		AI = new AgentLogic();
		this.position = position;
	}
	
	public void setDestination(Point destination) {
		this.destination = destination;
	}
	
	public Point getDestination() {
		return destination;
	}
	
	public Point getPosition() {
		return position;
	}
	
	public void sendCommand(Command c, Point p) {
		/* 
		 * This will call AgentLogic to queue this command.
		 */
	}
	
	public void move() {
		boolean pRightOfD = position.x >= destination.x;
		boolean pBelowD = position.y >= destination.y;
		
		int direction = (int) (Math.random() * 2);
		
		if(!pRightOfD && !pBelowD) {
			if(position.x == destination.x)
				position = new Point(position.x, position.y + 1);
			else if(position.y == destination.y)
				position = new Point(position.x + 1, position.y);
			else if(direction == 0)
				position = new Point(position.x, position.y + 1);
			else if(direction == 1)
				position = new Point(position.x + 1, position.y);
		} else if(pRightOfD && !pBelowD) {
			if(position.x == destination.x)
				position = new Point(position.x, position.y + 1);
			else if(position.y == destination.y)
				position = new Point(position.x - 1, position.y);
			else if(direction == 0)
				position = new Point(position.x, position.y + 1);
			else if(direction == 1)
				position = new Point(position.x - 1, position.y);
		} else if(pRightOfD && pBelowD) {
			if(position.x == destination.x)
				position = new Point(position.x, position.y - 1);
			else if(position.y == destination.y)
				position = new Point(position.x - 1, position.y);
			else if(direction == 0)
				position = new Point(position.x, position.y - 1);
			else if(direction == 1)
				position = new Point(position.x - 1, position.y);
		} else if(!pRightOfD && pBelowD) {
			if(position.x == destination.x)
				position = new Point(position.x, position.y - 1);
			else if(position.y == destination.y)
				position = new Point(position.x + 1, position.y);
			else if(direction == 0)
				position = new Point(position.x, position.y - 1);
			else if(direction == 1)
				position = new Point(position.x + 1, position.y);
		}
	}
	
	// Hardcoded to move randomly
	abstract void tic();
	abstract void decrementEnergy();
	abstract void decrementCondition();
	
	public class AgentLogic {
		
		/*
		 * Agent should be doing things in this priority:
		 * 1. Addressing critically low energy and/or condition
		 * 2. Depositing Resource if it's at carrying capacity
		 * 3. Following user commands in the order they are issued
		 * 
		 * Critically low energy/condition can be assessed by constantly
		 * measuring distance from a refill station, comparing it to
		 * remaining energy/condition, and moving there once stat
		 * remaining after traveling <= some number greater than 0.
		 * 
		 * User commands expire only once the command has been fully carried out,
		 * i.e. all of specified resource gathered, or until user cancels
		 * command.
		 * 
		 * May be possible to get caught between these two priorities, such as
		 * if the resource is too far away to reach w/o running out of a stat.
		 * This could be accounted for, or it might just be up to the user to
		 * notice an unproductive robot waffling between commands and needs.
		 */
		public AgentLogic() {
			
		}
		
		public void recieveCommand(Command c, Point p) {
			
		}
		
		public void assessCurrentDestination() {
			if(position.x == destination.x && position.y == destination.y && switchFlag) {
				setDestination(new Point(13, 7));
				switchFlag = false;
			} else if(position.x == destination.x && position.y == destination.y && !switchFlag) {
				setDestination(new Point(0, 0));
				switchFlag = true;
			}
		}
	}
}