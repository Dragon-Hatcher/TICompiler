package parser.nodes;

import java.util.Map;

public class NumLitteralPN extends ParseNode implements Evaluable {

	String num = "";
	
	public NumLitteralPN (String num) {
		this.num = num;
	}
	
	@Override
	public String toString() {
		return "("+num+")";
	}

	public FunctionCallPN checkFunctionNameAndLength(Map<String, FunctionDeclerationPN> functions) {
		return null;
	}

	public String type() {
		return num.contains(".") ? "float" : "int";
	}

	public void setSubParseNodeVariables(Map<String, String> superVariables) throws Exception {
		this.variables = superVariables;
	}
	
	public void setFunctions(Map<String, FunctionDeclerationPN> functions) {
		this.functions = functions;
	}

}
