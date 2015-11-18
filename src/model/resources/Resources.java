package model.resources;

public enum Resources {
	COAL(0),
	COPPER(1),
	ELECTRICITY(2),
	GOLD(3),
	IRON(4),
	OIL(5);
	
	private int identifier;
	
	private Resources(int i){
		identifier = i;
	}
	
	public int getIdentifer(){
		return identifier;
	}
	
}
