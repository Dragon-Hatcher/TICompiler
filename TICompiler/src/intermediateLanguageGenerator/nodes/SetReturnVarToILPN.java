package intermediateLanguageGenerator.nodes;

public class SetReturnVarToILPN extends ILParseNode {

	String returnVar = "";
	
	public SetReturnVarToILPN(String returnVar) {
		this.returnVar = returnVar;
	}

	@Override
	public String toString() {
		return "Set return var to " + returnVar;
	}

}
