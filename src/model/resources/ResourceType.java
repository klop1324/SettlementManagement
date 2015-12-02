package model.resources;

public enum ResourceType {
	
	COAL(1,true),
	COPPER(2,true),
	ELECTRICITY(3,false),
	GOLD(4,true),
	IRON(5,true),
	OIL(6,true),
	TOOL(7,false);
	
	private int value;
	private boolean spawns;

	ResourceType(int value, boolean spawns){
		this.value = value;
		this.spawns = spawns;
	}
	
	public int getValue(){
		return value;
	}
	public boolean isSpawnable(){
		return spawns;
	}
	
}
