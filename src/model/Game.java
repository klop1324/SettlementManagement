package model;


import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Random;

import javax.swing.Timer;

import model.agents.AbstractAgent;
import model.agents.AgentCommand;
import model.agents.AgentCommandWithDestination;
import model.agents.BuilderAgent;
import model.agents.WorkerAgent;
import model.buildings.Building;
import model.buildings.BuildingType;
import model.resources.Resource;
import model.resources.ResourceType;
import model.tools.Tool;


public class Game extends Observable implements Serializable{

	private static Game game;
	private ArrayList<Building> buildings;
	private	ArrayList<Resource> mapResources;
	private ArrayList<AbstractAgent> agents;
	private ArrayList<Building> buildingsInProcess;
	private HashMap<ResourceType, Integer > playerResources;
	private Map map;

	private Timer timer;
	private Timer agentTimer;
	private boolean collected = false;
	private Point resourcePointClicked;
	private Resource resourceClicked = null;

	public static synchronized Game getInstance(){
		if(game == null){
			game = new Game();
		}
		return game;

	}

	public Game() {
		this.map = new Map(GlobalSettings.MAP_SIZE_X, GlobalSettings.MAP_SIZE_Y);
		this.buildings = new ArrayList<Building>();
		this.mapResources = new ArrayList<Resource>();
		this.agents = new ArrayList<AbstractAgent>();
		this.buildingsInProcess = new ArrayList<Building>();
		this.playerResources = new HashMap<ResourceType, Integer>();
		for(ResourceType r: ResourceType.values()){
			this.playerResources.put(r, 0);
		}

		//TODO intitial resource generation

		generateResources();

		//TODO intitial agent generation

		timer = new Timer(50, new TickActionListener());
		timer.start();

	}


	public void agentToResource(Point resourcePointClicked) {
		
		WorkerAgent agentToSend = null;
		this.resourcePointClicked = resourcePointClicked;
		ResourceType resourceTypeClicked = getResourceClicked(resourcePointClicked).getType();
		
		for(AbstractAgent a : agents) {
			if(a.getClass() == WorkerAgent.class)
				agentToSend = (WorkerAgent) a;
		}
		
		switch(resourceTypeClicked) {
		case COAL:
			agentToSend.sendCommand(new AgentCommandWithDestination(AgentCommand.COLLECT_COAL, resourcePointClicked));
			break;
		case COPPER:
			agentToSend.sendCommand(new AgentCommandWithDestination(AgentCommand.COLLECT_COPPER, resourcePointClicked));
			break;
		case ELECTRICITY:
			agentToSend.sendCommand(new AgentCommandWithDestination(AgentCommand.COLLECT_ELECTRICITY, resourcePointClicked));
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
		}
	}

	private Building findBuildingForResource(Resource r){
		Building building = null;
		for (Building b: buildings){
			if (b.getResources().contains(r)){
				building = b;
			}
		}
		return building;
	}
	private Resource getResourceClicked(Point resourcePoint){
		Resource resource = null;
		for (Resource r: mapResources){
			if (r.getLocation().equals(resourcePoint)){
				resource = r;
			}
		}
		return resource;
	}

	/**
	 * Creates a tool and equipts it to specific agent object
	 * @param r1
	 * resource one
	 * @param r2
	 * resource two
	 * @param a
	 * agent
	 */
	public void createTool(Resource r1, Resource r2, AbstractAgent a){
		for (Building b : buildings){
			// if the building has the resource
			if (b.getResources().contains(r1.getType())&& b.getResources().contains(r2.getType())) {
				// Removes that resource amount
				b.removeResource(r1.getType(), b.getResourceAmount(r1.getType()) - 1);
				b.removeResource(r2.getType(), b.getResourceAmount(r2.getType()) - 1);
				break;
			}
		}
		a.addTool(new Tool(r1, r2)); // adds tool to agent
	}

	/**
	 * Creates a building by checking if the location is an empty tile 
	 * and then using BuildingType to add a new instance of that type of building
	 * to the buildingsInProgress ArrayList.
	 * @param p
	 * Point in which building is being placed.
	 * @param b
	 * BuildingType of new Building.
	 */
	public void createBuilding(Point p, BuildingType b){
		// TODO Refactor once functionality is figured out
		for (Building buildings: buildings){
			// bip stands for building in process
			for(Building bip: buildingsInProcess) {
				for (Resource r: mapResources){
					if ((p.equals(r.getLocation()) || p.equals(bip.getLocation()) 
							|| p.equals(buildings.getLocation()))){
						System.out.println("Cannot be placed over another object!");
						return;
					}
				}
			}
		}
		
		switch (b){
		case ARMORY:
			armoryCost();
			buildingsInProcess.add(new Building(BuildingType.ARMORY, p));
			break;
		case CHARGINGSTATION:
			chargingStationCost();
			buildingsInProcess.add(new Building(BuildingType.CHARGINGSTATION, p));
			break;
		case HOMEDEPOT:
			homeDepotCost();
			buildingsInProcess.add(new Building(BuildingType.HOMEDEPOT, p));
			break;
		case JUNKYARD:
			junkYardCost();
			buildingsInProcess.add(new Building(BuildingType.JUNKYARD, p));
			break;
		case OILTANK:
			oilTankCost();
			buildingsInProcess.add(new Building(BuildingType.OILTANK, p));
			break;
		case OILWELL:
			oilWellCost();
			buildingsInProcess.add(new Building(BuildingType.OILWELL, p));
			break;
		case WORKSHOP:
			workShopCost();
			buildingsInProcess.add(new Building(BuildingType.WORKSHOP, p));
			break;
		default:
			break;

		}
		System.out.println(buildingsInProcess);
	}

