package parser.nodes;

import java.util.Map;
import java.util.Set;

public class BreakPN extends ParseNode implements Instruction {

	@Override
	public String toString() {
		return "(Break)";
	}

	public FunctionCallPN checkFunctionNameAndLength(Map<String, FunctionDeclerationPN> functions) {
		return null;
	}
	
	public void setSubParseNodeVariables(Map<String, String> superVariables) throws Exception {
		this.variables = superVariables;
	}

	public void setFunctions(Map<String, FunctionDeclerationPN> functions) {
		this.functions = functions;
	}
}
