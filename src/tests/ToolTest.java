package tests;

import static org.junit.Assert.*;

import java.awt.Point;

import org.junit.Test;

import model.Game;
import model.agents.SoldierAgent;
import model.agents.WorkerAgent;
import model.resources.Resource;
import model.resources.ResourceType;
import model.tools.Tool;
import model.tools.ToolType;

public class ToolTest {
	
	@Test
	public void createToolTest(){
		Game game = new Game();
		WorkerAgent agent = new WorkerAgent(null);
		SoldierAgent soldier = new SoldierAgent(null);
		Resource iron = new Resource(10, new Point(2,4), ResourceType.IRON);
		Resource coal = new Resource(10, new Point(2,4), ResourceType.COAL);
		Resource gold = new Resource(10, new Point(2,4), ResourceType.GOLD);
		Tool pickaxe = new Tool(coal, iron);
		assertEquals(pickaxe.getType(), ToolType.PICKAXE);
		game.createTool(coal, iron, agent);
		assertTrue(agent.hasTool());
		assertTrue(agent.hasPickAxe());
		assertFalse(soldier.hasTool());
		game.createTool(gold, iron, soldier);
		assertTrue(soldier.hasTool());
		assertTrue(soldier.hasArmor());
		
	}
}
