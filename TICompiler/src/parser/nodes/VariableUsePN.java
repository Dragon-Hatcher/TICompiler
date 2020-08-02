package parser.nodes;

import java.util.Map;

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

}
