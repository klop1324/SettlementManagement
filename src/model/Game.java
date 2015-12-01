package model;


import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Random;

import javax.swing.Timer;

import model.agents.AbstractAgent;
import model.agents.SoldierAgent;
import model.agents.WorkerAgent;
import model.buildings.*;
import model.resources.Resource;
import model.resources.ResourceType;
import model.tools.Tool;

public class Game extends Observable {

	private Game game = this;
	private ArrayList<AbstractBuilding> buildings;
	private	ArrayList<Resource> resources;
	private ArrayList<AbstractAgent> agents;
	private Map map;

	private Timer timer;
	private Timer agentTimer;
	private int[] currentResources;
	private ChargingStation charge;
	private OilTank oilTank;
	private Armory armory;
	private JunkYard junkYard;
	private HomeDepot homeDepot;
	private Resource electric;
	private Resource electric2;
	private Resource oil;
	private Resource coal;
	private Resource copper;
	private Resource iron;
	private Resource gold;
	private SoldierAgent firstAgent;
	private WorkerAgent secondAgent;
	private boolean collected = false;
	private Point resourcePointClicked;
	private Point buildingDest;
	private Resource resourceClicked = null;

	public Game() {
		this.map = new Map(GlobalSettings.MAP_SIZE_X, GlobalSettings.MAP_SIZE_Y);
		this.buildings = new ArrayList<AbstractBuilding>();
		this.resources = new ArrayList<Resource>();
		this.agents = new ArrayList<AbstractAgent>();

		//TODO implement tick system


		//TODO intitial resource generation

		generateResources();

		//TODO intitial agent generation

		addToGameTemporary();

		timer = new Timer(50, new TickActionListener());
		agentTimer = new Timer(200, new AgentListener());
		timer.start();

	}

	/*
	 *  Ugly code, will need to be changed during refactorization
	 *  Agent moves to resource user clicks
	 *	they will grab 10 and then if agent travels to 
	 *	building that holds that resource it will add to that building
	 */

	public void agentToResource(Point resourcePointClicked) {
		agentTimer.start();
		this.resourcePointClicked = resourcePointClicked;
		resourceClicked = getResourceClicked(resourcePointClicked);
		if (resourceClicked.hasResources()){
			sendAgentsToResource(resourceClicked);
			for (AbstractAgent a: agents){
				if (a.getPosition().equals(resourceClicked.getLocation())){
					resourceClicked.removeResource(10, a);
					System.out.println(resourceClicked.getNotification());
					collected = true;
					agentTimer.stop(); // Has to stop timer to keep from continally taking from resource.
				}
				else {
					// Agent should be moving if he reaches this conditional
				}
			}
			agentTimer.start();
		}
		else {
			agentTimer.stop();
		}
	}

	
	public void agentToBuilding(Resource r){
		AbstractBuilding building = findBuildingForResource(r);
		sendAgentsToBuilding(building);
		for (AbstractAgent a: agents) {
			if (a.getPosition().equals(building.getLocation())){
				building.agentAddCapacity(r.getType(), a.getAmountCarried(), a);
				collected = false;
			}
		}
	}
	
	private void sendAgentsToBuilding(AbstractBuilding b){
		for (AbstractAgent a: agents){
			a.setDestination(b.getLocation());
		}
	}
	
	private AbstractBuilding findBuildingForResource(Resource r){
		AbstractBuilding building = null;
		for (AbstractBuilding b: buildings){
			if (b.getResources().containsKey(r.getType())){
				building = b;
			}
		}
		return building;
	}
	
	private void sendAgentsToResource(Resource r){
		for (AbstractAgent agent: agents){
			agent.setDestination(r.getLocation());
		}
	}
	
	private Resource getResourceClicked(Point resourcePoint){
		Resource resource = null;
		for (Resource r: resources){
			if (r.getLocation().equals(resourcePoint)){
				resource = r;
			}
		}
		return resource;
	}
	
	// should create tool
	public void createTool(Resource r1, Resource r2){
		for (AbstractBuilding b : buildings){
			if (b.getResources().containsKey(r1.getType())) {
				// Removes that resource amount
				b.getResources().replace(r1.getType(), b.getResources().get(r1.getType()) - 1);
			}
			if (b.getResources().containsKey(r2.getType())) {
				// Removes that resource amount
				b.getResources().replace(r2.getType(), b.getResources().get(r2.getType()) - 1);
			}
		}
		new Tool(r1, r2);
	}
	
