package parser.nodes;

import java.util.Map;

import parser.exceptions.UseOfUnknownVariableException;

public class VariableUsePN extends ParseNode implements Evaluable {

	String name = "";
	
	public VariableUsePN(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "(var: " + name + ")";
	}

	public FunctionCallPN checkFunctionNameAndLength(Map<String, FunctionDeclerationPN> functions) {
		return null;
	}

	public void setSubParseNodeVariables(Map<String, String> superVariables) throws Exception {
		this.variables = superVariables;
	}

	public String type() throws Exception {
		if(variables.containsKey(name)) {
			return variables.get(name);
		} else {
			throw new UseOfUnknownVariableException("Use of unknown variable " + name + ".");
		}
	}
	
	public void setFunctions(Map<String, FunctionDeclerationPN> functions) {
		this.functions = functions;
	}
	
	public void checkTypes(String returnType) throws Exception {
	}

}
