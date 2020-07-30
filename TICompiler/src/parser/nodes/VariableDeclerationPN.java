 package parser.nodes;

public class VariableDeclerationPN extends ParseNode implements Instruction {
	public String type;
	public String name;
	
	public VariableDeclerationPN(String type, String name) {
		this.type = type;
		this.name = name;
	}

	@Override
	public String toString() {
		return "(Var Decleration:\n  (Type: " + type + ")\n  (Name: " + name + ")\n)\n";
	}
}
