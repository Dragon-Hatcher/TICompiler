package intermediateLanguageGenerator.nodes;

public class CallILPN extends ILParseNode {

	public String funcToCall;

	public CallILPN(String funcToCall) {
		this.funcToCall = funcToCall;
	}

	@Override
	public String toString() {
		return "Call " + funcToCall;
	}

}
