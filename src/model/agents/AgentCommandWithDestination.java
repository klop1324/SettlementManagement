package model.agents;

import java.awt.Point;
import java.io.Serializable;

public class AgentCommandWithDestination implements Serializable{
	
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