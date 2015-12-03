package model.tools;

import java.util.HashMap;

import model.resources.ResourceType;

public enum ToolType {

	WELDINGGUN(50, ResourceType.COPPER, 100),
	ROCKETS(100, ResourceType.OIL, 400),
	PICKAXE(50, ResourceType.IRON, 100),
	ARMOR(100, ResourceType.IRON, 200)
	;
	
	private int effect;
	private HashMap<ResourceType, Integer> cost;
	
	private ToolType(int effect, ResourceType resource, int cost){
		this.effect = effect;
		this.cost = new HashMap<ResourceType, Integer>();
		this.cost.put(resource, cost);
		
	}
	
	public int getToolEffect(){
		return effect;
	}
	
	public HashMap<ResourceType, Integer> getCost(){
		return cost;
	}
	
}
