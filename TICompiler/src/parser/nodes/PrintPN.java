package parser.nodes;

public class PrintPN extends ParseNode implements Instruction {

	public PrintPN() {
	}

	@Override
	public String toString() {
		return "Print";
	}

}
