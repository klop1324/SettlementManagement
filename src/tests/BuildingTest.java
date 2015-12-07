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
	public void junkYardTest() {
		JunkYard junkyard = new JunkYard(new Point(5,5));
		Resource oil = new Resource(10, new Point(12, 3), ResourceType.OIL);
		Resource iron = new Resource(10, new Point(12, 3), ResourceType.IRON);
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
		assertTrue(charge.isPassiveProvider());
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
