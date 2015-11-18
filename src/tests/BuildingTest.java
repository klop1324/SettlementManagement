package tests;

import static org.junit.Assert.*;

import java.awt.Point;

import org.junit.Test;

import model.buildings.ChargingStation;
import model.buildings.JunkYard;
import model.resources.Electricity;
import model.resources.Iron;
import model.resources.Oil;

public class BuildingTest {

	@Test
	public void junkYardTest() {
		Point test = new Point(12,3);
		JunkYard junkyard = new JunkYard("Junk yard", 100, null, test);
		Oil oil = new Oil(10, test);
		Iron iron = new Iron(10, test);
		assertFalse(junkyard.getResources().containsKey(oil));
		assertTrue(junkyard.getResources().containsKey(iron.getClass()));
		junkyard.agentAddCapacity(iron.getClass(), 20);
		assertEquals((int)junkyard.getResources().get(iron.getClass()), 20);
		junkyard.agentRemoveCapacity(iron.getClass(), 10);
		assertEquals((int)junkyard.getResources().get(iron.getClass()), 10);

	}
	
	@Test
	public void chargingStationTest(){
		Point point = new Point(12, 89);
		ChargingStation charge = new ChargingStation(null, 90, point);
		Electricity electricSlide = new Electricity(10, point);
		assertEquals((int) charge.getResources().get(electricSlide.getClass()), 0);
		charge.agentAddCapacity(electricSlide.getClass(), 9);
		assertEquals((int) charge.getResources().get(electricSlide.getClass()), 9);
		charge.agentRemoveCapacity(electricSlide.getClass(), 3);
		assertEquals((int) charge.getResources().get(electricSlide.getClass()), 6);
		charge.agentRemoveCapacity(electricSlide.getClass(), 10000);
		assertEquals((int) charge.getResources().get(electricSlide.getClass()), 6);
		charge.agentAddCapacity(electricSlide.getClass(), 10000);
		assertEquals((int) charge.getResources().get(electricSlide.getClass()), 6);
		charge.passiveCheck();
	}
	
	

}
