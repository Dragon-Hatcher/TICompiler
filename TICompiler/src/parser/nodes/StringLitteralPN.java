package parser.nodes;

import java.util.Map;

public class StringLitteralPN extends ParseNode implements Evaluable {

	String str = "";
	
	public StringLitteralPN (String str) {
		this.str = str;
	}
	
	@Override
	public String toString() {
		return "(\"" + str + "\")";
	}

	public FunctionCallPN checkFunctionNameAndLength(Map<String, FunctionDeclerationPN> functions) {
		return null;
	}

	public String type() {
		return "String";
	}

	public void setSubParseNodeVariables(Map<String, String> superVariables) throws Exception {
		this.variables = superVariables;
	}

	public void setFunctions(Map<String, FunctionDeclerationPN> functions) {
		this.functions = functions;
	}
	
	public void checkTypes(String returnType) throws Exception {
	}

}
