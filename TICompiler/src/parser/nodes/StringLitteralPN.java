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

}
