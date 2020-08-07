package intermediateLanguageGenerator.nodes;

public class CreateVariableILPN extends ILParseNode {

	public String name;
	public String type;
	
	public CreateVariableILPN(String name, String type) {
		this.name = name;
		this.type = type;
	}

	@Override
	public String toString() {
		return "Create variable " + name + " with type " + type;
	}

}
