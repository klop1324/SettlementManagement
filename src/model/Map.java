package model;

import java.awt.Graphics;

public class Map {
	
	int[][] map;
	int xLength, yLength;

	public Map(int i, int j) {
		this.map = new int[i][j];
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
	
	public void paintComponent(Graphics g){
		for(int i = 0; i < xLength; i++){
			for(int j = 0; j < yLength; j++){
				//TODO paint all the tiles
			}
		}
	}

}
