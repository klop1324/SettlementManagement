package model;

<<<<<<< HEAD
import java.awt.Graphics;
import java.awt.Point;
=======
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
>>>>>>> b3f63e43832b6031aabe5735eb74298b2e78b2e2
import java.util.ArrayList;
import java.util.Observable;
import java.util.Random;

import javax.swing.Timer;

import model.agents.AbstractAgent;
import model.agents.WorkerAgent;
import model.buildings.AbstractBuilding;
import model.buildings.JunkYard;
import model.resources.AbstractResource;
<<<<<<< HEAD
import model.resources.Iron;


public class Game extends Observable {

=======
import model.resources.Resources;

public class Game extends Observable {
	
	private Game game = this;
>>>>>>> b3f63e43832b6031aabe5735eb74298b2e78b2e2
	private ArrayList<AbstractBuilding> buildings;
	private	ArrayList<AbstractResource> resources;
	private ArrayList<AbstractAgent> agents;
	private Map map;
<<<<<<< HEAD
	private Iron iron;
	private JunkYard junkyard;
	
=======
	private Timer timer;
	private int[] currentResources;

>>>>>>> b3f63e43832b6031aabe5735eb74298b2e78b2e2
	public Game() {
		this.map = new Map(GlobalSettings.MAP_SIZE_X, GlobalSettings.MAP_SIZE_Y);
		this.buildings = new ArrayList<AbstractBuilding>();
		this.resources = new ArrayList<AbstractResource>();
		this.agents = new ArrayList<AbstractAgent>();
<<<<<<< HEAD

		//TODO implement tick system

		//TODO intitial resource generation
		iron = new Iron(10, new Point(0, 0));
		resources.add(iron);
		junkyard = new JunkYard("Junk Yard", 40, new Point(13, 7));
		buildings.add(junkyard);
		//TODO intitial agent generation
		WorkerAgent agent = new WorkerAgent(new Point(1,1));
=======
		
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
>>>>>>> b3f63e43832b6031aabe5735eb74298b2e78b2e2
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
>>>>>>> b3f63e43832b6031aabe5735eb74298b2e78b2e2

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
