package model;

import java.awt.Point;
import java.io.Serializable;
import java.util.Observable;

public class Map extends Observable implements Serializable{
	
	
	public static void main(String args[]){
		Map map = new Map(100,100);
		System.out.println(map.toString());
		
	}
	
	private static final int size = 6;	// this is the size of the biome, smaller means larger biomes  
	private int[][] map;
	private boolean[][] isVisible;
	private int xLength, yLength;

	public Map(int i, int j) {
		if(i < 2 || j < 2) throw new RuntimeException("I got passed something less than 2! Here's what was passed to me i = " +i + " j = " +j);
		this.map = new int[i][j];
		this.isVisible = new boolean[i][j];
		this.xLength = i;
		this.yLength = j;
		
		for(int k = 0; k < xLength; k++){
			for(int t = 0; t < yLength; t++){
				isVisible[k][t] = false;
				map[k][t] = -1;
			}
		}
		
		this.generate();
	}
	
	// makes the map
	private void generate(){
		PerlinNoise noise = new PerlinNoise(size, size);
		int totalSpawnChance = 0;
		for(Tile t: Tile.values()){
			totalSpawnChance += t.getSpawnRate();
		}
		
		for (int y = 0; y < this.yLength; y++) {
	         for (int x = 0; x < this.xLength; x++) {
	            
	            float xx = (float) x / this.xLength * size; // Where does the point lie in the noise space according to image space. 
	            float yy = (float) y / this.yLength * size; // Where does the point lie in the noise space according to image space. 
	            
	            float n = (float) noise.noise(xx, yy, 3f); // Noise values from Perlin's noise.
	            
	            // Since noise value returned is -1 to 1, we need it to be between 0 and Tile.values().length * total Spawn Rate
	            int generation = (int) (((n + 1) * totalSpawnChance) / 2f); 
	            // sets the tile
	            boolean flag = true;
	            //System.out.println(generation);
	            for(int i = 0; i < Tile.values().length && flag; i++){
	            	if(generation <= Tile.values()[i].getSpawnRate()){
	            		map[x][y] = Tile.values()[i].getIntRepresentation();
	            		flag = false;
	            	}
	            	generation -= Tile.values()[i].getSpawnRate();
	            	
	            }
	            
	            // hacky code
	            if(map[x][y] == -1) map[x][y] = Tile.values()[Tile.values().length-1].getIntRepresentation();
	            
	            
	         }
		}
	}
	
	// primarily for debugging, but returns the value of map at x,y
	public int get(int x, int y){
		if(x<0 || x>= xLength || y<0 || y >= yLength) throw new RuntimeException("crap");
		return map[x][y];
	}
	
	public int get(Point p){
		if(p.x<0 || p.x>= xLength || p.y<0 || p.y >= yLength) throw new RuntimeException("crap");
		return map[p.x][p.y];
	}
	
	public int getXLength(){
		return xLength;
	}
	
	public int getYLength(){
		return yLength;
	}
	
	// for point objects because comparing with doubles can be... problematic
	private class Node{
		private int x, y;
		private Node(int x, int y){
			this.x = x;
			this.y = y;
		}
		int getX(){
			return x;
		}
		int getY(){
			return y;
		}
		
	}
	
	
	@Override
	public String toString(){
		String result = "";
		for(int i = 0; i < 100; i++){
			for(int j = 0; j < 100; j++){
				result += "" + Tile.values()[map[i][j]].getCharRepresentation();
			}
			result += "\n";
		}
		return result;
	}

}
