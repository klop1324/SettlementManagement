package model;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Set;

import javax.swing.Timer;

import model.agents.AbstractAgent;
import model.agents.AgentCommand;
import model.agents.AgentCommandWithDestination;
import model.agents.BuilderAgent;
import model.agents.Enemy;
import model.agents.SoldierAgent;
import model.agents.WorkerAgent;
import model.buildings.AbstractBuilding;
import model.buildings.Armory;
import model.buildings.BuildingType;
import model.buildings.ChargingStation;
import model.buildings.HomeDepot;
import model.buildings.JunkYard;
import model.buildings.OilTank;
import model.buildings.OilWell;
import model.buildings.VictoryMonument;
import model.buildings.Workshop;
import model.resources.Resource;
import model.resources.ResourceType;
import model.tools.ToolType;

public class Game extends Observable implements Serializable {

	private static Game game;
	private ArrayList<AbstractBuilding> buildings;
	private ArrayList<Resource> mapResources;
	private ArrayList<AbstractAgent> agents;
	private ArrayList<AbstractBuilding> buildingsInProcess;
	private ArrayList<Enemy> enemies;
	private HashMap<ResourceType, Integer> playerResources;
	private Map map;

	private Timer timer;
	private Point resourcePointClicked = null;

	private boolean haveWon = false;

	public static synchronized Game getInstance() {
		if (game == null) {
			game = new Game();
		}
		return game;

	}

	public Game() {
		this.map = new Map(GlobalSettings.MAP_SIZE_X, GlobalSettings.MAP_SIZE_Y);
		this.buildings = new ArrayList<AbstractBuilding>();
		this.mapResources = new ArrayList<Resource>();
		this.agents = new ArrayList<AbstractAgent>();
		this.buildingsInProcess = new ArrayList<AbstractBuilding>();
		this.enemies = new ArrayList<Enemy>();
		this.playerResources = new HashMap<ResourceType, Integer>();
		for (ResourceType r : ResourceType.values()) {
			this.playerResources.put(r, 0);
		}

		generateResources();

		// TODO intitial agent generation

		agents.add(new WorkerAgent(new Point(6, 6)));
		agents.add(new WorkerAgent(new Point(6, 8)));
		agents.add(new WorkerAgent(new Point(6, 4)));
		agents.add(new SoldierAgent(new Point(7, 7)));
		agents.add(new BuilderAgent(new Point(8, 7)));
		enemies.add(new Enemy(new Point(10, 10)));

		// TODO temp building generation for testing

		buildings.add(new JunkYard(new Point(4, 4)));
		buildings.add(new ChargingStation(new Point(4, 5)));
		buildings.add(new OilTank(new Point(4, 6)));
		buildings.add(new HomeDepot(new Point(4, 7)));

		this.startGame();

	}

	public void agentToEnemy(int enemyIDClicked) {
		SoldierAgent agentToSend = null;

		for (AbstractAgent a : agents) {
			if (a.getClass() == SoldierAgent.class)
				agentToSend = (SoldierAgent) a;
		}

		AgentCommandWithDestination fight = new AgentCommandWithDestination(AgentCommand.FIGHT, null);
		fight.setEnemyID(enemyIDClicked);

		if (agentToSend != null)
			agentToSend.sendCommand(fight);
	}
	
	public void agentToResource(Point resourcePointClicked) {

		WorkerAgent agentToSend = null;
		this.resourcePointClicked = resourcePointClicked;
		ResourceType resourceTypeClicked = getResourceClicked(resourcePointClicked).getType();

		int min = 0;
		for (AbstractAgent a : agents) {
			if (a.getClass() == WorkerAgent.class && a.getAI().getActionQueue().size() <= min) {
				agentToSend = (WorkerAgent) a;
				min = a.getAI().getActionQueue().size();
			}
		}

		switch (resourceTypeClicked) {
		case COAL:
			agentToSend.sendCommand(new AgentCommandWithDestination(AgentCommand.COLLECT_COAL, resourcePointClicked));
			break;
		case COPPER:
			agentToSend.sendCommand(new AgentCommandWithDestination(AgentCommand.COLLECT_COPPER, resourcePointClicked));
			break;
		case ELECTRICITY:
			agentToSend.sendCommand(new AgentCommandWithDestination(AgentCommand.COLLECT_ELECTRICITY,
					resourcePointClicked));
			break;
		case GOLD:
			agentToSend.sendCommand(new AgentCommandWithDestination(AgentCommand.COLLECT_GOLD, resourcePointClicked));
			break;
		case IRON:
			agentToSend.sendCommand(new AgentCommandWithDestination(AgentCommand.COLLECT_IRON, resourcePointClicked));
			break;
		case OIL:
			agentToSend.sendCommand(new AgentCommandWithDestination(AgentCommand.COLLECT_OIL, resourcePointClicked));
			break;
		default:
			break;
		}
	}

	private Resource getResourceClicked(Point resourcePoint) {
		Resource resource = null;
		for (Resource r : mapResources) {
			if (r.getLocation().equals(resourcePoint)) {
				resource = r;
			}
		}
		return resource;
	}
	
