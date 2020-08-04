package parser.nodes;

import java.util.Map;

public class NumLitteralPN extends ParseNode implements Evaluable {

	public String num = "";
	
	public NumLitteralPN (String num) {
		this.num = num;
	}
	
	@Override
	public String toString() {
		return "("+num+")";
	}

	@Override
	public String type() {
		return num.contains(".") ? "float" : "int";
	}

}
