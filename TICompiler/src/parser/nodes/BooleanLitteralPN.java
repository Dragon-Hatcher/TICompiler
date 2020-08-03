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

	@Override
	public String type() {
		return "bool";
	}	
}
