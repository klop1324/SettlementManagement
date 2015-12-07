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

public class CreateAgentTest {
	@Test
	public void createAgent(){
		Game game = new Game();
		game.getBuildings().get(2).addResource(ResourceType.ELECTRICITY, 1);
		game.getBuildings().get(1).addResource(ResourceType.IRON, 1);
		assertFalse(game.canCreateAgent(new BuilderAgent(null)));
	}
}
