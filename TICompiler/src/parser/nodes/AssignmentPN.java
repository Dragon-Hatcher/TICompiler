package parser.nodes;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public class AssignmentPN extends ParseNode implements Instruction, ContainsEvaluable {

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
	
	public FunctionCallPN checkFunctionNameAndLength(Map<String, FunctionDeclerationPN> functions) {
		if(toAssign instanceof ContainsEvaluable) {
			return ((ContainsEvaluable)toAssign).checkFunctionNameAndLength(functions);
		}
		return null;
	}

	public void setSubParseNodeVariables(Map<String, String> superVariables) throws Exception {
		this.variables = superVariables;
		((ParseNode)toAssign).setSubParseNodeVariables(superVariables);
	}

	public void setFunctions(Map<String, FunctionDeclerationPN> functions) {
		this.functions = functions;
		((ParseNode)toAssign).setFunctions(functions);
	}

}
