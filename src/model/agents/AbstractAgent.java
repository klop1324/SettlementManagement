package model.agents;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;

import model.Game;
import model.Map;
import model.buildings.AbstractBuilding;
import model.buildings.BuildingType;
import model.resources.Resource;
import model.resources.ResourceType;
import model.tools.ToolType;

public abstract class AbstractAgent implements Serializable {
	protected int energy, condition, oil, carriedResources, MAX_RESOURCES, MAX_NEED, ticInt, gatherRate, damageFromEnemies,
			moveDelay, numberOfTasks;
	protected Point position, destination, nearestOilTank, nearestHomeDepot, nearestChargingStation, nearestJunkYard;
	protected AgentLogic AI;
	protected ResourceType carriedResourceType;
	protected double buildRate;
	protected Map map;

	/**
	 * Creates a new AbstractAgent at a given position.
	 * 
	 * @param Point
	 *            position
	 */
	public AbstractAgent(Point position) {
		energy = 10000;
		condition = 10000;
		oil = 10000;
		MAX_NEED = 10000;
		carriedResources = 0;
		destination = null;
		this.position = position;
		gatherRate = 50;
		damageFromEnemies = 500;
		moveDelay = 10;
		buildRate = 0.1;
	}
	
	public AgentLogic getAI() {
		return AI;
	}

	public void incrementCompletionAmount(AbstractBuilding b, int n) {
		b.incrementCompletionAmount(n);
	}

	// Added getter and setters for condition to interact with HomeDepot
	public int getCondition() {
		return condition;
	}

	/**
	 * Adds a tool. This actually just changes certain fixed instance variables.
	 * 
	 * @param t
	 */
	public void addTool(ToolType t) {
		switch (t) {
		case ARMOR:
			damageFromEnemies = 100;
		case PICKAXE:
			gatherRate = 500;
		case WELDINGGUN:
			buildRate = 0.5;
		case ROCKETS:
			moveDelay = 5;
		}
	}

	/**
	 * Sets carriedResources. Used by AI only.
	 * 
	 * @param carriedResources
	 */
	private void setAmountCarried(int a) {
		carriedResources = a;
	}

	/**
	 * Sets destination. Used by AI only.
	 * 
	 * @param destination
	 */
	public void setDestination(Point destination) {
		this.destination = destination;
	}

	/**
	 * Returns destination for view. Don't use this to change Agent's
	 * destination.
	 * 
	 * @return Point destination
	 */
	public Point getDestination() {
		return destination;
	}

	/**
	 * Returns position for view. Don't use this to change Agent's position.
	 * 
	 * @return Point position
	 */
	public Point getPosition() {
		return position;
	}

	public int getEnergy() {
		return energy;
	}

	public int getOil() {
		return oil;
	}
	
