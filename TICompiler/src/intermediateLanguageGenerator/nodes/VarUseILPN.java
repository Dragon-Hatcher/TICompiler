package intermediateLanguageGenerator.nodes;

public class VarUseILPN extends ILParseNode implements ILEvaluable {

	String name = "";
	
	public VarUseILPN(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "var " + name;
	}

}
