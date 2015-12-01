package tests;

import static org.junit.Assert.*;

import java.awt.Point;

import org.junit.Test;

import model.resources.Resource;
import model.resources.ResourceType;
import model.tools.Tool;
import model.tools.ToolType;

public class ToolTest {
	
	@Test
	public void createToolTest(){
		Resource iron = new Resource(10, new Point(2,4), ResourceType.IRON);
		Resource coal = new Resource(10, new Point(2,4), ResourceType.COAL);
		Tool pickaxe = new Tool(coal, iron);
		assertEquals(pickaxe.getType(), ToolType.PICKAXE);
	}
}
