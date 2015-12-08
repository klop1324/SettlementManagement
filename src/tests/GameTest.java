package tests;

import static org.junit.Assert.*;

import java.awt.Point;

import org.junit.Test;

import model.Game;
import model.buildings.VictoryMonument;
import model.resources.ResourceType;

public class GameTest {

	@Test
	public void canBuildTest() {
		Game g = Game.getInstance();
		assertFalse(g.canBuildBuilding(new VictoryMonument(new Point(5,5))));
		g.addResources(ResourceType.COAL, 10000);
		g.addResources(ResourceType.COPPER, 10000);
		g.addResources(ResourceType.ELECTRICITY, 10000);
		g.addResources(ResourceType.GOLD, 10000);
		g.addResources(ResourceType.IRON, 10000);
		g.addResources(ResourceType.OIL, 10000);
		assertTrue(g.canBuildBuilding(new VictoryMonument(new Point(5,5))));
		
	}

}
