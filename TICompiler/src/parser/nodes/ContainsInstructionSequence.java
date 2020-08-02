package parser.nodes;

import java.util.Map;
import java.util.Set;

public interface ContainsInstructionSequence {
	public boolean willReturn();
	public boolean hasIllegalBreak();
	public VariableDeclerationPN hasIllegalDeclerationType(Set<String> types);
	public FunctionCallPN checkFunctionNameAndLength(Map<String, FunctionDeclerationPN> functions);
}
