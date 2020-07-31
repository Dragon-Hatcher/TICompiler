package parser.nodes;

public class VariableUsePN extends ParseNode implements Evaluable {

	String name = "";
	
	public VariableUsePN(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "(var: " + name + ")";
	}

}
