package tests;

import static org.junit.Assert.*;

import java.awt.Point;

import model.agents.*;

import org.junit.Test;

public class AbstractAgentTest {

	@Test
	public void testHasWeldingGun() {
		BuilderAgent a = new BuilderAgent(new Point(0,0));
		assertFalse(a.hasWeldingGun());
	}

	@Test
	public void testSendCommandAndRecieveCommand() {
		BuilderAgent a = new BuilderAgent(new Point(0,0));
		a.sendCommand(new AgentCommandWithDestination(AgentCommand.COLLECT_COAL, new Point(1,1)));
	}


}
