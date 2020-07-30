package parser.nodes;

import java.util.ArrayList;

public class AssignmentPN extends ParseNode implements Instruction {

	String varName = "";
	Evaluable toAssign = null;
	
	public AssignmentPN(String varName, Evaluable toAssign) {
		this.varName = varName;
		this.toAssign = toAssign;
	}

	@Override
	public String toString() {
		ArrayList<String> assignStrings = new ArrayList<String>();
		
		String[] aString = toAssign.toString().split("\n");
		for(String i : aString) {
			assignStrings.add("  " + i);
		}
		
		return "(Assign:\n" + "  (Name: " + varName + ")\n" + String.join("\n", assignStrings)  + "\n)\n";
	}

}
