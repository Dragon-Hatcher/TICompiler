package parser.nodes;

public class RawFPN extends ParseNode implements Instruction {

	public String text;
	
	public RawFPN(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return "(Rawf: " + text + ")";
	}

}
