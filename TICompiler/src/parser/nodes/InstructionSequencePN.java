package parser.nodes;

import java.util.ArrayList;
import java.util.Set;

public class InstructionSequencePN extends ParseNode implements Instruction {

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

	public boolean willReturn() {
		for(Instruction i : instructions) {
			if(i.willReturn()) {
				return true;
			}
		}
		return false;
	}
	
	public boolean hasIllegalBreak() {
		for(Instruction i : instructions) {
			if(i.hasIllegalBreak()) {
				return true;
			}
		}
		return false;
	}

	public String hasIllegalDeclerationType(Set<String> types) {
		for(Instruction i : instructions) {
			String iHIDT = i.hasIllegalDeclerationType(types);
			if(iHIDT != null) {
				return iHIDT;
			}
		}
		return null;
	}
}
