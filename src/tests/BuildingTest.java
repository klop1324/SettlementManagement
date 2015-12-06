package tests;

import static org.junit.Assert.*;

import java.awt.Point;

import org.junit.Test;

import model.Game;
import model.agents.BuilderAgent;
import model.agents.WorkerAgent;
import model.buildings.*;
import model.resources.Resource;
import model.resources.ResourceType;

public class BuildingTest {
	WorkerAgent agent = new WorkerAgent(new Point(1,2));
	@Test
	public void newBuildingTest(){
		Game game = new Game(); // turn the constructor back into private when done
		BuilderAgent bobTheBuilder = new BuilderAgent(null);
		AbstractBuilding home = new HomeDepot(new Point(5,5));
		Resource coal = new Resource(10, null, ResourceType.COAL);
		Resource gold = new Resource(10, new Point(12, 3), ResourceType.GOLD);
		Resource iron = new Resource(10, new Point(12, 7), ResourceType.IRON);
		game.addBuilding(home);
		home.agentAddCapacity(coal.getType(), 100);
		home.agentAddCapacity(gold.getType(), 100);
		home.agentAddCapacity(iron.getType(), 100);
		game.addBuildingInProcess(new Armory(new Point(5,5)));
		game.createBuilding(new Armory(new Point(12, 3)));
		bobTheBuilder.incrementCompletionAmount(game.getBuildingsInProcess().get(0));
		assertEquals(game.getBuildingsInProcess().get(0).getCompletionAmount(), 3);
		game.createBuilding(new Point(12, 3), BuildingType.CHARGINGSTATION);
		System.out.println(game.getBuildingsInProcess());
		
		
	}
	@Test
	public void junkYardTest() {
		JunkYard junkyard = new JunkYard(new Point(5,5));
		Resource oil = new Resource(10, new Point(12, 3), ResourceType.OIL);
		Resource iron = new Resource(10, new Point(12, 3), ResourceType.IRON);0
		assertFalse(junkyard.getResources().contains(oil.getType()));
		assertTrue(junkyard.getResources().contains(iron.getType()));
		junkyard.agentAddCapacity(iron.getType(), 20);
		junkyard.agentRemoveCapacity(iron.getType(), 10);
		System.out.println(junkyard.getResources());

	}
	
	@Test
	public void chargingStationTest(){
		Point point = new Point(13, 7);
		ChargingStation charge = new ChargingStation(new Point(5,5));
		Resource electricSlide = new Resource(10, new Point(13,7), ResourceType.ELECTRICITY);
		assertEquals(charge.getResourceAmount(ResourceType.ELECTRICITY), 0);
		charge.agentAddCapacity(electricSlide.getType(), 9);
		assertEquals((int) charge.getResourceAmount(electricSlide.getType()), 9);
		charge.agentRemoveCapacity(electricSlide.getType(), 3);
		assertEquals((int) charge.getResourceAmount(electricSlide.getType()), 6);
		charge.agentAddCapacity(electricSlide.getType(), 10000);
		assertEquals((int) charge.getResourceAmount(electricSlide.getType()), 10006);
		assertFalse(charge.isPassiveProvider());
	}
	
	@Test
	public void locationCheck(){
		Resource iron = new Resource(10, new Point(4, 30), ResourceType.IRON);
		JunkYard junkyard = new JunkYard(new Point(30,20));
		assertEquals(iron.getLocation(), new Point(4, 30));
		assertEquals(junkyard.getLocation(), new Point(30, 20));
	}
	
	@Test
	public void agentInteraction(){
		
	}
	

}
