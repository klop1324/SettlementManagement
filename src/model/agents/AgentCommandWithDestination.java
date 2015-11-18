package model.agents;

import java.awt.Point;

public class AgentCommandWithDestination {
	
	private AgentCommand command;
	private Point commandDestination;
	
	public AgentCommandWithDestination(AgentCommand command, Point commandDestination) {
		this.command = command;
		this.commandDestination = commandDestination;
	}
	
	public AgentCommand getAgentCommand() {
		return command;
	}
	
	public Point getCommandDestination() {
		return commandDestination;
	}
}