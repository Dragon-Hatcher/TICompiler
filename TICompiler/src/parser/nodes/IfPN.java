package parser.nodes;

import java.util.ArrayList;

public class IfPN extends ParseNode implements Instruction {
	
	Evaluable condition = null;
	InstructionSequencePN instructions = new InstructionSequencePN();
	
	@Override
	public String toString() {
		ArrayList<String> conditionLines = new ArrayList<String>();
		ArrayList<String> bodyLines = new ArrayList<String>();

		String[] cLines = condition.toString().split("\n");
		for (String j : cLines) {
			conditionLines.add("  " + j);
		}
		
		String[] iLines = instructions.toString().split("\n");
		for (String j : iLines) {
			bodyLines.add("  " + j);
		}

		return "(If" +
		"  (Condition:\n" +
		String.join("\n", conditionLines) + 
		"  )\n" +
		"  (Body:\n" +
		String.join("\n", bodyLines) +
		"  )\n" +
		")\n";
	}

}
