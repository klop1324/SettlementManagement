package model;

public abstract class AbstractAgent {
	protected int energy;
	protected int health;
	protected int oil;
	
	AbstractAgent(){
		// Starting each agent as full
		energy = 100;
		health = 100;
		oil = 100;
	}
	
	// Getters, Setters and Removers
	public int getEnergy(){
		return energy;
	}
	
	public void setEnergy(int newEnergy) {
		energy = newEnergy;
	}
	
	public void removeEnergy(int remEAmount){
		energy -= remEAmount;
	}
	
	public int getOil(){
		return oil;
	}
	
	public void removeOil(int remOilAmount){
		oil-=remOilAmount;
	}
	
	public void setOil(int newOil){
		oil = newOil;
	}
	
	public int getHealth(){
		return health;
	}
	
	public void setHealth(int newHealth) {
		health=newHealth;
	}
	
	public void removeHealth(int remHealth){
		health-=remHealth;
	}
	
	// Abstract method different for each kind of agent
	public abstract void doWork();
	
	
}
