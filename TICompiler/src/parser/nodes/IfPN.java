package parser.nodes;

import java.util.ArrayList;
import java.util.Set;

public class IfPN extends ParseNode implements Instruction {
	
	Evaluable condition = null;
	InstructionSequencePN instructions = new InstructionSequencePN();
	InstructionSequencePN elseInstructions = null;
	
	public IfPN(Evaluable condition, InstructionSequencePN instructions, InstructionSequencePN elseInstructions) {
		this.condition = condition;
		this.instructions = instructions;
		this.elseInstructions = elseInstructions;
	}

	@Override
	public String toString() {
		ArrayList<String> conditionLines = new ArrayList<String>();
		ArrayList<String> bodyLines = new ArrayList<String>();
		ArrayList<String> elseBodyLines = new ArrayList<String>();
		
		String[] cLines = condition.toString().split("\n");
		for (String j : cLines) {
			conditionLines.add("    " + j);
		}
		
		String[] iLines = instructions.toString().split("\n");
		for (String j : iLines) {
			bodyLines.add("    " + j);
		}

		if(elseInstructions != null) {
			String[] eLines = elseInstructions.toString().split("\n");
			for (String j : eLines) {
				elseBodyLines.add("    " + j);
			}
		}
		
		return "(If:\n" +
		"  (Condition:\n" +
		String.join("\n", conditionLines) + "\n" + 
		"  )\n" +
		"  (Body:\n" +
		String.join("\n", bodyLines) +
		"\n  )\n" +
		"  (Else Body:\n" +
		String.join("\n", elseBodyLines) +
		"\n  )\n" +
		")\n";
	}

	public boolean willReturn() {
		return elseInstructions != null && elseInstructions.willReturn() && instructions.willReturn();
	}
	
	public boolean hasIllegalBreak() {
		return instructions.hasIllegalBreak() || elseInstructions.hasIllegalBreak();
	}

	public String hasIllegalDeclerationType(Set<String> types) {
		String ifBody = instructions.hasIllegalDeclerationType(types);
		String elseBody = elseInstructions.hasIllegalDeclerationType(types);

		if(ifBody != null) {
			return ifBody;
		} else if (elseBody != null) {
			return elseBody;
		} else {
			return null;
		}

	}

}
