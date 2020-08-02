package parser.nodes;

import java.util.Map;
import java.util.Set;

public class BreakPN extends ParseNode implements Instruction {

	@Override
	public String toString() {
		return "(Break)";
	}

	public boolean willReturn() {
		return false;
	}

	public boolean hasIllegalBreak() {
		return true;
	}

	public String hasIllegalDeclerationType(Set<String> types) {
		return null;
	}

	public FunctionCallPN checkFunctionNameAndLength(Map<String, FunctionDeclerationPN> functions) {
		return null;
	}

}
