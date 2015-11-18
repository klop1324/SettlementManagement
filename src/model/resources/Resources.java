package model.resources;

public enum Resources {
	
	COAL(1),
	COPPER(2),
	ELECTRICITY(3),
	GOLD(4),
	IRON(5),
	OIL(6);
	
	private int value;

	Resources(int value){
		this.value = value;
	}
	
	public int getValue(){
		return value;
	}
	
}
