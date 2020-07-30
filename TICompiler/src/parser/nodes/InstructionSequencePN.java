package parser.nodes;

import java.util.ArrayList;

public class InstructionSequencePN extends ParseNode {

	public ArrayList<Instruction> instructions = new ArrayList<Instruction>();

	@Override
	public String toString() {
		ArrayList<String> lines = new ArrayList<String>();

		for(Instruction i : instructions) {
			String[] iLines = i.toString().split("\n");
			for(String j : iLines) {
				lines.add("  " + j);
			}
		}
		
		return "(InstructionSequence:\n" + String.join("\n", lines) + "\n)\n";
	}

}
