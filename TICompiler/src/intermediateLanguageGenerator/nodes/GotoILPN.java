package intermediateLanguageGenerator.nodes;

public class GotoILPN extends ILParseNode {
	
	String label = "";
	ConditionTypes condition;
	
	public GotoILPN(String label, ConditionTypes condition) {
		this.label = label;
		this.condition = condition;
	}

	@Override
	public String toString() {
		return "Goto " + label + " if " + condition;
	}

}
