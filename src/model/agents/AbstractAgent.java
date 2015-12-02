package model.agents;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;

import model.Game;
import model.buildings.AbstractBuilding;
import model.buildings.BuildingType;
import model.resources.Resource;
import model.resources.ResourceType;
import model.tools.Tool;
import model.tools.ToolType;

public abstract class AbstractAgent implements Serializable {
	Tool tool = null;
	int energy, condition, oil, carriedResources, MAX_RESOURCES, MAX_NEED, ticInt;
	Point position, destination, nearestOilTank, nearestHomeDepot, nearestChargingStation, nearestJunkYard;
	AgentLogic AI;
	String filename;
	ResourceType carriedResourceType;

	/**
	 * Creates a new AbstractAgent at a given position.
	 * 
	 * @param Point
	 *            position
	 */
	public AbstractAgent(Point position) {
		energy = 2000;
		condition = 2000;
		oil = 2000;
		MAX_NEED = 2000;
		carriedResources = 0;
		AI = new AgentLogic();
		destination = null;
		this.position = position;
	}

	public boolean hasPickAxe() { // Tool for Worker Agents to get ore faster.
		if (this.hasTool() && tool.getType().equals(ToolType.PICKAXE)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean hasArmor() { // Tool for Soldier Agents to lose less health
								// in battle.
		if (this.hasTool() && tool.getType().equals(ToolType.ARMOR)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean hasSpear() { // Tool for Soldier Agents to do more damage.
		if (this.hasTool() && tool.getType().equals(ToolType.SPEAR)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean hasWeldingGun() { // Tool for Builder Agents to build faster.
		if (this.hasTool() && tool.getType().equals(ToolType.WELDINGGUN)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean hasTool() {
		if (tool != null) {
			return true;
		} else {
			return false;
		}
	}

	public void incrementCompletionAmount(AbstractBuilding b, int n) {
		b.incrementCompletionAmount(n);
	}

	// Added getter and setters for condition to interact with HomeDepot
	public int getCondition() {
		return condition;
	}

	public void setCondition(int n) {
		if ((n + condition) > 2000) {
			condition = 2000;
		} else {
			condition += n;
		}
	}

	/**
	 * Adds a tool. Tools don't do anything right now.
	 * @param t
	 */
	public void addTool(Tool t) {
		tool = t;
	}

	/**
	 * Sets carriedResources. Used by AI only.
	 * @param carriedResources
	 */
	private void setAmountCarried(int a) {
		carriedResources = a;
	}

	/**
	 * Sets destination. Used by AI only.
	 * @param destination
	 */
	public void setDestination(Point destination) {
		this.destination = destination;
	}

	/**
	 * Returns destination for view. Don't use this to change Agent's destination.
	 * @return Point destination
	 */
	public Point getDestination() {
		return destination;
	}

	/**
	 * Returns position for view. Don't use this to change Agent's position.
	 * @return Point position
	 */
	public Point getPosition() {
		return position;
	}

	/**
	 * Sends a command and a destination to the Agent. Agent sends this to its
	 * AI for further processing.
	 * 
	 * @param AgentCommandWithDestination
	 */
	public void sendCommand(AgentCommandWithDestination c) {
		AI.recieveCommand(c);
	}

	/**
	 * Moves the Agent a space towards destination. No pathfinding yet, but
	 * Agent randomly chooses whether to move horizantal or vertical towards a
	 * diagonal target.
	 */
	private void move() {
		if (atDestination() || destination == null)
			return;

		boolean pRightOfD = position.x >= destination.x;
		boolean pBelowD = position.y >= destination.y;

		int direction = (int) (Math.random() * 2);

		if (!pRightOfD && !pBelowD) {
			if (position.x == destination.x)
				position = new Point(position.x, position.y + 1);
			else if (position.y == destination.y)
				position = new Point(position.x + 1, position.y);
			else if (direction == 0)
				position = new Point(position.x, position.y + 1);
			else if (direction == 1)
				position = new Point(position.x + 1, position.y);
		} else if (pRightOfD && !pBelowD) {
			if (position.x == destination.x)
				position = new Point(position.x, position.y + 1);
			else if (position.y == destination.y)
				position = new Point(position.x - 1, position.y);
			else if (direction == 0)
				position = new Point(position.x, position.y + 1);
			else if (direction == 1)
				position = new Point(position.x - 1, position.y);
		} else if (pRightOfD && pBelowD) {
			if (position.x == destination.x)
				position = new Point(position.x, position.y - 1);
			else if (position.y == destination.y)
				position = new Point(position.x - 1, position.y);
			else if (direction == 0)
				position = new Point(position.x, position.y - 1);
			else if (direction == 1)
				position = new Point(position.x - 1, position.y);
		} else if (!pRightOfD && pBelowD) {
			if (position.x == destination.x)
				position = new Point(position.x, position.y - 1);
			else if (position.y == destination.y)
				position = new Point(position.x + 1, position.y);
			else if (direction == 0)
				position = new Point(position.x, position.y - 1);
			else if (direction == 1)
				position = new Point(position.x + 1, position.y);
		}
	}

	/**
	 * Checks if Agent is at its destination. Null destination (no command)
	 * returns true because of some null pointer shenanigans doing otherwise
	 * causes.
	 * 
	 * @return position == destination or destination == null
	 */
	private boolean atDestination() {
		if (destination == null)
			return true;

		if (position.x == destination.x && position.y == destination.y)
			return true;
		return false;
	}

	/**
	 * Nice going, commander.
	 */
	public void die() {
		Game g = Game.getInstance();
		for (AbstractAgent a : g.getAgents()) {
			if (a.equals(this))
				g.getAgents().remove(a);
		}
	}

	/**
	 * Does everything Agent needs to do in a game tick, including checking
	 * destination, decrementing needs, and moving toward destinations (every
	 * 10th tick).
	 */
	public void tic() {
		AI.assessCurrentDestination();

		decrementEnergy();
		decrementCondition();
		decrementOil();

		if (energy <= 0 || condition <= 0 || oil <= 0)
			die();

		if (ticInt == 10) {
			checkClosestBuildings();
			move();
			ticInt = 0;
		}

		ticInt++;
	}

	private void checkClosestBuildings() {
		// TODO --- SINNING CODE ZONE ---
		Game g = Game.getInstance();
		double chargeDistance, depotDistance, oilDistance, junkDistance;
		chargeDistance = 999999999;
		depotDistance = 999999999;
		oilDistance = 999999999;
		junkDistance = 999999999;

		for (model.buildings.AbstractBuilding b : g.getBuildings()) {
			if (b.getType().equals(BuildingType.CHARGINGSTATION) && position.distance(b.getLocation()) < chargeDistance) {
				nearestChargingStation = b.getLocation();
				chargeDistance = position.distance(b.getLocation());
			} else if (b.getType().equals(BuildingType.HOMEDEPOT) && position.distance(b.getLocation()) < depotDistance) {
				nearestHomeDepot = b.getLocation();
				depotDistance = position.distance(b.getLocation());
			} else if (b.getType().equals(BuildingType.OILTANK) && position.distance(b.getLocation()) < oilDistance) {
				nearestOilTank = b.getLocation();
				oilDistance = position.distance(b.getLocation());
			} else if (b.getType().equals(BuildingType.JUNKYARD) && position.distance(b.getLocation()) < junkDistance) {
				nearestJunkYard = b.getLocation();
				junkDistance = position.distance(b.getLocation());
			}

		}

	}

	abstract void decrementEnergy();

	abstract void decrementCondition();

	abstract void decrementOil();

	public class AgentLogic {

		/*
		 * Agent should be doing things in this priority: 1. Addressing
		 * critically low needs 2. Depositing Resource if it's at carrying
		 * capacity 3. Following user commands in the order they are issued
		 * 
		 * Critically low energy/condition can be assessed by constantly
		 * measuring distance from a refill station, comparing it to remaining
		 * energy/condition, and moving there once stat remaining after
		 * traveling <= some number greater than 0.
		 * 
		 * User commands expire only once the command has been fully carried
		 * out, i.e. all of specified resource gathered, or until user cancels
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

		public ArrayList<AgentCommandWithDestination> getActionQueue() {
			return actionQueue;
		}

		public void assessCurrentDestination() {
			// Need low
			if (oil < 100)
				actionQueue.add(0, new AgentCommandWithDestination(AgentCommand.REFILL_OIL, nearestOilTank));
			else if (energy < 100)
				actionQueue.add(0, new AgentCommandWithDestination(AgentCommand.REFILL_ENERGY, nearestChargingStation));
			else if (condition < 100)
				actionQueue.add(0, new AgentCommandWithDestination(AgentCommand.REFILL_CONDITION, nearestHomeDepot));

			// Sets destination
			if (!actionQueue.isEmpty())
				setDestination(actionQueue.get(0).getCommandDestination());
			else
				setDestination(null);

			// At destination
			if (atDestination() && destination != null) {
				if (!assessActionAtDestination()) {
					actionQueue.remove(0);
				}
			}
		}

		/**
		 * Returns true if Agent won't need a new destination yet, returns false
		 * if agent needs a new destination.
		 * 
		 * @return
		 */
		public boolean assessActionAtDestination() {
			if (actionQueue.isEmpty())
				return true;

			Game g = Game.getInstance();

			if (actionQueue.get(0).getAgentCommand().equals(AgentCommand.REFILL_OIL) && oil <= MAX_NEED - 50) {
				oil += 50;
				return true;
			} else if (actionQueue.get(0).getAgentCommand().equals(AgentCommand.REFILL_ENERGY)
					&& energy <= MAX_NEED - 50) {
				energy += 50;
				return true;
			} else if (actionQueue.get(0).getAgentCommand().equals(AgentCommand.REFILL_CONDITION)
					&& condition <= MAX_NEED - 50) {
				condition += 50;
				return true;
			}

			if (actionQueue.get(0).getAgentCommand().equals(AgentCommand.FIGHT)) {
				// TODO kill rogue agent
				condition -= 500;
				return false;
			}

			// Collecting a resource
			if (actionQueue.get(0).getAgentCommand().isCollect()) {
				boolean resourceDepleted = false;

				Resource collectingResource = null;
				for (Resource r : g.getResources()) {
					if (r.getLocation().x == position.x && r.getLocation().y == position.y)
						collectingResource = r;
				}

				if (collectingResource.getAmount() < 50) {
					carriedResources += collectingResource.getAmount();
					collectingResource.removeResource(50);
					resourceDepleted = true;
				} else if (carriedResources <= MAX_RESOURCES - 50) {
					carriedResources += 50;
					collectingResource.removeResource(50);
					return true;
				}
				if (actionQueue.get(0).getAgentCommand().equals(AgentCommand.COLLECT_COAL)) {
					if (resourceDepleted)
						actionQueue.remove(0);
					actionQueue.add(0, new AgentCommandWithDestination(AgentCommand.DEPOSIT_COAL, nearestHomeDepot));
					return true;
				} else if (actionQueue.get(0).getAgentCommand().equals(AgentCommand.COLLECT_COPPER)) {
					if (resourceDepleted)
						actionQueue.remove(0);
					actionQueue.add(0, new AgentCommandWithDestination(AgentCommand.DEPOSIT_COPPER, nearestJunkYard));
					return true;
				} else if (actionQueue.get(0).getAgentCommand().equals(AgentCommand.COLLECT_ELECTRICITY)) {
					if (resourceDepleted)
						actionQueue.remove(0);
					actionQueue.add(0, new AgentCommandWithDestination(AgentCommand.DEPOSIT_ELECTRICITY,
							nearestChargingStation));
					return true;
				} else if (actionQueue.get(0).getAgentCommand().equals(AgentCommand.COLLECT_GOLD)) {
					if (resourceDepleted)
						actionQueue.remove(0);
					actionQueue.add(0, new AgentCommandWithDestination(AgentCommand.DEPOSIT_GOLD, nearestJunkYard));
					return true;
				} else if (actionQueue.get(0).getAgentCommand().equals(AgentCommand.COLLECT_IRON)) {
					if (resourceDepleted)
						actionQueue.remove(0);
					actionQueue.add(0, new AgentCommandWithDestination(AgentCommand.DEPOSIT_IRON, nearestJunkYard));
					return true;
				} else if (actionQueue.get(0).getAgentCommand().equals(AgentCommand.COLLECT_OIL)) {
					if (resourceDepleted)
						actionQueue.remove(0);
					actionQueue.add(0, new AgentCommandWithDestination(AgentCommand.DEPOSIT_OIL, nearestOilTank));
					return true;
				}
			}

			if (actionQueue.get(0).getAgentCommand().isDeposit()) {
				// TODO --- SINNING CODE ZONE ---
				int buildingIndex = -1;
				for (int i = 0; i < g.getBuildings().size(); i++) {
					if (g.getBuildings().get(i).getLocation().x == destination.x
							&& g.getBuildings().get(i).getLocation().y == destination.y) {
						buildingIndex = i;
					}
				}

				if (actionQueue.get(0).getAgentCommand().equals(AgentCommand.DEPOSIT_COAL))
					g.getBuildings().get(buildingIndex).agentAddCapacity(ResourceType.COAL, carriedResources);
				else if (actionQueue.get(0).getAgentCommand().equals(AgentCommand.DEPOSIT_COPPER))
					g.getBuildings().get(buildingIndex).agentAddCapacity(ResourceType.COPPER, carriedResources);
				else if (actionQueue.get(0).getAgentCommand().equals(AgentCommand.DEPOSIT_IRON))
					g.getBuildings().get(buildingIndex).agentAddCapacity(ResourceType.IRON, carriedResources);
				else if (actionQueue.get(0).getAgentCommand().equals(AgentCommand.DEPOSIT_ELECTRICITY))
					g.getBuildings().get(buildingIndex).agentAddCapacity(ResourceType.ELECTRICITY, carriedResources);
				else if (actionQueue.get(0).getAgentCommand().equals(AgentCommand.DEPOSIT_GOLD))
					g.getBuildings().get(buildingIndex).agentAddCapacity(ResourceType.GOLD, carriedResources);
				else if (actionQueue.get(0).getAgentCommand().equals(AgentCommand.DEPOSIT_OIL))
					g.getBuildings().get(buildingIndex).agentAddCapacity(ResourceType.OIL, carriedResources);

				setAmountCarried(0);
			}

			return false;
		}
	}
}