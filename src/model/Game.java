package model;


import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Random;

import javax.swing.Timer;

import model.agents.AbstractAgent;
import model.agents.AgentCommand;
import model.agents.AgentCommandWithDestination;
import model.buildings.*;
import model.resources.Resource;


public class Game extends Observable implements Serializable{

	private static Game game;
	private ArrayList<AbstractBuilding> buildings;
	private	ArrayList<Resource> resources;
	private ArrayList<AbstractAgent> agents;
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
	
	private Game() {
		this.map = new Map(GlobalSettings.MAP_SIZE_X, GlobalSettings.MAP_SIZE_Y);
		this.buildings = new ArrayList<AbstractBuilding>();
		this.resources = new ArrayList<Resource>();
		this.agents = new ArrayList<AbstractAgent>();


		//TODO intitial resource generation

		generateResources();

		//TODO intitial agent generation

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

	private void generateResources(){
		// generates log(sqrt(MapSizeX*MapSizeY))* MapRichness\
		Random r = new Random();
		for(int i = 0; i < Math.log(Math.sqrt(GlobalSettings.MAP_SIZE_X * GlobalSettings.MAP_SIZE_Y)) * GlobalSettings.MAP_RICHNESS; i++){

			//TODO: actually generate resources

		}

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
			//			// removal of resources // causes sprite to stop halfway
			//			for(int i = 0; i < resources.size(); i++){
			//				if(!resources.get(i).hasResources()){
			//					resources.remove(i);
			//					i--;
			//				}
			setChanged();
			notifyObservers();
			notifyObservers(resources);

		}
	}
}
//}