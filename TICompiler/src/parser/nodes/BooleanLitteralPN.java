package parser.nodes;

import java.util.Map;

public class BooleanLitteralPN extends ParseNode implements Evaluable {
	String trueOrFalse = "";
	
	public BooleanLitteralPN(String trueOrFalse) {
		this.trueOrFalse = trueOrFalse;
	}
	
	@Override
	public String toString() {
		return "("+trueOrFalse+")";
	}

	public String type() {
		return "bool";
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
