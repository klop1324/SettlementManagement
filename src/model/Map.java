package model;

<<<<<<< HEAD
=======
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
>>>>>>> 10bd02e14328cb5c193eddb53c5063e71a91e0cc
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
		this.generate();
		
		for(int k = 0; k < xLength; k++){
			for(int t = 0; t < yLength; t++){
				isVisible[k][t] = false;
			}
		}
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
	            
	            float n = (float) noise.noise(xx, yy, 1.6f); // Noise values from Perlin's noise.
	            int generation = (int) ((n + 1) * Tile.values().length-1
	            		* totalSpawnChance / 2f); // Since noise value returned is -1 to 1, we need it to be between 0 and Tile.values().length
	            // uses visor 
	            int divisor = 0;;
	            for(int i = 0; i < Tile.values().length; i++){
	            	if(generation < Tile.values()[i].getSpawnRate() + divisor){
	            		map[x][y] = i;
	            	}
	            	divisor += Tile.values()[i].getSpawnRate();
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
