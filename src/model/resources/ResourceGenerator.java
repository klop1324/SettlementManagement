package model.resources;

import java.awt.Point;
import java.util.ArrayList;

import model.GlobalSettings;
import model.Map;
import model.PerlinNoise;
import model.Tile;

public class ResourceGenerator {
	
	Map map;
	int xLength,yLength;
	ArrayList<Resource> mapResources = new ArrayList<Resource>();
	
	public ResourceGenerator(int x, int y, Map m){
		this.map = m;
	}
	
	public ArrayList<Resource> generateResources(){
		generate();
		return mapResources;
	}
	
	private void generate() {
		// magic numbers, i know.
		int size = 32;
		PerlinNoise noise = new PerlinNoise(size, size);
		int generationArray[][] = new int[xLength][yLength];

		for (int y = 0; y < yLength; y++) {
			for (int x = 0; x < xLength; x++) {

				float xx = (float) x / generationArray.length * size; 
				float yy = (float) y / generationArray[0].length * size; 

				float n = (float) noise.noise(xx, yy, 1.7f); 
				int generation = (int) (((n + 1) * (Tile.values().length - 1)));
				if (generation >= (Tile.values().length)) {
					generationArray[x][y] = -1;
				}
			}
		}

		ArrayList<ResourceType> unplacedResources = new ArrayList<ResourceType>();

		// makes a list of unplaced resources
		for (ResourceType resource : ResourceType.values()) {
			if (resource.isSpawnable())
				unplacedResources.add(resource);
		}
		java.util.Collections.shuffle(unplacedResources);

		ArrayList<ResourceType> placedResources = new ArrayList<ResourceType>();

		// places resources
		for (int i = 0; i < generationArray.length; i++) {
			for (int j = 0; j < generationArray[0].length; j++) {
				// if there is a node i should be generating on
				if (generationArray[i][j] == -1) {
					// place call generation helper to generate all the
					// resources ONLY ON TILES THAT ARE PASSABLE
					if (!unplacedResources.isEmpty()) {
						generationArray = generationHelper(generationArray, i, j, unplacedResources.get(0));
						placedResources.add(unplacedResources.get(0));
						unplacedResources.remove(0);
					} else {
						generationArray = generationHelper(generationArray, i, j,
								placedResources.get((int) (Math.random() * placedResources.size())));
					}

				}
			}
		}

	}
	// recursive, going through the blob formed by -1's in the array, adding them to the mapResources
	private int[][] generationHelper(int array[][], int x, int y, ResourceType resource){
		if(array[x][y] == -1){
			if(Tile.values()[map.get(x, y)].isPassible()){
				mapResources.add(new Resource(((int) ((Math.random()+5.0) * 1000.0 * GlobalSettings.MAP_RICHNESS)), new Point(x,y), resource));
			}
			// base case
			array[x][y] = 0;

			// recursion
			if (x + 1 < array[0].length)
				if (array[x + 1][y] != 0)
					array = generationHelper(array, x + 1, y, resource);
			if (y + 1 < array.length)
				if (array[x][y + 1] != 0)
					array = generationHelper(array, x, y + 1, resource);
			if (x - 1 >= 0)
				if (array[x - 1][y] != 0)
					array = generationHelper(array, x - 1, y, resource);
			if (y - 1 >= 0)
				if (array[x][y - 1] != 0)
					array = generationHelper(array, x, y - 1, resource);
		}
		return array;
	}
}
