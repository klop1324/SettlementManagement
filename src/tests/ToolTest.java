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
		Tool pickaxe2 = new Tool(iron, coal);
		assertEquals(pickaxe2.getType(), ToolType.PICKAXE);
		assertEquals(pickaxe.getType(), ToolType.PICKAXE);
		game.createTool(coal, iron, agent);
		assertTrue(agent.hasTool());
		assertTrue(agent.hasPickAxe());
		assertFalse(soldier.hasTool());
		game.createTool(gold, iron, soldier);
		assertTrue(soldier.hasTool());
		assertTrue(soldier.hasArmor());
		
	}
	
	public void spearTest(){
		Game game = new Game();
		SoldierAgent agent = new SoldierAgent(null);
		Resource copper = new Resource(30, null, ResourceType.COPPER);
		Resource iron = new Resource(30, null, ResourceType.IRON);
		game.addResource(iron);
		game.addResource(copper);
		game.addAgents(agent);
		game.createTool(iron, copper, agent);
		assertTrue(agent.hasTool());
		assertEquals(agent.getAttackPoints(), 5);
		agent.attack();
		assertEquals(agent.getAttackPoints(), 15);
		assertTrue(agent.hasSpear());
	}
}
