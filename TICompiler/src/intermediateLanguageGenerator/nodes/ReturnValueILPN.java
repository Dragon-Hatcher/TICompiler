package intermediateLanguageGenerator.nodes;

public class ReturnValueILPN extends ILParseNode {

	String varToReturn = "";
	
	public ReturnValueILPN(String varToReturn) {
		this.varToReturn = varToReturn;
	}

	@Override
	public String toString() {
		return "Return var " + varToReturn;
	}

}
