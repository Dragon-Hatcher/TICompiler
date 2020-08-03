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
	public int line = -1;
	public int col = -1;
	public ParseNode lineCol(int line, int col) {
		this.line = line;
		this.col = col;
		return this;
	}
	public String lcText() {
		return "line " + line + ", col " + col;
	}
}

