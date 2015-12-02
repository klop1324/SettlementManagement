package model;


import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Observable;
import java.util.Random;

import javax.swing.Timer;

import model.agents.AbstractAgent;
import model.buildings.*;
import model.resources.Resource;
import model.resources.ResourceType;
import model.tools.Tool;
import sun.net.www.content.audio.basic;
import model.agents.BuilderAgent;
import model.agents.WorkerAgent;


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
		for(ResourceType r: ResourceType.values()){
			this.playerResources.put(r, 0);
		}

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
		Building building = findBuildingForResource(r);
		sendAgentsToBuilding(building);
		for (AbstractAgent a: agents) {
			if (a.getPosition().equals(building.getLocation())){
				building.agentAddCapacity(r.getType(), a.getAmountCarried());
				collected = false;
			}
		}
	}

	private void sendAgentsToBuilding(Building b){
		for (AbstractAgent a: agents){
			a.setDestination(b.getLocation());
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

	private void sendAgentsToResource(Resource r){
		for (AbstractAgent agent: agents){
			agent.setDestination(r.getLocation());
		}
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

	// Should create tool
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

	// Interacts with builder agents to create a building
	// Will also have user interaction to send builder agents to
	// build this building.
	public void createBuilding(Point p, BuildingType b){
		/*
		
			ResourceType keys[] = (ResourceType[]) building.getCost().keySet().toArray();
			HashMap<ResourceType, Integer> m = building.getCost();
			boolean flag = true;
			// checks if the player has the resources to build it
			for(int i = 0; i < keys.length; i++){
				if(playerResources.get(keys[i])- m.get(keys[i]) < 0) flag = false;
			}
			if(flag){
				for(int i = 0; i < keys.length; i++){
					int currVal = playerResources.get(keys[i]);
					playerResources.replace(keys[i], currVal - m.get(keys[i]));
				}
				buildingsInProcess.add(new Armory(p));
			}
		*/
	
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
			notifyObservers(mapResources);
		}

	}

	private class TickActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
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