	public boolean canCreateTool(ToolType tool){
		boolean flag = false;
		for (AbstractBuilding b : buildings) {
			Set<ResourceType> resources = tool.getCost().keySet();
			for (ResourceType trt : resources) {
				if (b.getResources().contains(trt)) {
					flag = true;
				}
				if (!flag) {
					flag = false;
					break;
				}
			}
			if (flag)
				break;
		}
		return flag;
	}

	/**
	 * Creates a tool and equipts it to specific agent object
	 * 
	 * @param r1
	 *            resource one
	 * @param r2
	 *            resource two
	 * @param a
	 *            agent
	 */
	public void createTool(ToolType tool, AbstractAgent a) {
		boolean flag = false;
		for (AbstractBuilding b : buildings) {
			Set<ResourceType> resources = tool.getCost().keySet();
			for (ResourceType trt : resources) {
				if (b.getResources().contains(trt)) {
					flag = true;
				}
				if (!flag) {
					flag = false;
					break;
				}
			}
			if (flag) {
				for (ResourceType trt : resources) {
					for (ResourceType brt : b.getResources()) {
						b.removeResource(trt, tool.getCost().get(trt));
					}
				}
				a.addTool(tool);
				break;
			}
		}
	}

	private boolean canPlaceBuilding(Point p) {
		for (AbstractBuilding building : buildings) {
			// bip stands for building in process
			if (p.equals(building.getLocation()))
				return false;
		}
		for (AbstractBuilding bip : buildingsInProcess) {
			if (p.equals(bip.getLocation()))
				return false;
		}
		for (Resource r : mapResources) {
			if (p.equals(r.getLocation()))
				return false;
		}
		return true;
	}

	private boolean haveResourcesForBuilding(Point p, AbstractBuilding b) {
		boolean flag = false;
		for (AbstractBuilding tb : buildings) {
			Set<ResourceType> resources = b.getCost().keySet();
			for (ResourceType trt : resources) {
				if (tb.getResources().contains(trt)) {
					flag = true;
				}
				if (!flag) {
					flag = false;
					break;
				}
			}
		}
		return flag;
	}

	public boolean canBuildBuilding(Point p, AbstractBuilding b) {
		return haveResourcesForBuilding(p, b) && canPlaceBuilding(p);
	}

	/**
	 * Creates a building by checking if the location is an empty tile and then
	 * using BuildingType to add a new instance of that type of building to the
	 * buildingsInProgress ArrayList.
	 * 
	 * @param p
	 *            Point in which building is being placed.
	 * @param b
	 *            BuildingType of new Building.
	 */
	public void createBuilding(AbstractBuilding b){
		// TODO Refactor once functionality is figured out
		Point p = b.getLocation();
		boolean flag = false;
		for (AbstractBuilding tb : buildings) {
			Set<ResourceType> resources = b.getCost().keySet();
			for (ResourceType trt : resources) {
				if (tb.getResources().contains(trt)) {
					flag = true;
				}
				if (!flag) {
					flag = false;
					break;
				}
			}
			if (flag) {
				for (ResourceType trt : resources) {
					for (ResourceType brt : tb.getResources()) {
						tb.removeResource(trt, b.getCost().get(trt));
					}
				}

				break;
			}
		}
		if (flag)
			buildingsInProcess.add(b);
	}

	private void generateResources() {
		// generates log(sqrt(MapSizeX*MapSizeY))* MapRichness\
		// magic numbers, i know.\
		int size = 32;
		PerlinNoise noise = new PerlinNoise(size, size);
		int generationArray[][] = new int[GlobalSettings.MAP_SIZE_X][GlobalSettings.MAP_SIZE_Y];

		for (int y = 0; y < GlobalSettings.MAP_SIZE_X; y++) {
			for (int x = 0; x < GlobalSettings.MAP_SIZE_Y; x++) {

				float xx = (float) x / generationArray.length * size; // Where
																		// does
																		// the
																		// point
																		// lie
																		// in
																		// the
																		// noise
																		// space
																		// according
																		// to
																		// image
																		// space.
				float yy = (float) y / generationArray[0].length * size; // Where
																			// does
																			// the
																			// point
																			// lie
																			// in
																			// the
																			// noise
																			// space
																			// according
																			// to
																			// image
																			// space.

				float n = (float) noise.noise(xx, yy, 1.7f); // Noise values
																// from Perlin's
																// noise.
				// Since noise value returned is -1 to 1, we need it to be
				// between 0 and Tile.values().length * total Spawn Rate
				int generation = (int) (((n + 1) * (Tile.values().length - 1)));
				if (generation >= (Tile.values().length)) {
					generationArray[x][y] = -1;
				}
			}
		}

		ArrayList<ResourceType> unplacedResources = new ArrayList<ResourceType>();

		// makes a list of unplaced resources
		for (ResourceType resource : ResourceType.values()) {
			if (resource.isSpawnable())
				unplacedResources.add(resource);
		}
		java.util.Collections.shuffle(unplacedResources);

		ArrayList<ResourceType> placedResources = new ArrayList<ResourceType>();

		// places resources
		for (int i = 0; i < generationArray.length; i++) {
			for (int j = 0; j < generationArray[0].length; j++) {
				// if there is a node i should be generating on
				if (generationArray[i][j] == -1) {
					// place call generation helper to generate all the
					// resources ONLY ON TILES THAT ARE PASSABLE
					if (!unplacedResources.isEmpty()) {
						generationArray = generationHelper(generationArray, i, j, unplacedResources.get(0));
						placedResources.add(unplacedResources.get(0));
						unplacedResources.remove(0);
					} else {
						generationArray = generationHelper(generationArray, i, j,
								placedResources.get((int) (Math.random() * placedResources.size())));
					}

				}
			}
		}

	}

