package tests;

import static org.junit.Assert.*;

import java.awt.Point;

import org.junit.Test;

import model.agents.WorkerAgent;
import model.buildings.ChargingStation;
import model.buildings.JunkYard;
import model.resources.Resource;
import model.resources.ResourceType;

public class BuildingTest {
	WorkerAgent agent = new WorkerAgent(new Point(1,2));

	@Test
	public void junkYardTest() {
		JunkYard junkyard = new JunkYard("Junk yard", 100, new Point(1,2));
		Resource oil = new Resource(10, new Point(12, 3), ResourceType.OIL);
		Resource iron = new Resource(10, new Point(12, 3), ResourceType.IRON);
		assertFalse(junkyard.getResources().containsKey(oil));
		assertTrue(junkyard.getResources().containsKey(iron.getClass()));
		junkyard.agentAddCapacity(iron.getClass(), 20, agent);
		assertEquals((int)junkyard.getResources().get(iron.getClass()), 20);
		junkyard.agentRemoveCapacity(iron.getClass(), 10);
		assertEquals((int)junkyard.getResources().get(iron.getClass()), 10);

	}
	
	@Test
	public void chargingStationTest(){
		Point point = new Point(13, 7);
		System.out.println(agent.getDestination());
		System.out.println(agent.getPosition());
		ChargingStation charge = new ChargingStation(null, 90, point);
		Resource electricSlide = new Resource(10, new Point(13,7), ResourceType.ELECTRICITY);
		assertEquals((int) charge.getResources().get(electricSlide.getClass()), 0);
		charge.agentAddCapacity(electricSlide.getClass(), 9, agent);
		assertEquals((int) charge.getResources().get(electricSlide.getClass()), 9);
		charge.agentRemoveCapacity(electricSlide.getClass(), 3);
		assertEquals((int) charge.getResources().get(electricSlide.getClass()), 6);
		charge.agentRemoveCapacity(electricSlide.getClass(), 10000);
		assertEquals((int) charge.getResources().get(electricSlide.getClass()), 6);
		charge.agentAddCapacity(electricSlide.getClass(), 10000, agent);
		assertEquals((int) charge.getResources().get(electricSlide.getClass()), 6);
		charge.passiveCheck();
		System.out.println(ResourceType.COAL.name());
	}
	
	@Test
	public void locationCheck(){
		Resource iron = new Resource(10, new Point(4, 30), ResourceType.IRON);
		JunkYard junkyard = new JunkYard("Junk Yard", 40, new Point(30, 20));
		assertEquals(iron.getLocation(), new Point(4, 30));
		assertEquals(junkyard.getLocation(), new Point(30, 20));
	}
	
	

}
