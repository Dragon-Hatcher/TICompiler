package intermediateLanguageGenerator.nodes;

public class RawFILPN extends ILParseNode {

	public String text;
	
	public RawFILPN(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return "RawF " + text;
	}

}
