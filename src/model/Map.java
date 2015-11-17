package model;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

public class Map {
	
	
	int[][] map;
	int xLength, yLength;
	
	// debugging code
	/*
	public static void main(String args[]){
		Map map = new Map(100, 100);
		for(int i = 0; i < 100; i++){
			for(int j = 0; j < 100; j++){
				System.out.print(map.get(i, j));
				
			}
			System.out.println();
		}
	}
	*/

	public Map(int i, int j) {
		if(i < 2 || j < 2) throw new RuntimeException("I got passed something less than 2! Here's what was passed to me i = " +i + " j = " +j);
		this.map = new int[i][j];
		this.xLength = i;
		this.yLength = j;
		this.generate();
	}
	// makes the map
	private void generate(){

		ArrayList<Node> points = new ArrayList<Node>();
		
		Random random = new Random();
		
		int numOfSites = 0;
		while(numOfSites < Math.sqrt(Math.sqrt(xLength * yLength))){
			 numOfSites = xLength * yLength * random.nextInt(xLength);
		}
		
		//makes sure there is less than log(Xlength) sites, so that we don't get, for example, 50 sites in a 2x2 array
		while(numOfSites > Math.log(xLength)){
			numOfSites /= 2;
		}
		
		// creates all the points
		while(points.size() < numOfSites){
			Node p = new Node(random.nextInt(xLength), random.nextInt(yLength));
			boolean flag = true;
			for(int j = 0; j < points.size() && flag; j++){
				if(points.get(j).getX() == p.getX() && points.get(j).getY() == p.getY())flag = false;
			}
			// adds to the list of sites, and sets that site to a random tile type
			if(flag){
				points.add(p);
				map[p.getX()][p.getY()] = random.nextInt(Tile.values().length);
			}
		}
		
		// does the actual generation, uses the closest site to set the current tile to that site
		for(int i = 0; i < xLength; i++){
			for(int j = 0; j < yLength; j++){
				Node temp = getClosestPoint(i, j, points);
				map[i][j] = map[temp.getX()][temp.getY()];
				
			}
		}
		
		// debugging! yay!
		/*
		for(int i = 0; i < points.size();i++){
			int x = points.get(i).getX();
			int y = points.get(i).getY();;
			System.out.println("Point " + i + " = (" + x + "," + y + ") and the value is " + map[x][y]);
		}
		*/
	}
	
	// private helper method to determine what is the closest node to the point x, y
	private Node getClosestPoint(int x, int y, ArrayList<Node> list){
		Node n = null;
		double min = Double.POSITIVE_INFINITY;
		for(int i = 0; i < list.size(); i++){
			Node temp = list.get(i);
			int a = temp.getX() - x;
			int b = temp.getY() - y;
			double result = Math.sqrt(Math.pow(a, 2) * Math.pow(b, 2));
			if(result < min){
				min = result;
				n = temp;
			}
		}
		if(n == null) throw new RuntimeException("Was passed in a 0 length list! How did this happen!");
		return n;
		
	}
	
	public void paintComponent(Graphics g){
		for(int i = 0; i < xLength; i++){
			for(int j = 0; j < yLength; j++){
				//TODO paint all the tiles
			}
		}
	}
	
	// primarily for debugging, but returns the value of map at x,y
	public int get(int x, int y){
		if(x<0 || x>= xLength || y<0 || y >= yLength) throw new RuntimeException("crap");
		return map[x][y];
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

}
