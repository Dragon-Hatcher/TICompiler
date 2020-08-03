package parser.nodes;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import parser.exceptions.MismatchedTypeException;
import parser.exceptions.UseOfUnknownVariableException;

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
	
	@Override
	public void checkFunctionNameAndLength() throws Exception {
		((ParseNode)toAssign).checkFunctionNameAndLength();
	}

	@Override
	public void setSubParseNodeVariables(Map<String, String> superVariables) throws Exception {
		this.variables = superVariables;
		((ParseNode)toAssign).setSubParseNodeVariables(superVariables);
	}

	@Override
	public void setFunctions(Map<String, FunctionDeclerationPN> functions) {
		this.functions = functions;
		((ParseNode)toAssign).setFunctions(functions);
	}

	@Override
	public void checkTypes(String returnType) throws Exception {
		if(!variables.containsKey(varName)) {
			System.out.println(variables);
			throw new UseOfUnknownVariableException("Use of unknown variable " + varName + " on " + lcText() + ".");
		}
		
		String varType = variables.get(varName);
		if(!varType.equals(toAssign.type())) {
			throw new MismatchedTypeException("Variable " + varName + " is of type " + varType + " but you attempted to assign it to type " + toAssign.type() + " on " + lcText() + ".");
		}
		
		((ParseNode)toAssign).checkTypes(returnType);
	}

}
