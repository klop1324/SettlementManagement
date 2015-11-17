package tests;

import static org.junit.Assert.*;
import model.*;

import org.junit.Test;

public class MapTest {

	@Test
	public void MapTest() {
		
	}
	
	@Test
	public void ToStringTest() {
		Map map = new Map(100, 100);
		assertEquals("", map.toString());
	}

}
