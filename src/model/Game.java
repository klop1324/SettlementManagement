package model;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
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
	private boolean haveLost = false;

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

		Point temp;
		// building and agent generation
		do{
			temp = new Point((int)(Math.random() * GlobalSettings.MAP_SIZE_X),(int) (Math.random() * GlobalSettings.MAP_SIZE_Y));
		}
		while(!canInitBuildings(temp));
		initBuildings(temp);
		initAgents(temp);
		
		
		this.startGame();

	}
	private void initAgents(Point p){
		agents.add(new WorkerAgent(p));
		agents.add(new WorkerAgent(new Point(p.x+1, p.y)));
		agents.add(new WorkerAgent(new Point(p.x-1, p.y)));
		agents.add(new SoldierAgent(new Point(p.x+1, p.y+1)));
		agents.add(new BuilderAgent(new Point(p.x+1, p.y-1)));
	}
	
	private void initBuildings(Point p){
		if(!canInitBuildings(p)) throw new RuntimeException("y u no check if you can build");
		
		System.out.println("generated at point:"+ p.toString());
		
		buildings.add(new HomeDepot(p));
		buildings.add(new JunkYard(new Point(p.x+1, p.y+1)));
		buildings.add(new ChargingStation(new Point(p.x-1, p.y-1)));
		buildings.add(new OilTank(new Point(p.x-1, p.y+1)));
		for(AbstractBuilding b: buildings){
			b.incrementCompletionAmount(b.getBuildTime()+1);
		}
		
		
	}
	
	private boolean canInitBuildings(Point p){
		if(!(p.x< GlobalSettings.MAP_SIZE_X-1) || !(p.x>1)) return false;
		if(!(p.y< GlobalSettings.MAP_SIZE_Y-1) || !(p.y>1)) return false;
		Tile values[] = Tile.values();
		boolean flag = true;
		//mess to check is there's a empty square around p.x,p.y
		Point tempP = p;
		if(!canPlace(p)) flag = false;
		tempP = new Point(p.x+1, p.y);
		if(!canPlace(p)) flag = false;
		tempP = new Point(p.x+1, p.y+1);
		if(!canPlace(p)) flag = false;
		tempP = new Point(p.x+1, p.y-1);
		if(!canPlace(p)) flag = false;
		tempP = new Point(p.x, p.y);
		if(!canPlace(p)) flag = false;
		tempP = new Point(p.x, p.y+1);
		if(!canPlace(p)) flag = false;
		tempP = new Point(p.x, p.y-1);
		if(!canPlace(p)) flag = false;
		tempP = new Point(p.x-1, p.y);
		if(!canPlace(p)) flag = false;
		tempP = new Point(p.x-1, p.y+1);
		if(!canPlace(p)) flag = false;
		tempP = new Point(p.x-1, p.y-1);
		if(!canPlace(p)) flag = false;
		
		return flag;
	}
	
	public boolean canPlace(Point p){
		for(AbstractBuilding b: buildings){
			if(b.getLocation().equals(p)){
				return false;
			}
		}
		for(Resource r : mapResources){
			if(r.getLocation().equals(p)){
				return false;
			}
		}
		if(!Tile.values()[map.get(p)].isPassible()) return false; 
		return true;
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
		
		if(agentToSend == null) return; // ERROR ERROR ERROR

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
	
	public void createAgent(Class agentClass, Point position) {
		if(agentClass == WorkerAgent.class)
			agents.add(new WorkerAgent(position));
		if(agentClass == SoldierAgent.class)
			agents.add(new SoldierAgent(position));
		if(agentClass == BuilderAgent.class)
			agents.add(new BuilderAgent(position));
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
		return canRemoveResources(tool.getCost());
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
	public void createTool(ToolType tool) {
		if(!canRemoveResources(tool.getCost())){
			throw new RuntimeException("You didnt check if you could build the tool!");
		}
		AbstractAgent assignedAgent = null;
		for (AbstractAgent a: agents){
			switch(tool){
			case ARMOR:
				if (a.getClass().equals(SoldierAgent.class)){
					assignedAgent = a;
				}
				break;
			case PICKAXE:
				if (a.getClass().equals(WorkerAgent.class)){
					assignedAgent = a;
				}
				break;
			case ROCKETS:
				assignedAgent = a;
				break;
			case WELDINGGUN:
				if (a.getClass().equals(BuilderAgent.class)){
					assignedAgent = a;
				}
				break;
			default:
				break;
			
			}
			if(assignedAgent != null) break;
		}
		removeResources(tool.getCost());
		assignedAgent.addTool(tool);
		
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

	private boolean canRemoveResources(HashMap<ResourceType, Integer> reqResources) {
		boolean flag = true;
		
		ArrayList<Integer> resourceValues = new ArrayList<Integer>();
		for(ResourceType r: ResourceType.values()){
			resourceValues.add(0);
		}
		
		// sums all the resources into resourceValues
		for(ResourceType r: reqResources.keySet()){
			for(AbstractBuilding b: buildings){
				if(b.getResources().contains(r)){
					resourceValues.set(r.getValue(), (resourceValues.get(r.getValue()) + b.getResourceAmount(r)));
				}
			}
		}
		// checks for every resource that the there are more resources total than required
		for(ResourceType r: reqResources.keySet()){
			if(!(resourceValues.get(r.getValue()) >= reqResources.get(r))) flag = false;
		}
		
		return flag;
	}
	
	private void removeResources(HashMap<ResourceType, Integer> reqResources) {		
		if(!canRemoveResources(reqResources)) throw new RuntimeException("something has gone wrong");
		
		for(AbstractBuilding b: buildings){
			for(ResourceType r: reqResources.keySet()){
				if(reqResources.get(r)> 0 && b.getResources().contains(r)){ 
					if(b.getResourceAmount(r) > 0){
						 int buildingResourceAmount = b.getResourceAmount(r);
						 int reqResourceAmount = reqResources.get(r);
						 
						 if(buildingResourceAmount <= reqResourceAmount){
							 b.removeResource(r, buildingResourceAmount);
							 reqResources.replace(r, reqResourceAmount-buildingResourceAmount);
						 }
						 else{
							 b.removeResource(r, reqResourceAmount);
							 reqResources.replace(r, 0);
						 }
					 }
				}
			}
		}
		
	}

	public boolean canBuildBuilding(AbstractBuilding b) {
		return canRemoveResources(b.getCost()) && canPlaceBuilding(b.getLocation());
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
		if(!canBuildBuilding(b)) throw new RuntimeException("cannot create!");
		
		removeResources(b.getCost());
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

				float xx = (float) x / generationArray.length * size; 
				float yy = (float) y / generationArray[0].length * size; 

				float n = (float) noise.noise(xx, yy, 1.7f); 
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
	// recursive, going through the blob formed by -1's in the array, adding them to the mapResources
	private int[][] generationHelper(int array[][], int x, int y, ResourceType resource){
		if(array[x][y] == -1){
			if(Tile.values()[this.map.get(x, y)].isPassible()){
				mapResources.add(new Resource(((int) ((Math.random()+5.0) * 1000.0 * GlobalSettings.MAP_RICHNESS)), new Point(x,y), resource));
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
		this.buildingsInProcess.add(b);
	}
	public boolean haveLost(){
		return haveLost;
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

	public void stopGame(){
		if(timer!=null)timer.stop();
	}
	
	public void killEnemy(int enemyID) {
		for(int i = 0; i < enemies.size(); i++) {
			if(enemies.get(i).getID() == enemyID)
				enemies.remove(i);
		}
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
				game.haveLost = true;
			}

			// Updates enemies
			if (!enemies.isEmpty()) {
				for (Enemy e : enemies)
					e.tic();
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
					if(b.canInsert(b.getPassiveResource(), b.getPassiveRate())){
						b.passiveAddResource(b.getPassiveResource(), b.getPassiveRate());
					}
				}
			}
			
			// Checks if buildings are completed and adds the completed ones to
			// the buildings list.
			if (!buildingsInProcess.isEmpty()) {
				for (int i = 0; i < buildingsInProcess.size(); i++) {
					if (buildingsInProcess.get(i).isCompleted()) {
						buildings.add(buildingsInProcess.get(i));
						buildingsInProcess.remove(i);
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
			
			if(Math.random()<0.001){
				Point p = new Point((int)(map.getXLength()*Math.random()), (int) (map.getYLength() *Math.random()));
				if(Tile.values()[map.get(p.x, p.y)].isPassible())
					enemies.add(new Enemy(p));
			}

			setChanged();
			notifyObservers();
			notifyObservers(mapResources);

		}
	}

}
