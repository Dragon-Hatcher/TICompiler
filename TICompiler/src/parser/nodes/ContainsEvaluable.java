package parser.nodes;

import java.util.Map;

public interface ContainsEvaluable {
	public FunctionCallPN checkFunctionNameAndLength(Map<String, FunctionDeclerationPN> functions);
}
