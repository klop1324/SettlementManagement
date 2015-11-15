package model;

public enum Tile {
	//===============================
	//==> DO NOT SKIP NUMBERS!!!! <==
	//===============================
	OIL(0, 'o'), PLATING(1, ' '), GRAVEL(2, 'G');
	
	int intRep;
	char charRep;
	
	Tile(int i, char c){
		this.intRep = i;
		this.charRep = c;
		
		//TODO: load images based on the integer representation number
		
	}
	public int getIntRepresentation(){
		return intRep;
	}
	public char getCharRepresentation(){
		return charRep;
	}
}
