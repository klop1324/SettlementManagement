package model.buildings;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;

import model.GlobalSettings;
import model.Map;
import model.Tile;
import model.resources.Resource;

public class BuildingGenerator implements Serializable{
	
	ArrayList<AbstractBuilding> buildings;
	ArrayList<Resource> mapResources;
	Map map;
	Point homeDepot;
	
	public BuildingGenerator(ArrayList<Resource> mapResources, Map m){
		this.buildings = new ArrayList<AbstractBuilding>();
		this.mapResources = mapResources;
		this.map = m;
	}
	
	public ArrayList<AbstractBuilding> generate(ArrayList<Resource> mresources, Map m){
		this.map = m;
		this.mapResources = mresources;
		Point temp;
		// building and agent generation
		do{
			temp = new Point((int)(Math.random() * GlobalSettings.MAP_SIZE_X),(int) (Math.random() * GlobalSettings.MAP_SIZE_Y));
		}
		while(!canInitBuildings(temp));
		initBuildings(temp);
		this.homeDepot = temp;
		return buildings;
	}
	
	public Point getHome(){
		return homeDepot;
	}
	
	private void initBuildings(Point p){
		if(!canInitBuildings(p)) throw new RuntimeException("y u no check if you can build");
		
		System.out.println("generated at point:"+ p.toString());
		
		buildings.add(new HomeDepot(p));
		buildings.add(new JunkYard(new Point(p.x+1, p.y+1)));
		buildings.add(new ChargingStation(new Point(p.x-1, p.y-1)));
		buildings.add(new OilTank(new Point(p.x-1, p.y+1)));
		for(AbstractBuilding b: buildings){
			b.incrementCompletionAmount(b.getBuildTime()+1);
		}
		
		
	}
	
	private boolean canInitBuildings(Point p){
		if(!(p.x< GlobalSettings.MAP_SIZE_X-1) || !(p.x>1)) return false;
		if(!(p.y< GlobalSettings.MAP_SIZE_Y-1) || !(p.y>1)) return false;
		Tile values[] = Tile.values();
		boolean flag = true;
		//mess to check is there's a empty square around p.x,p.y
		Point tempP = p;
		if(!canPlace(p)) flag = false;
		tempP = new Point(p.x+1, p.y);
		if(!canPlace(p)) flag = false;
		tempP = new Point(p.x+1, p.y+1);
		if(!canPlace(p)) flag = false;
		tempP = new Point(p.x+1, p.y-1);
		if(!canPlace(p)) flag = false;
		tempP = new Point(p.x, p.y);
		if(!canPlace(p)) flag = false;
		tempP = new Point(p.x, p.y+1);
		if(!canPlace(p)) flag = false;
		tempP = new Point(p.x, p.y-1);
		if(!canPlace(p)) flag = false;
		tempP = new Point(p.x-1, p.y);
		if(!canPlace(p)) flag = false;
		tempP = new Point(p.x-1, p.y+1);
		if(!canPlace(p)) flag = false;
		tempP = new Point(p.x-1, p.y-1);
		if(!canPlace(p)) flag = false;
		
		return flag;
	}
	
	public boolean canPlace(Point p){
		for(AbstractBuilding b: buildings){
			if(b.getLocation().equals(p)){
				return false;
			}
		}
		for(Resource r : mapResources){
			if(r.getLocation().equals(p)){
				return false;
			}
		}
		if(!Tile.values()[map.get(p)].isPassible()) return false; 
		return true;
	}
}
