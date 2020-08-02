package parser.nodes;

import java.util.Map;
import java.util.Set;

public interface Instruction {
	public String toString();
	public boolean willReturn();
	public boolean hasIllegalBreak();
	public String hasIllegalDeclerationType(Set<String> types);
	public FunctionCallPN checkFunctionNameAndLength(Map<String, FunctionDeclerationPN> functions);
}