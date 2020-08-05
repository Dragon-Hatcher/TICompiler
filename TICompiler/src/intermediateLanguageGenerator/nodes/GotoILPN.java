package intermediateLanguageGenerator.nodes;

public class GotoILPN extends ILParseNode {
	
	String label = "";
	String ifVar = "";
	
	public GotoILPN(String label, String ifVar) {
		this.label = label;
		this.ifVar = ifVar;
	}

	public GotoILPN(String label) {
		this.label = label;
	}
	
	@Override
	public String toString() {
		return "Goto " + label + (ifVar.equals("") ? "" : " if " + ifVar);
	}

}
