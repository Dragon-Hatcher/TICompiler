package intermediateLanguageGenerator.nodes;

public class CallCloseScopeILPN extends ILParseNode {

	int scopesToClose;
	
	public CallCloseScopeILPN(int scopesToClose) {
		this.scopesToClose = scopesToClose;
	}

	@Override
	public String toString() {
		return "Call close scope times " + scopesToClose;
	}

}
