package model.agents;

import java.awt.Point;

import model.Game;
import model.buildings.Building;
import model.buildings.BuildingType;
import model.resources.ResourceType;

/**
 * Enemies spawn in random places on the map and move towards a random building
 * with resources. Once there, they steal player resources, then take them and
 * dump them (delete them) where they spawned.
 * 
 * @author mirandamots
 *
 */
public class Enemy {
	int ticCounter, carriedResources, MAX_RESOURCES;
	Point position, destination, home, targetBuildingLocation;
	ResourceType stealingResource;

	/**
	 * Creates a new Enemy at a given Point position. Initial position should be
	 * randomly determined by Game.
	 * 
	 * @param Point
	 *            position
	 */
	public Enemy(Point position) {
		ticCounter = -1;
		carriedResources = 0;
		MAX_RESOURCES = 100;
		home = position;
		this.position = position;
	}
	
	/**
	 * Run only right after initialization. Determines what Building the Enemy is
	 * going to and what Resource it's going to steal.
	 */
	private void determineTargetBuilding() {
		Game g = Game.getInstance();
		boolean buildingFound = false;
		
		// Searches for an appropriate target building
		while(!buildingFound) {
			int buildingIndex = (int) Math.random() * g.getBuildings().size();
			Building targetBuildingTemp = g.getBuildings().get(buildingIndex);
			
			if(targetBuildingTemp.getBuildingType() == BuildingType.CHARGINGSTATION) {
				buildingFound = true;
				stealingResource = ResourceType.ELECTRICITY;
				targetBuildingLocation = targetBuildingTemp.getLocation();
			} else if(targetBuildingTemp.getBuildingType() == BuildingType.HOMEDEPOT) {
				buildingFound = true;
				stealingResource = ResourceType.COAL;
				targetBuildingLocation = targetBuildingTemp.getLocation();
			} else if(targetBuildingTemp.getBuildingType() == BuildingType.JUNKYARD) {
				buildingFound = true;
				targetBuildingLocation = targetBuildingTemp.getLocation();

				// Randomly picks a junkyard resource
				switch((int) Math.random() * 3) {
				case 0:
					stealingResource = ResourceType.COPPER;
					break;
				case 1:
					stealingResource = ResourceType.IRON;
					break;
				case 2:
					stealingResource = ResourceType.GOLD;
					break;
				}
			}
		}
		
		destination = targetBuildingLocation;
	}


	/**
	 * Returns position for drawing purposes.
	 * 
	 * @return Point position
	 */
	public Point getPosition() {
		return position;
	}
	
	/**
	 * Basic update method. Call this from Game whenever Game ticks.
	 */
	public void tic() {
		if(ticCounter == -1) determineTargetBuilding();
		
		ticCounter++;
		
		if(position.equals(destination))
			actionAtDestination();
		else if(ticCounter >= 20) {
			move();
			ticCounter = 0;
		}
	}

	/**
	 * Determines enemy action at destination. If it's at targetBuilding,
	 * it should steal. If it's at max capacity or player has no more of
	 * that resource, it should head home. If it's at home, it should dump
	 * its resources and head back to the building.
	 */
	private void actionAtDestination() {
		Game g = Game.getInstance();
		Building thisBuilding = null;
		for(Building b : g.getBuildings()) {
			if(b.getLocation().equals(destination))
				thisBuilding = b;
		}
		
		// At target building
		if(destination.equals(targetBuildingLocation)) {
			if(atResourceCapacity() || thisBuilding.getResourceAmount(stealingResource) == 0) {
				destination = home;
				return;
			} else if(thisBuilding.getResourceAmount(stealingResource) < 10) {
				carriedResources += thisBuilding.getResourceAmount(stealingResource);
				thisBuilding.removeResource(stealingResource, thisBuilding.getResourceAmount(stealingResource));
				destination = home;
				return;
			} else {
				carriedResources += 10;
				thisBuilding.removeResource(stealingResource, 10);
				return;
			}
		}
		
		// At home
		if(destination.equals(home)) {
			carriedResources = 0;
			destination = targetBuildingLocation;
		}
	}

	/**
	 * Checks if Enemy is carrying max resources
	 * @return
	 */
	private boolean atResourceCapacity() {
		if(carriedResources == MAX_RESOURCES)
			return true;
		return false;
	}

	/**
	 * Moves enemy. Called by tic().
	 */
	private void move() {
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

}
