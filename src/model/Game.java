package model;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Observable;

import model.agents.AbstractAgent;
import model.buildings.AbstractBuilding;
import model.resources.AbstractResource;

public class Game extends Observable {
	
	private ArrayList<AbstractBuilding> buildings;
	private	ArrayList<AbstractResource> resources;
	private ArrayList<AbstractAgent> agents;
	private Map map;

	public Game() {
		this.map = new Map(GlobalSettings.MAP_SIZE_X, GlobalSettings.MAP_SIZE_Y);
		this.buildings = new ArrayList<AbstractBuilding>();
		this.resources = new ArrayList<AbstractResource>();
		this.agents = new ArrayList<AbstractAgent>();
		
		//TODO implement tick system
		
		//TODO intitial resource generation
		
		//TODO intitial agent generation
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