	public int getCarriedResources(){
		return carriedResources;
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
	 * Moves the Agent a space towards destination. Placeholder terrible
	 * pathfinding where the Agent will move horizantally until it reaches
	 * the correct column or gets blocked, at which point it moves vertically
	 * until it either hits its destination or stops being blocked horizantally.
	 */
	private void move() {
		if (atDestination() || destination == null)
			return;
		
		boolean inDestinationColumn = position.x == destination.x;
		boolean pRightOfD = position.x >= destination.x;
		boolean pBelowD = position.y >= destination.y;
		
		// Horizantal movement cases
		if(!inDestinationColumn) {
			if(pRightOfD && !map.blocked(new Point(position.x - 1, position.y))) {
				position = new Point(position.x - 1, position.y);
				return;
			}
			if(!pRightOfD && !map.blocked(new Point(position.x + 1, position.y))) {
				position = new Point(position.x + 1, position.y);
				return;
			}
		}
		
		// Vertical movement cases
		if(pBelowD && !map.blocked(new Point(position.x, position.y - 1))) {
			position = new Point(position.x, position.y - 1);
			return;
		}
		if(!pBelowD && !map.blocked(new Point(position.x + 1, position.y))) {
			position = new Point(position.x + 1, position.y);
			return;
		}
		
		// Movement failed due to block, try this as a last resort
		if(!map.blocked(new Point(position.x - 1, position.y))) {
			position = new Point(position.x - 1, position.y);
			return;
		}
		if(!map.blocked(new Point(position.x + 1, position.y))) {
			position = new Point(position.x + 1, position.y);
			return;
		}

//		int direction = (int) (Math.random() * 2);
//
//		if (!pRightOfD && !pBelowD) {
//			if (position.x == destination.x)
//				position = new Point(position.x, position.y + 1);
//			else if (position.y == destination.y)
//				position = new Point(position.x + 1, position.y);
//			else if (direction == 0)
//				position = new Point(position.x, position.y + 1);
//			else if (direction == 1)
//				position = new Point(position.x + 1, position.y);
//		} else if (pRightOfD && !pBelowD) {
//			if (position.x == destination.x)
//				position = new Point(position.x, position.y + 1);
//			else if (position.y == destination.y)
//				position = new Point(position.x - 1, position.y);
//			else if (direction == 0)
//				position = new Point(position.x, position.y + 1);
//			else if (direction == 1)
//				position = new Point(position.x - 1, position.y);
//		} else if (pRightOfD && pBelowD) {
//			if (position.x == destination.x)
//				position = new Point(position.x, position.y - 1);
//			else if (position.y == destination.y)
//				position = new Point(position.x - 1, position.y);
//			else if (direction == 0)
//				position = new Point(position.x, position.y - 1);
//			else if (direction == 1)
//				position = new Point(position.x - 1, position.y);
//		} else if (!pRightOfD && pBelowD) {
//			if (position.x == destination.x)
//				position = new Point(position.x, position.y - 1);
//			else if (position.y == destination.y)
//				position = new Point(position.x + 1, position.y);
//			else if (direction == 0)
//				position = new Point(position.x, position.y - 1);
//			else if (direction == 1)
//				position = new Point(position.x + 1, position.y);
//		}
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
	 * Does everything Agent needs to do in a game tick, including checking
	 * destination, decrementing needs, and moving toward destinations (every
	 * 10th tick).
	 */
	public void tic() {
		AI.assessCurrentDestination();

		decrementEnergy();
		decrementCondition();
		decrementOil();

		if (ticInt == moveDelay) {
			checkClosestBuildings();
			move();
			ticInt = 0;
		}

		ticInt++;
	}

	private void checkClosestBuildings() {
		Game g = Game.getInstance();
		double chargeDistance, depotDistance, oilDistance, junkDistance;
		chargeDistance = 999999999;
		depotDistance = 999999999;
		oilDistance = 999999999;
		junkDistance = 999999999;

		for (AbstractBuilding b : g.getBuildings()) {
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

	public class AgentLogic implements Serializable {

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
		private Class agentClass;

		public AgentLogic(Class agentClass) {
			actionQueue = new ArrayList<AgentCommandWithDestination>();
			this.agentClass = agentClass;
		}

		public void recieveCommand(AgentCommandWithDestination c) {
			actionQueue.add(c);
		}

		public ArrayList<AgentCommandWithDestination> getActionQueue() {
			return actionQueue;
		}

		public void assessCurrentDestination() {
			Game g = Game.getInstance();
			ArrayList<Enemy> enemyList = g.getEnemies();
			ArrayList<AbstractBuilding> incompleteBuildingList = g.getBuildingsInProcess();
			
			// Need low
			if (oil < 1000) {
				if(actionQueue.isEmpty())
					actionQueue.add(new AgentCommandWithDestination(AgentCommand.REFILL_OIL, nearestOilTank));
				else if(!actionQueue.get(0).getAgentCommand().isRefill())
					actionQueue.add(0, new AgentCommandWithDestination(AgentCommand.REFILL_OIL, nearestOilTank));
			}
			if (energy < 1000) {
				if(actionQueue.isEmpty())
					actionQueue.add(new AgentCommandWithDestination(AgentCommand.REFILL_ENERGY, nearestChargingStation));
				else if(!actionQueue.get(0).getAgentCommand().isRefill())
					actionQueue.add(0, new AgentCommandWithDestination(AgentCommand.REFILL_ENERGY, nearestChargingStation));
			}
			if (condition < 1000) {
				if(actionQueue.isEmpty())
					actionQueue.add(new AgentCommandWithDestination(AgentCommand.REFILL_CONDITION, nearestHomeDepot));
				else if(!actionQueue.get(0).getAgentCommand().isRefill())
					actionQueue.add(0, new AgentCommandWithDestination(AgentCommand.REFILL_CONDITION, nearestHomeDepot));
			}
			
			// Builders need to check for buildings to build
			if(agentClass == BuilderAgent.class && actionQueue.isEmpty() && !incompleteBuildingList.isEmpty()) {
				actionQueue.add(new AgentCommandWithDestination(AgentCommand.BUILD,
						incompleteBuildingList.get(0).getLocation()));
			}

			// Sets destination
			if (!actionQueue.isEmpty()) {
				if(actionQueue.get(0).getAgentCommand() == AgentCommand.FIGHT) {
					for(Enemy e : enemyList) {
						if(e.getID() == actionQueue.get(0).getEnemyID())
							setDestination(e.getPosition());
					}
				} else
					setDestination(actionQueue.get(0).getCommandDestination());
			}
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

			if (actionQueue.get(0).getAgentCommand().equals(AgentCommand.REFILL_OIL) && oil <= MAX_NEED - 500) {
				for(AbstractBuilding b : g.getBuildings()) {
					if(b.getLocation().equals(position)) {
						if(b.getResourceAmount(ResourceType.OIL) < 500) {
							energy += b.getResourceAmount(ResourceType.OIL);
							b.agentRemoveCapacity(ResourceType.OIL, b.getResourceAmount(ResourceType.OIL));
							return false;
						} else {
							b.agentRemoveCapacity(ResourceType.OIL, 500);
							oil += 500;
							return true;
						}
					}
				}
			} else if (actionQueue.get(0).getAgentCommand().equals(AgentCommand.REFILL_ENERGY)
					&& energy <= MAX_NEED - 500) {
				for(AbstractBuilding b : g.getBuildings()) {
					if(b.getLocation().equals(position)) {
						if(b.getResourceAmount(ResourceType.ELECTRICITY) < 500) {
							energy += b.getResourceAmount(ResourceType.ELECTRICITY);
							b.agentRemoveCapacity(ResourceType.ELECTRICITY, b.getResourceAmount(ResourceType.ELECTRICITY));
							return false;
						} else {
							energy += 500;
							b.agentRemoveCapacity(ResourceType.ELECTRICITY, 500);
							return true;
						}
					}
				}
			} else if (actionQueue.get(0).getAgentCommand().equals(AgentCommand.REFILL_CONDITION)
					&& condition <= MAX_NEED - 500) {
				for(AbstractBuilding b : g.getBuildings()) {
					if(b.getLocation().equals(position)) {
						if(b.getResourceAmount(ResourceType.IRON) < 500) {
							energy += b.getResourceAmount(ResourceType.IRON);
							b.agentRemoveCapacity(ResourceType.IRON, b.getResourceAmount(ResourceType.IRON));
							return false;
						} else {
							b.agentRemoveCapacity(ResourceType.IRON, 500);
							condition += 500;
							return true;
						}
					}
				}
			}
			
			if(actionQueue.get(0).getAgentCommand().equals(AgentCommand.BUILD)) {
				for(AbstractBuilding b : g.getBuildingsInProcess()) {
					if(b.getLocation().equals(position)) {
						if(b.isCompleted())
							return false;
						else
							b.incrementCompletionAmount(buildRate);
					}
				}
			}

			if (actionQueue.get(0).getAgentCommand().equals(AgentCommand.FIGHT)) {
				for (int i = 0; i < g.getEnemies().size(); i++) {
					if (g.getEnemies().get(i).getPosition().equals(position)) {
						g.killEnemy(g.getEnemies().get(i).getID());
						condition -= damageFromEnemies;
					}
				}
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
				
				if(collectingResource == null) // collectingResource has been depleted and removed already
					return false;

				if (collectingResource.getAmount() <= gatherRate) {
					carriedResources += collectingResource.getAmount();
					collectingResource.removeResource(gatherRate);
					resourceDepleted = true;
				} else if (carriedResources <= MAX_RESOURCES - gatherRate) {
					carriedResources += gatherRate;
					collectingResource.removeResource(gatherRate);
					return true;
				}
				if (actionQueue.get(0).getAgentCommand().equals(AgentCommand.COLLECT_COAL)) {
					actionQueue.add(0, new AgentCommandWithDestination(AgentCommand.DEPOSIT_COAL, nearestHomeDepot));
					if (resourceDepleted)
						actionQueue.remove(1);
					return true;
				} else if (actionQueue.get(0).getAgentCommand().equals(AgentCommand.COLLECT_COPPER)) {
					actionQueue.add(0, new AgentCommandWithDestination(AgentCommand.DEPOSIT_COPPER, nearestJunkYard));
					if (resourceDepleted)
						actionQueue.remove(1);
					return true;
				} else if (actionQueue.get(0).getAgentCommand().equals(AgentCommand.COLLECT_ELECTRICITY)) {
					actionQueue.add(0, new AgentCommandWithDestination(AgentCommand.DEPOSIT_ELECTRICITY,
							nearestChargingStation));
					if (resourceDepleted)
						actionQueue.remove(1);
					return true;
				} else if (actionQueue.get(0).getAgentCommand().equals(AgentCommand.COLLECT_GOLD)) {
					actionQueue.add(0, new AgentCommandWithDestination(AgentCommand.DEPOSIT_GOLD, nearestJunkYard));
					if (resourceDepleted)
						actionQueue.remove(1);
					return true;
				} else if (actionQueue.get(0).getAgentCommand().equals(AgentCommand.COLLECT_IRON)) {
					actionQueue.add(0, new AgentCommandWithDestination(AgentCommand.DEPOSIT_IRON, nearestJunkYard));
					if (resourceDepleted)
						actionQueue.remove(1);
					return true;
				} else if (actionQueue.get(0).getAgentCommand().equals(AgentCommand.COLLECT_OIL)) {
					actionQueue.add(0, new AgentCommandWithDestination(AgentCommand.DEPOSIT_OIL, nearestOilTank));
					if (resourceDepleted)
						actionQueue.remove(1);
					return true;
				}
			}

			if (actionQueue.get(0).getAgentCommand().isDeposit()) {
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