	public void armoryCost(){
		for (Resource r: mapResources){ // Removing from user resources
			if (r.getType().equals(ResourceType.COAL)){
				r.spendResource(10);
			}
			if (r.getType().equals(ResourceType.GOLD)){
				r.spendResource(5);
			}
			if (r.getType().equals(ResourceType.IRON)){
				r.spendResource(15);
			}
			else {
				// Else statement
			}
		}
	}

	public void chargingStationCost(){
		for (Resource r: mapResources){ // Removing from user resources
			if (r.getType().equals(ResourceType.COAL)){
				r.spendResource(10);
			}
			if (r.getType().equals(ResourceType.GOLD)){
				r.spendResource(5);
			}
			if (r.getType().equals(ResourceType.IRON)){
				r.spendResource(15);
			}
			else {
				// Else statement
			}
		}
	}

	public void homeDepotCost(){
		for (Resource r: mapResources){ // Removing from user resources
			if (r.getType().equals(ResourceType.COAL)){
				r.spendResource(10);
			}
			if (r.getType().equals(ResourceType.GOLD)){
				r.spendResource(5);
			}
			if (r.getType().equals(ResourceType.IRON)){
				r.spendResource(15);
			}
			else {
				// Else statement
			}
		}
	}

	public void junkYardCost(){
		for (Resource r: mapResources){ // Removing from user resources
			if (r.getType().equals(ResourceType.COAL)){
				r.spendResource(10);
			}
			if (r.getType().equals(ResourceType.GOLD)){
				r.spendResource(5);
			}
			if (r.getType().equals(ResourceType.IRON)){
				r.spendResource(15);
			}
			else {
				// Else statement
			}
		}
	}

	public void oilTankCost(){
		for (Resource r: mapResources){ // Removing from user resources
			if (r.getType().equals(ResourceType.COAL)){
				r.spendResource(10);
			}
			if (r.getType().equals(ResourceType.GOLD)){
				r.spendResource(5);
			}
			if (r.getType().equals(ResourceType.IRON)){
				r.spendResource(15);
			}
			else {
				// Else statement
			}
		}
	}

	public void oilWellCost(){
		for (Resource r: mapResources){ // Removing from user resources
			if (r.getType().equals(ResourceType.COAL)){
				r.spendResource(10);
			}
			if (r.getType().equals(ResourceType.GOLD)){
				r.spendResource(5);
			}
			if (r.getType().equals(ResourceType.IRON)){
				r.spendResource(15);
			}
			else {
				// Else statement
			}
		}
	}

	public void workShopCost(){
		for (Resource r: mapResources){ // Removing from user resources
			if (r.getType().equals(ResourceType.COAL)){
				r.spendResource(10);
			}
			if (r.getType().equals(ResourceType.GOLD)){
				r.spendResource(5);
			}
			if (r.getType().equals(ResourceType.IRON)){
				r.spendResource(15);
			}
			else {
				// Else statement
			}
		}
	}

	private void generateResources(){
		// generates log(sqrt(MapSizeX*MapSizeY))* MapRichness\
		Random r = new Random();
		for(int i = 0; i < Math.log(Math.sqrt(GlobalSettings.MAP_SIZE_X * GlobalSettings.MAP_SIZE_Y)) * GlobalSettings.MAP_RICHNESS; i++){

			//TODO: actually generate resources

		}

		mapResources.add(new Resource(40, new Point(5,3), ResourceType.IRON));
		mapResources.add(new Resource(40, new Point(5,4), ResourceType.COPPER));
		mapResources.add(new Resource(40, new Point(5,5), ResourceType.GOLD));
		mapResources.add(new Resource(40, new Point(5,6), ResourceType.OIL));
		mapResources.add(new Resource(40, new Point(10, 3), ResourceType.ELECTRICITY));

		buildings.add(new Building(BuildingType.ARMORY, new Point(6,9)));
		buildings.add(new Building(BuildingType.JUNKYARD, new Point(7,9)));
		buildings.add(new Building(BuildingType.OILWELL, new Point(8,9)));
		addBuilding(new Building(BuildingType.CHARGINGSTATION, new Point(9,9)));
		addBuilding(new Building(BuildingType.OILTANK, new Point(10,9)));

		
		addAgents(new WorkerAgent(new Point(4, 9)));

	}

	public Map getMap() {
		return map;
	}

	public void addBuildingInProcess(Building b){
		this.buildingsInProcess.add(b);
	}

	public void addBuilding(Building b){
		this.buildings.add(b);
	}

	public void addAgents(AbstractAgent agent){
		this.agents.add(agent);
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

	public HashMap<ResourceType, Integer> getPlayerResources(){
		return playerResources;
	}
	
	public ArrayList<Building> getBuildings() {
		return buildings;
	}

	public ArrayList<Building> getBuildingsInProcess() {
		return buildingsInProcess;
	}

	private class TickActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			// Updates agents
			if(!agents.isEmpty()) {
				for(AbstractAgent a : agents)
					a.tic();
			}
			
			for (AbstractAgent ba : agents) {
				if (ba.getClass().equals(BuilderAgent.class)){
					ba.setDestination(buildingsInProcess.get(0).getLocation());
				}
			}
			// removal of resources // causes sprite to stop halfway
			for(int i = 0; i < mapResources.size(); i++){
				if(!mapResources.get(i).hasResources()){
					mapResources.remove(i);
					i--;
				}
			}

			// Checks if buildings are completed and adds the completed ones to 
			// the buildings list.
			if (!buildingsInProcess.isEmpty()){	
				for (Building b: buildingsInProcess) {
					if (b.isCompleted()){
						buildings.add(b);
						buildingsInProcess.remove(b);
					}
				}
			}

			setChanged();
			notifyObservers();
			notifyObservers(mapResources);

		}
	}
}
