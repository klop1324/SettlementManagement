package model.resources;

public enum Resources {
<<<<<<< HEAD
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
=======
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
>>>>>>> b3f63e43832b6031aabe5735eb74298b2e78b2e2
	}
	
}
