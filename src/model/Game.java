package model;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Observable;

import model.agents.AbstractAgent;
import model.agents.WorkerAgent;
import model.buildings.AbstractBuilding;
import model.buildings.JunkYard;
import model.resources.AbstractResource;
import model.resources.Iron;


public class Game extends Observable {

	private ArrayList<AbstractBuilding> buildings;
	private	ArrayList<AbstractResource> resources;
	private ArrayList<AbstractAgent> agents;
	private Map map;
	private Iron iron;
	private JunkYard junkyard;
	
	public Game() {
		this.map = new Map(GlobalSettings.MAP_SIZE_X, GlobalSettings.MAP_SIZE_Y);
		this.buildings = new ArrayList<AbstractBuilding>();
		this.resources = new ArrayList<AbstractResource>();
		this.agents = new ArrayList<AbstractAgent>();

		//TODO implement tick system

		//TODO intitial resource generation
		iron = new Iron(10, new Point(0, 0));
		resources.add(iron);
		junkyard = new JunkYard("Junk Yard", 40, new Point(13, 7));
		buildings.add(junkyard);
		//TODO intitial agent generation
		WorkerAgent agent = new WorkerAgent(new Point(1,1));
	}
	

	public Map getMap() {
		return map;
	}

	public ArrayList<AbstractAgent> getAgents() {
		return agents;
	}

	public ArrayList<AbstractResource> getResources() {
		return resources;
	}

	public ArrayList<AbstractBuilding> getBuildings() {
		return buildings;
	}


}
