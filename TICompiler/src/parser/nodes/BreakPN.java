package parser.nodes;

import java.util.Map;
import java.util.Set;

public class BreakPN extends ParseNode implements Instruction {

	@Override
	public String toString() {
		return "(Break)";
	}

	public FunctionCallPN checkFunctionNameAndLength(Map<String, FunctionDeclerationPN> functions) {
		return null;
	}

}
