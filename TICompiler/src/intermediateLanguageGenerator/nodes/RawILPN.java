package intermediateLanguageGenerator.nodes;

public class RawILPN extends ILParseNode {

	public String text;
	
	public RawILPN(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return "Raw " + text;
	}
	
	public void replaceSection(String sectionCode) {
		text = text.replaceAll("\\*", "_raw_" + sectionCode);
	}

}