	public void createBuilding(Point p, BuildingType b){
		switch (b){
		case ARMORY:
			for (Resource r: resources){ // Removing from user resources
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
					
				}
			}
			buildings.add(new Armory(100, p));
			break;
		case CHARGINGSTATION:
			buildings.add(new ChargingStation(100, p));
			break;
		case HOMEDEPOT:
			buildings.add(new HomeDepot(100, p));
			break;
		case JUNKYARD:
			buildings.add(new JunkYard(100, p));
			break;
		case OILTANK:
			buildings.add(new OilTank(100, p));
			break;
		case OILWELL:
			buildings.add(new OilWell(100, p));
			break;
		case WORKSHOP:
			buildings.add(new WorkShop(100, p));
			break;
		default:
			break;
		
		}
	}

	// Temporarily adds hardcoded resources, buildings, and agent to game.
	private void addToGameTemporary(){
		addBuilding(charge);
		addBuilding(oilTank);		
		addBuilding(junkYard);
		addBuilding(armory);
		addBuilding(homeDepot);
		addResource(electric2);
		addResource(coal);
		addResource(copper);
		addResource(iron);
		addResource(gold);
		addResource(electric);
		addResource(oil);
		addAgents(secondAgent);
	}

	private void generateResources(){
		// generates log(sqrt(MapSizeX*MapSizeY))* MapRichness\
		Random r = new Random();
		for(int i = 0; i < Math.log(Math.sqrt(GlobalSettings.MAP_SIZE_X * GlobalSettings.MAP_SIZE_Y)) * GlobalSettings.MAP_RICHNESS; i++){

			//TODO: actually generate resources

		}

		// Temporarily initializes the hardcoded resources
		charge = new ChargingStation(1000, new Point(10, 5));
		oilTank = new OilTank(1000, new Point(10, 4));
		junkYard = new JunkYard(1000, new Point(11, 3));
		armory = new Armory(1000, new Point(12, 3));
		homeDepot = new HomeDepot(1000, new Point(13, 6));
		electric = new Resource(20, new Point(0, 0), ResourceType.ELECTRICITY);
		electric2 = new Resource(20, new Point(0, 1), ResourceType.ELECTRICITY);
		oil = new Resource(20, new Point(0, 2), ResourceType.OIL);	
		coal = new Resource(20, new Point(4, 7), ResourceType.COAL);
		copper = new Resource(30, new Point(9, 8), ResourceType.COPPER);
		iron = new Resource(20, new Point(8, 6), ResourceType.IRON);
		gold = new Resource(40, new Point(9, 6), ResourceType.GOLD);	
		firstAgent = new SoldierAgent(new Point(11,4));
		secondAgent = new WorkerAgent(new Point(6, 6));

	}


	public Map getMap() {
		return map;
	}

	public void addBuilding(AbstractBuilding b){
		this.buildings.add(b);
	}

	public void addAgents(AbstractAgent agent){
		this.agents.add(agent);
	}

	public void addResource(Resource resource) {
		this.resources.add(resource);
	}

	public ArrayList<AbstractAgent> getAgents() {
		return agents;
	}

	public ArrayList<Resource> getResources() {
		return resources;
	}

	public ArrayList<AbstractBuilding> getBuildings() {
		return buildings;
	}
	
	private class AgentListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (collected){
				agentToBuilding(resourceClicked);
			}
			else {
				agentToResource(resourcePointClicked);	
			}
			
			for (AbstractAgent a: agents){
				if (a.getPosition().equals(a.getDestination())){
					timer.stop();
				}
				else {	
					a.tic();
				}
			}
			
			setChanged();
			notifyObservers();
			notifyObservers(resources);
		}
		
	}

	private class TickActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {


			//tick all the agents
//			for(int i = 0; i < agents.size(); i++) {
//				if (agents.get(i).getPosition().equals(agents.get(i).getDestination())){
//					timer.stop();
//				}
//				else {	
//					agents.get(i).tic();
//				}
//			}

			//collect the resources for each building
//			for(int i = 0; i < buildings.size(); i++){
//				AbstractBuilding b = buildings.get(i);
//				if(b.isPassiveProvider()){
//
//				}
//			}
			// removal of resources // causes sprite to stop halfway
			for(int i = 0; i < resources.size(); i++){
				if(!resources.get(i).hasResources()){
					resources.remove(i);
					i--;
				}
				
			setChanged();
			notifyObservers();
			notifyObservers(resources);

		}
	}
}
}