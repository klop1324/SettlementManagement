package model;

public class Map {
	
	short[][] map;
	int xLength, yLength;

	public Map(int i, int j) {
		this.map = new short[i][j];
		this.xLength = i;
		this.yLength = j;
		this.generate();
	}
	
	private void generate(){
		//TODO make actual procedual generation
		
		for(int i = 0; i < xLength; i++){
			for(int j = 0; j < yLength; j++){
				map[i][j] = 1;
			}
		}
		
	}

}
