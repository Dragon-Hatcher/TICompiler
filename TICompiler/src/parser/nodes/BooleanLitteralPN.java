package parser.nodes;

import java.util.Map;

public class BooleanLitteralPN implements Evaluable {
	String trueOrFalse = "";
	
	public BooleanLitteralPN(String trueOrFalse) {
		this.trueOrFalse = trueOrFalse;
	}
	
	@Override
	public String toString() {
		return "("+trueOrFalse+")";
	}

	@Override
	public FunctionCallPN checkFunctionNameAndLength(Map<String, FunctionDeclerationPN> functions) {
		return null;
	}
	
	
}
