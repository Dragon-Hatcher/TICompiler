package parser.nodes;

import java.util.Map;

public interface Evaluable {
	public String toString();
	public FunctionCallPN checkFunctionNameAndLength(Map<String, FunctionDeclerationPN> functions);
}
