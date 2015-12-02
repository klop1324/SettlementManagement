package model.tools;

import model.resources.Resource;
import model.resources.ResourceType;

public class Tool {
	private ToolType typeTool;
	// When creating tool put in two types of resources.
	// Will output ToolType
	public Tool(Resource res1, Resource res2){
		ResourceType type1 = res1.getType();
		ResourceType type2 = res2.getType();

		if ((type1.equals(ResourceType.IRON) || type1.equals(ResourceType.COAL)) && 
				(type2.equals(ResourceType.COAL) || type2.equals(ResourceType.IRON)) &&
				!type1.equals(type2)){
			typeTool = ToolType.PICKAXE;
		}
		if ((type1.equals(ResourceType.GOLD) ||type1.equals(ResourceType.IRON)) &&
				(type2.equals(ResourceType.IRON) || type2.equals(ResourceType.GOLD)) &&
				!type1.equals(type2)){
			typeTool = ToolType.ARMOR;
		}
		if ((type1.equals(ResourceType.COPPER) ||type1.equals(ResourceType.IRON)) &&
				(type2.equals(ResourceType.IRON) || type2.equals(ResourceType.COPPER)) &&
				!type1.equals(type2)){
			typeTool = ToolType.SPEAR;
		}
		if((type1.equals(ResourceType.COAL) ||type1.equals(ResourceType.COPPER)) &&
				(type2.equals(ResourceType.COPPER) || type2.equals(ResourceType.COAL)) &&
				!type1.equals(type2)){ 
			typeTool = ToolType.WELDINGGUN;
		}
	}

	public ToolType getType(){
		return typeTool;
	}

}
