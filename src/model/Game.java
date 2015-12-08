package model;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import javax.swing.Timer;

import model.agents.AbstractAgent;
import model.agents.AgentCommand;
import model.agents.AgentCommandWithDestination;
import model.agents.BuilderAgent;
import model.agents.Enemy;
import model.agents.SoldierAgent;
import model.agents.WorkerAgent;
import model.buildings.AbstractBuilding;
import model.buildings.BuildingGenerator;
import model.buildings.VictoryMonument;
import model.resources.Resource;
import model.resources.ResourceGenerator;
import model.resources.ResourceType;
import model.tools.ToolType;

public class Game extends Observable implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Game game;
	private ArrayList<AbstractBuilding> buildings;
	private ArrayList<Resource> mapResources;
	private ArrayList<AbstractAgent> agents;
	private ArrayList<AbstractBuilding> buildingsInProcess;
	private ArrayList<Enemy> enemies;
	private HashMap<ResourceType, Integer> playerResources;
	private Map map;
	private String notification;

	private Timer timer;

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

		ResourceGenerator rGenerator = new ResourceGenerator(map.getXLength(), map.getYLength(), map);
		mapResources = rGenerator.generateResources();
		
		BuildingGenerator bGenerator= new BuildingGenerator(mapResources, map);
		buildings = bGenerator.generate(mapResources, map);
		
		initAgents(bGenerator.getHome());
		
		
		this.startGame();

	}
	private void initAgents(Point p){
		agents.add(new WorkerAgent(p));
		agents.add(new WorkerAgent(new Point(p.x+1, p.y)));
		agents.add(new WorkerAgent(new Point(p.x-1, p.y)));
		agents.add(new SoldierAgent(new Point(p.x+1, p.y+1)));
		agents.add(new BuilderAgent(new Point(p.x+1, p.y-1)));
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
	
	public boolean canCreateAgent(AbstractAgent a){
		return canRemoveResources(a.getCostMap());
	}

	
	public void createAgent(AbstractAgent agentClass) {
		if(agentClass.getClass().equals(WorkerAgent.class)){
			removeResources(agentClass.getCostMap());
			agents.add(new WorkerAgent(agentClass.getPosition()));
		}
		if(agentClass.getClass().equals(SoldierAgent.class)){
			removeResources(agentClass.getCostMap());
			agents.add(new SoldierAgent(agentClass.getPosition()));
		}
		if(agentClass.getClass().equals(BuilderAgent.class)){
			removeResources(agentClass.getCostMap());
			agents.add(new BuilderAgent(agentClass.getPosition()));
		}
	}

	public void addAgents(AbstractAgent agent) {
		this.agents.add(agent);
	}

	public ArrayList<AbstractAgent> getAgents() {
		return agents;
	}
	
	public String getNotification(){
		return notification;
	}
	public void killEnemy(int enemyID) {
		for(int i = 0; i < enemies.size(); i++) {
			if(enemies.get(i).getID() == enemyID)
				enemies.remove(i);
		}
	}

	public boolean canCreateTool(ToolType tool){
		return canRemoveResources(tool.getCost());
	}

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
	
	public boolean canBuildBuilding(AbstractBuilding b) {
		return canRemoveResources(b.getCost()) && canPlaceBuilding(b.getLocation());
	}
	
	public void addResources(ResourceType r, int amount){
		for(AbstractBuilding b: buildings){
			if(b.getResources().contains(r)){
				if(b.canInsert(r, (double) amount)){
					b.addResource(r, amount);
					return;
				}
			}
		}
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
		if(!canBuildBuilding(b)) throw new RuntimeException("cannot create!");
		removeResources(b.getCost());
		buildingsInProcess.add(b);
	}

	public Map getMap() {
		return map;
	}

	public ArrayList<Enemy> getEnemies() {
		return enemies;
	}

	public ArrayList<Resource> getMapResources() {
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
	
	public boolean haveLost(){
		return haveLost;
	}

	public boolean haveWonTheGame() {
		return haveWon;
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

	private Resource getResourceClicked(Point resourcePoint) {
		Resource resource = null;
		for (Resource r : mapResources) {
			if (r.getLocation().equals(resourcePoint)) {
				resource = r;
			}
		}
		return resource;
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
			
			// Notification panel for resources being consumed
			for (AbstractAgent a: agents){
				for (Resource r: mapResources){
					if (a.getPosition().equals(r.getLocation()) && a.getPosition().equals(a.getDestination())){
						if (a.getClass().equals(WorkerAgent.class)){
							notification = "Agent has picked up " + a.getCarriedResources() + " " + r.getType();
						}
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
