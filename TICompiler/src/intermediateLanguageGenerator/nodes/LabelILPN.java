package intermediateLanguageGenerator.nodes;

public class LabelILPN extends ILParseNode {

	public String label = "";
	
	public LabelILPN(String label) {
		this.label = label;
	}

	@Override
	public String toString() {
		return label + ":";
	}

}