	// recursive, going through the blob formed by -1's in the array, adding
	// them to the mapResources
	private int[][] generationHelper(int array[][], int x, int y, ResourceType resource) {
		if (array[x][y] == -1) {
			if (Tile.values()[this.map.get(x, y)].isPassible()) {
				mapResources.add(new Resource((int) (Math.random() + 1) * 1000 * GlobalSettings.MAP_RICHNESS,
						new Point(x, y), resource));
			}
			// base case
			array[x][y] = 0;

			// recursion
			if (x + 1 < array[0].length)
				if (array[x + 1][y] != 0)
					array = generationHelper(array, x + 1, y, resource);
			if (y + 1 < array.length)
				if (array[x][y + 1] != 0)
					array = generationHelper(array, x, y + 1, resource);
			if (x - 1 >= 0)
				if (array[x - 1][y] != 0)
					array = generationHelper(array, x - 1, y, resource);
			if (y - 1 >= 0)
				if (array[x][y - 1] != 0)
					array = generationHelper(array, x, y - 1, resource);
		}
		return array;
	}
	
	public Map getMap() {
		return map;
	}

	public ArrayList<Enemy> getEnemies() {
		return enemies;
	}

	public void addBuildingInProcess(AbstractBuilding b) {

	}

	public void addAgents(AbstractAgent agent) {
		this.agents.add(agent);
	}

	public boolean haveWonTheGame() {
		return haveWon;
	}

	public void addResource(Resource resource) {
		this.mapResources.add(resource);
	}
	
	public ArrayList<AbstractAgent> getAgents() {
		return agents;
	}
	
	public ArrayList<Resource> getResources() {
		return mapResources;
	}

	public HashMap<ResourceType, Integer> getPlayerResources() {
		return playerResources;
	}

	public ArrayList<AbstractBuilding> getBuildings() {
		return buildings;
	}
	
	public ArrayList<AbstractBuilding> getBuildingsInProcess() {
		return buildingsInProcess;
	}

	public static void onLoad(Game inc) {
		game = inc;
	}

	public void startGame() {
		timer = new Timer(50, new TickActionListener());
		timer.start();
	}

	private class TickActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {

			// Updates agents
			if (!agents.isEmpty()) {
				for (int i = 0; i < agents.size(); i++) {
					agents.get(i).tic();
					// Agent death condition
					if (agents.get(i).getEnergy() <= 0 || agents.get(i).getOil() <= 0
							|| agents.get(i).getCondition() <= 0)
						agents.remove(i);
				}
			} else { // LOSE CONDITION
				System.out.println("All of your agents are dead!");
			}

			// Updates enemies
			if (!enemies.isEmpty()) {
				for (Enemy e : enemies)
					e.tic();
			}

			if (!buildingsInProcess.isEmpty()) {
				for (AbstractAgent ba : agents) {
					if (ba.getClass().equals(BuilderAgent.class)) {
						ba.setDestination(buildingsInProcess.get(0).getLocation());
					}
				}
			}

			// removal of resources // causes sprite to stop halfway
			for (int i = 0; i < mapResources.size(); i++) {
				if (!mapResources.get(i).hasResources()) {
					mapResources.remove(i);
					i--;
				}
			}
			// Allows for building passive generation
			for (AbstractBuilding b: buildings){
				if (b.isPassiveProvider()){
					System.out.println(b.getResources());
					b.addResource(b.getPassiveResource(), b.getPassiveRate());
					System.out.println("I have passively generated in " + b.getName() + "!");
				}
			}
			
			// Checks if buildings are completed and adds the completed ones to
			// the buildings list.
			if (!buildingsInProcess.isEmpty()) {
				for (AbstractBuilding b : buildingsInProcess) {
					if (b.isCompleted()) {
						buildings.add(b);
						buildingsInProcess.remove(b);
					}
				}
			}
			
			// Win condition
			if (!buildings.isEmpty()) {
				for (AbstractBuilding b : buildings) {
					if (b.getClass().equals(VictoryMonument.class)) {
						haveWon = true;
					}
					if (b.isPassiveProvider()) {
						ResourceType resource = b.getPassiveResource();
						if (b.canInsert(resource, b.getPassiveRate())) {
							b.addResource(resource, b.getPassiveRate());
						}
					}
				}
			}

			setChanged();
			notifyObservers();
			notifyObservers(mapResources);

		}
	}

}
