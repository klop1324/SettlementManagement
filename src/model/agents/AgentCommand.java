package model.agents;

public enum AgentCommand {
	COLLECT_COAL, COLLECT_COPPER, COLLECT_ELECTRICITY, COLLECT_GOLD, COLLECT_IRON, COLLECT_OIL, DEPOSIT_COAL,
	DEPOSIT_COPPER, DEPOSIT_ELECTRICITY, DEPOSIT_GOLD, DEPOSIT_IRON, DEPOSIT_OIL, REFILL_OIL,
	REFILL_ENERGY, REFILL_CONDITION, FIGHT;
	
	public boolean isCollect() {
		if(this.equals(COLLECT_COAL) || this.equals(COLLECT_COPPER) || this.equals(COLLECT_ELECTRICITY) || this.equals(COLLECT_GOLD) ||
				this.equals(COLLECT_IRON) || this.equals(COLLECT_OIL))
			return true;
		
		return false;
	}
	
	public boolean isDeposit() {
		if(this.equals(DEPOSIT_COAL) || this.equals(DEPOSIT_COPPER) || this.equals(DEPOSIT_ELECTRICITY) || this.equals(DEPOSIT_GOLD) ||
				this.equals(DEPOSIT_IRON) || this.equals(DEPOSIT_OIL))
			return true;
		
		return false;
	}
	
	public boolean isRefill() {
		if(this.equals(REFILL_OIL) || this.equals(REFILL_ENERGY) || this.equals(REFILL_CONDITION))
			return true;
					
		return false;
	}

}