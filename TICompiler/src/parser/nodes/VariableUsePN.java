package parser.nodes;

import java.util.Map;
import java.util.Set;

import parser.exceptions.UseOfUnknownVariableException;
import parser.exceptions.VariableUsedBeforeDeclaredException;

public class VariableUsePN extends ParseNode implements Evaluable {

	public String name = "";
	
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
	
	@Override
	public void checkVariableUsedBeforeDeclared(Set<String> vars) throws Exception {
		if(!vars.contains(name)) {
			throw new VariableUsedBeforeDeclaredException("Variable " + name + " used before declared on " + lcText() + ".");
		}
	}
}
