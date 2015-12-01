package model.agents;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;

import model.resources.ResourceType;

public abstract class AbstractAgent implements Serializable{
	int energy, condition, oil, carriedResources, MAX_RESOURCES, MAX_NEED;
	Point position, destination, nearestOilTank, nearestHomeDepot, nearestChargingStation, nearestJunkYard;
	AgentLogic AI;
	String filename;
	ResourceType carriedResourceType;
	
	/**
	 * Creates a new AbstractAgent at a given position.
	 * @param Point position
	 */
	public AbstractAgent(Point position) {
		energy = 2000;
		condition = 2000;
		oil = 2000;
		MAX_NEED = 2000;
		carriedResources = 0;
		AI = new AgentLogic();
		destination = new Point(6, 6);
		this.position = position;
	}
	
	// Added getter that can be changed later down the road
	public int getAmountCarried(){
		return carriedResources;
	}
	
	public ResourceType getCarriedResource(){
		return carriedResourceType;
	}
	
	public void setAmountCarried(int a) {
		carriedResources = a;
	}
	
	// Added carriedResources setter can be changed later
	public void setPickedUpResource(ResourceType resource){
		setAmountCarried(10); // Change later when max carry limit is set up
		carriedResourceType = resource;
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
		
	public void sendCommand(AgentCommandWithDestination c) {
		AI.recieveCommand(c);
	}
	
	public void move() {
		if(atDestination()) return;
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
	
	private boolean atDestination() {
		if(position.x == destination.x && position.y == destination.y) return true;
		return false;
	}

	/**
	 * Moves towards destination each tic. If destination has been reached,
	 * destination is changed. Current pathfinding: four-way directional
	 * movement, chooses how to get to diagonal target randomly each move.
	 */
	public void tic() {
		AI.assessCurrentDestination();
		move();
		decrementEnergy();
		decrementCondition();
		decrementOil();
	}
	
	abstract void decrementEnergy();
	abstract void decrementCondition();
	abstract void decrementOil();
	
	public class AgentLogic {
		
		/*
		 * Agent should be doing things in this priority:
		 * 1. Addressing critically low needs
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
		
		private ArrayList<AgentCommandWithDestination> actionQueue;
		
		public AgentLogic() {
			actionQueue = new ArrayList<AgentCommandWithDestination>();
		}
		
		public void recieveCommand(AgentCommandWithDestination c) {
			actionQueue.add(c);
		}
		
		public ArrayList<AgentCommandWithDestination> getActionQueue(){
			return actionQueue;
		}
		
		public void assessCurrentDestination() {
			// Need low
			if(oil < 100)
				actionQueue.add(0, new AgentCommandWithDestination(AgentCommand.REFILL_OIL, nearestOilTank));
			else if(energy < 100)
				actionQueue.add(0, new AgentCommandWithDestination(AgentCommand.REFILL_ENERGY, nearestChargingStation));
			else if(condition < 100)
				actionQueue.add(0, new AgentCommandWithDestination(AgentCommand.REFILL_CONDITION, nearestJunkYard));
			
			// At destination
			if(atDestination()) {
				if(!assessActionAtDestination()) {
					actionQueue.remove(0);
					setDestination(actionQueue.get(0).getCommandDestination());
				}
			}
		}
		
		/**
		 * Returns true if Agent won't need a new destination yet, returns false if
		 * agent needs a new destination.
		 * @return
		 */
		public boolean assessActionAtDestination() {
			if(actionQueue.get(0).getAgentCommand().equals(AgentCommand.REFILL_OIL) &&
					oil <= MAX_NEED - 50) {
				oil += 50;
				return true;
			} else if(actionQueue.get(0).getAgentCommand().equals(AgentCommand.REFILL_ENERGY) &&
					energy <= MAX_NEED - 50) {
				energy += 50;
				return true;
			} else if(actionQueue.get(0).getAgentCommand().equals(AgentCommand.REFILL_CONDITION) &&
					condition <= MAX_NEED - 50) {
				condition += 50;
				return true;
			}
			
			if(actionQueue.get(0).getAgentCommand().equals(AgentCommand.FIGHT)) {
				// kill rogue agent
				condition -= 500;
				return false;
			}
			
			if(actionQueue.get(0).getAgentCommand().isCollect()) {
				if(carriedResources <= MAX_RESOURCES - 50) {
					carriedResources += 50;
					return true;
				} else {
					if(actionQueue.get(0).getAgentCommand().equals(AgentCommand.COLLECT_COAL)) {
						actionQueue.add(0, new AgentCommandWithDestination(AgentCommand.DEPOSIT_COAL, nearestHomeDepot));
						return true;
					} else if(actionQueue.get(0).getAgentCommand().equals(AgentCommand.COLLECT_COPPER)) {
						actionQueue.add(0, new AgentCommandWithDestination(AgentCommand.DEPOSIT_COPPER, nearestJunkYard));
						return true;
					} else if(actionQueue.get(0).getAgentCommand().equals(AgentCommand.COLLECT_ELECTRICITY)) {
						actionQueue.add(0, new AgentCommandWithDestination(AgentCommand.DEPOSIT_ELECTRICITY, nearestChargingStation));
						return true;
					} else if(actionQueue.get(0).getAgentCommand().equals(AgentCommand.COLLECT_GOLD)) {
						actionQueue.add(0, new AgentCommandWithDestination(AgentCommand.DEPOSIT_GOLD, nearestJunkYard));
						return true;
					} else if(actionQueue.get(0).getAgentCommand().equals(AgentCommand.COLLECT_IRON)) {
						actionQueue.add(0, new AgentCommandWithDestination(AgentCommand.DEPOSIT_IRON, nearestJunkYard));
						return true;
					} else if(actionQueue.get(0).getAgentCommand().equals(AgentCommand.COLLECT_OIL)) {
						actionQueue.add(0, new AgentCommandWithDestination(AgentCommand.DEPOSIT_OIL, nearestOilTank));
						return true;
					}
				}
			}
			
			if(actionQueue.get(0).getAgentCommand().isDeposit()) {
				
			}
			
			return false;
		}
	}
}