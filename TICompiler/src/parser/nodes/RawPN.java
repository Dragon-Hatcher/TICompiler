package parser.nodes;

public class RawPN extends ParseNode implements Instruction {

	public String text;
	
	public RawPN(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return "(Raw: " + text + ")";
	}

}
