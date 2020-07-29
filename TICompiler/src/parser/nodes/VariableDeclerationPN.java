package parser.nodes;

import langaugeConstructs.Types;

public class VariableDeclerationPN extends ParseNode implements Instruction {
	public Types type;
	public String name;

	@Override
	public String toString() {
		return "(Var Decleration:\n  (Type: " + type.toString() + ")\n  (Name: " + name + ")\n)\n";
	}
}
