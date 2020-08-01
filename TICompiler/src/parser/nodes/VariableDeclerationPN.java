 package parser.nodes;

import java.util.Set;

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
	
	public boolean willReturn() {
		return false;
	}

	public boolean hasIllegalBreak() {
		return false;
	}

	public String hasIllegalDeclerationType(Set<String> types) {
		return types.contains(type) ? null : type;
	}

}
