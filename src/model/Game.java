package model;


import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Random;

import javax.swing.Timer;

import model.agents.AbstractAgent;
import model.agents.AgentCommand;
import model.agents.AgentCommandWithDestination;
import model.agents.SoldierAgent;
import model.agents.WorkerAgent;
import model.buildings.AbstractBuilding;
import model.buildings.ChargingStation;
import model.buildings.JunkYard;
import model.buildings.OilTank;
import model.resources.Resource;
import model.resources.ResourceType;

public class Game extends Observable {

	private Game game = this;
	private ArrayList<AbstractBuilding> buildings;
	private	ArrayList<Resource> resources;
	private ArrayList<AbstractAgent> agents;
	private Map map;

	private Timer timer;
	private int[] currentResources;
	private ChargingStation charge;
	private OilTank oilTank;
	private Resource electric;
	private Resource oils;
	private SoldierAgent firstAgent;
	private WorkerAgent secondAgent;
	private boolean collect;
	private Point resourcePointClicked;

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
		timer.start();
	}

	/*
	 *  Ugly code, will need to be changed during refactorization
	 *  Agent moves to resource user clicks
	 *	they will grab 10 and then if agent travels to 
	 *	building that holds that resource it will add to that building
	 */

	public void agentToResource(Point resourcePointClicked) {
		this.resourcePointClicked = resourcePointClicked;
		collect = true;
		for (AbstractAgent a: agents) {
			AgentCommandWithDestination comm = new AgentCommandWithDestination(AgentCommand.COLLECT_RESOURCE, resourcePointClicked);
			a.sendCommand(comm); // Was getting index out of bounds error until added these
			for (Resource r: resources) {
				for (AbstractBuilding b: buildings){
					if (a.getPosition().equals(r.getLocation())){ // if agent at a resource
						r.removeResource(10, a); // remove 10 unit resource
						System.out.println(r.getNotification());
						setChanged();
						notifyObservers(r); // Trying to get the notification panel to set to this...
						if (b.getResources().containsKey(r.getType())) { // if the building contains the resource the
							// agent is holding
							Point buildingDest = new Point(b.getLocation());
							a.setDestination(buildingDest); // Agent goes to that building
						}
					}
					else if (a.getPosition().equals(b.getLocation())) { // if agent at building
						b.agentAddCapacity(a.getCarriedResource(), a.getAmountCarried(), a); // agent adds their resource to building 
						System.out.println(b.resourcesToString());
						if (r.hasResources() == false) { // If the resource they were collecting from no longer has anything
							collect = false; // It will stop the agent from moving/collecting
							timer.stop();
						}
						else {
							a.setDestination(resourcePointClicked); // If there is resource left, agent will go back to collect more
						}
					}
					else {
						// Else case
					}
				}
			}
			timer.start();
		}
	}

	private void removeResourceGame(Resource r){
		// Was giving a java.util.ConcurrentModificationException
		resources.remove(r);
	}

	// Temporarily adds hardcoded resources, buildings, and agent to game.
	private void addToGameTemporary(){
		addBuilding(charge);
		addResource(electric);
		addAgents(secondAgent);
	}

	private void generateResources(){
		// generates log(sqrt(MapSizeX*MapSizeY))* MapRichness\
		Random r = new Random();
		for(int i = 0; i < Math.log(Math.sqrt(GlobalSettings.MAP_SIZE_X * GlobalSettings.MAP_SIZE_Y)) * GlobalSettings.MAP_RICHNESS; i++){

			//TODO: actually generate resources

		}

		// Temporarily initializes the hardcoded resources
		charge = new ChargingStation("Charge", 1000, new Point(10, 5));
		oilTank = new OilTank("Oil", 1000, new Point(10, 4));
		electric = new Resource(20, new Point(0, 0), ResourceType.ELECTRICITY);
		oils = new Resource(20, new Point(0, 2), ResourceType.OIL);
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

	private class TickActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {


			//tick all the agents
			for(int i = 0; i < agents.size(); i++) {
				if (agents.get(i).getPosition().equals(agents.get(i).getDestination())){
					timer.stop();
				}
				else {	
					agents.get(i).tic();
				}
			}

			//collect the resources for each building
			for(int i = 0; i < buildings.size(); i++){
				AbstractBuilding b = buildings.get(i);
				if(b.isPassiveProvider()){

				}
			}
//			// removal of resources // causes sprite to stop halfway
//			for(int i = 0; i < resources.size(); i++){
//				if(!resources.get(i).hasResources()){
//					resources.remove(i);
//					i--;
//				}

				if (collect){ // boolean to determine if agent still needs to collect from resource
					agentToResource(resourcePointClicked);
					setChanged();
					notifyObservers();
				}
				setChanged();
				notifyObservers();

			}
		}
	}
//}