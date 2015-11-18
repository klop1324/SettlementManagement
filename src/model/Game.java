package model;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Random;

import javax.swing.Timer;

import model.agents.AbstractAgent;
import model.buildings.AbstractBuilding;
import model.resources.AbstractResource;
import model.resources.Resources;

public class Game extends Observable {
	
	private Game game = this;
	private ArrayList<AbstractBuilding> buildings;
	private	ArrayList<AbstractResource> resources;
	private ArrayList<AbstractAgent> agents;
	private Map map;
	private Timer timer;
	private int[] currentResources;

	public Game() {
		this.map = new Map(GlobalSettings.MAP_SIZE_X, GlobalSettings.MAP_SIZE_Y);
		this.buildings = new ArrayList<AbstractBuilding>();
		this.resources = new ArrayList<AbstractResource>();
		this.agents = new ArrayList<AbstractAgent>();
		
		timer = new Timer(50, new TickActionListener());
		
		generateResources();
		
		//TODO intitial agent generation
		
		
		
	}
	
	private void generateResources(){
		// generates log(sqrt(MapSizeX*MapSizeY))* MapRichness\
		Random r = new Random();
		for(int i = 0; i < Math.log(Math.sqrt(GlobalSettings.MAP_SIZE_X * GlobalSettings.MAP_SIZE_Y)) * GlobalSettings.MAP_RICHNESS; i++){
			int totalNumOfResources = r.nextInt(Resources.values().length);
			Resources resource = Resources.values()[totalNumOfResources];
			
			//TODO: actually generate resources
			
		}
	}
	

	public Map getMap() {
		return map;
	}
	
	public void addBuilding(AbstractBuilding b, Point p){
		this.buildings.add(b);
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
	
	private class TickActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			timer = new Timer(50, new TickActionListener());
			
			//tick all the agents
			for(int i = 0; i < agents.size(); i++){
				agents.get(i).tic();
			}
			
			//collect the resources for each building
			for(int i = 0; i < buildings.size(); i++){
				AbstractBuilding b = buildings.get(i);
				if(b.isPassiveProvider()){
					
					
				}
			}
		}
		
	}
}
