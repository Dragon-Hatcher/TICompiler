package intermediateLanguageGenerator.nodes;

public class SetParamToVarILPN extends ILParseNode {

	int paramNumber;
	String varToSetParamTo;

	public SetParamToVarILPN(int paramNumber, String varToSetParamTo) {
		this.paramNumber = paramNumber;
		this.varToSetParamTo = varToSetParamTo;
	}

	@Override
	public String toString() {
		return "Set param " + paramNumber + " to " + varToSetParamTo;
	}
	

}
