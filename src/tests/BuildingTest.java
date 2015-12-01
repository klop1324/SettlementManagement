package tests;

import static org.junit.Assert.*;

import java.awt.Point;

import org.junit.Test;

import model.Game;
import model.agents.WorkerAgent;
import model.buildings.Armory;
import model.buildings.BuildingType;
import model.buildings.ChargingStation;
import model.buildings.HomeDepot;
import model.buildings.JunkYard;
import model.resources.Resource;
import model.resources.ResourceType;

public class BuildingTest {
	WorkerAgent agent = new WorkerAgent(new Point(1,2));
	@Test
	public void newBuildingTest(){
		Game game = new Game(); // turn the constructor back into private i guess
		HomeDepot home = new HomeDepot(100, new Point(12, 3));
		Resource coal = new Resource(10, null, ResourceType.COAL);
		Resource gold = new Resource(10, new Point(12, 3), ResourceType.GOLD);
		Resource iron = new Resource(10, new Point(12, 7), ResourceType.IRON);
		game.addBuilding(home);
		home.agentAddCapacity(coal.getType(), 100);
		home.agentAddCapacity(gold.getType(), 100);
		home.agentAddCapacity(iron.getType(), 100);
		game.addBuildingInProcess(new Armory(10, new Point(10, 4)));
//		game.createBuilding(new Point(12, 3), BuildingType.ARMORY);
//		System.out.println(game.getBuildingsInProcess());
		
		
	}
	@Test
	public void junkYardTest() {
		JunkYard junkyard = new JunkYard(100, new Point(1,2));
		Resource oil = new Resource(10, new Point(12, 3), ResourceType.OIL);
		Resource iron = new Resource(10, new Point(12, 3), ResourceType.IRON);
		assertFalse(junkyard.getResources().containsKey(oil.getType()));
		assertTrue(junkyard.getResources().containsKey(iron.getType()));
		junkyard.agentAddCapacity(iron.getType(), 20);
		assertEquals((int)junkyard.getResources().get(iron.getType()), 20);
		junkyard.agentRemoveCapacity(iron.getType(), 10);
		assertEquals((int)junkyard.getResources().get(iron.getType()), 10);
		System.out.println(junkyard.getResources());

	}
	
	@Test
	public void chargingStationTest(){
		Point point = new Point(13, 7);
		ChargingStation charge = new ChargingStation(90, point);
		Resource electricSlide = new Resource(10, new Point(13,7), ResourceType.ELECTRICITY);
		assertEquals((int) charge.getResources().get(electricSlide.getType()), 0);
		charge.agentAddCapacity(electricSlide.getType(), 9);
		assertEquals((int) charge.getResources().get(electricSlide.getType()), 9);
		charge.agentRemoveCapacity(electricSlide.getType(), 3);
		assertEquals((int) charge.getResources().get(electricSlide.getType()), 6);
		charge.agentRemoveCapacity(electricSlide.getType(), 10000);
		assertEquals((int) charge.getResources().get(electricSlide.getType()), 6);
		charge.agentAddCapacity(electricSlide.getType(), 10000);
		assertEquals((int) charge.getResources().get(electricSlide.getType()), 6);
		charge.passiveCheck();
		System.out.println(ResourceType.COAL.name());
	}
	
	@Test
	public void locationCheck(){
		Resource iron = new Resource(10, new Point(4, 30), ResourceType.IRON);
		JunkYard junkyard = new JunkYard(40, new Point(30, 20));
		assertEquals(iron.getLocation(), new Point(4, 30));
		assertEquals(junkyard.getLocation(), new Point(30, 20));
	}
	
	@Test
	public void agentInteraction(){
		
	}
	

}
