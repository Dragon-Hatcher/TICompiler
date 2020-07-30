package parser.nodes;

public class AssignmentPN extends ParseNode implements Instruction {

	String varName = "";
	Evaluable toAssign = null;
	
	public AssignmentPN(String varName, Evaluable toAssign) {
		this.varName = varName;
		this.toAssign = toAssign;
	}

	@Override
	public String toString() {
		return "(Assign:\n" + "  (Name: " + varName + ")\n  " + toAssign.toString() + "\n)\n";
	}

}
