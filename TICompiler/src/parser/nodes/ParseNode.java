package parser.nodes;

import java.util.HashMap;
import java.util.Map;

public abstract class ParseNode {	
	public abstract String toString();
	public abstract void setSubParseNodeVariables(Map<String, String> superVariables) throws Exception;
	public abstract void setFunctions(Map<String, FunctionDeclerationPN> functions);
	public abstract void checkTypes(String returnType) throws Exception;
	public Map<String, String> variables = new HashMap<String, String>();
	public Map<String, FunctionDeclerationPN> functions = new HashMap<String, FunctionDeclerationPN>();
}
