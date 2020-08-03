package parser.nodes;

import java.util.Map;
import java.util.Set;

public class BreakPN extends ParseNode implements Instruction {

	@Override
	public String toString() {
		return "(Break)";
	}

}
