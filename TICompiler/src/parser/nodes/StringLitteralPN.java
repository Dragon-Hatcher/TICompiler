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

	@Override
	public String type() {
		return "String";
	}

}
