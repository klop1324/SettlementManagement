package model.resources;

public enum ResourceType {
	
	COAL(1),
	COPPER(2),
	ELECTRICITY(3),
	GOLD(4),
	IRON(5),
	OIL(6),
	TOOL(7);
	
	private int value;

	ResourceType(int value){
		this.value = value;
	}
	
	public int getValue(){
		return value;
	}
	
}
