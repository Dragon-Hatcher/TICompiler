package parser.nodes;

import java.util.ArrayList;
import java.util.Set;

public class AssignmentPN extends ParseNode implements Instruction {

	String varName = "";
	String assignOp = "";
	Evaluable toAssign = null;
	
	public AssignmentPN(String varName, String assignOp, Evaluable toAssign) {
		this.varName = varName;
		this.assignOp = assignOp;
		this.toAssign = toAssign;
	}

	@Override
	public String toString() {
		ArrayList<String> assignStrings = new ArrayList<String>();
		
		String[] aString = toAssign.toString().split("\n");
		for(String i : aString) {
			assignStrings.add("  " + i);
		}
		
		return "(Assign:\n" + "  (Name: " + varName + ")\n  (Type: " + assignOp + ")\n" + String.join("\n", assignStrings)  + "\n)\n";
	}
	
	public boolean willReturn() {
		return false;
	}

	public boolean hasIllegalBreak() {
		return false;
	}

	public String hasIllegalDeclerationType(Set<String> types) {
		return null;
	}

}
