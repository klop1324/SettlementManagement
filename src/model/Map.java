package model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Random;

public class Map extends Observable{
	
	
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
		System.out.println(totalSpawnChance);
		for (int y = 0; y < this.yLength; y++) {
	         for (int x = 0; x < this.xLength; x++) {
	            
	            float xx = (float) x / this.xLength * size; // Where does the point lie in the noise space according to image space. 
	            float yy = (float) y / this.yLength * size; // Where does the point lie in the noise space according to image space. 
	            
	            float n = (float) noise.noise(xx, yy, 1f); // Noise values from Perlin's noise.
	            
	            // Since noise value returned is -1 to 1, we need it to be between 0 and Tile.values().length * total Spawn Rate
	            double generation = (double) (((n + 1) * (Tile.values().length-1) * totalSpawnChance) / 2f); 

	            
	            // sets the tile
	            for(Tile t: Tile.values()){
	            	if(generation < t.getSpawnRate()) map[x][y] = t.getIntRepresentation();
	            	else{
	            		generation -= t.getSpawnRate();
	            	}
	            }
	            
	            
	            
	            
	         }
		}
	}
	
	// primarily for debugging, but returns the value of map at x,y
	public int get(int x, int y){
		if(x<0 || x>= xLength || y<0 || y >= yLength) throw new RuntimeException("crap");
		return map[x][y];
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
				result += "" + map[i][j];
			}
			result += "\n";
		}
		return result;
	}

}
