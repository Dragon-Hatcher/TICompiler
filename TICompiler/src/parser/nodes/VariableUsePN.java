package parser.nodes;

import java.util.Map;

import parser.exceptions.UseOfUnknownVariableException;

public class VariableUsePN extends ParseNode implements Evaluable {

	String name = "";
	
	public VariableUsePN(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "(var: " + name + ")";
	}

	@Override
	public String type() throws Exception {
		if(variables.containsKey(name)) {
			return variables.get(name);
		} else {
			throw new UseOfUnknownVariableException("Use of unknown variable " + name + " on " + lcText() + ".");
		}
	}
	
}
