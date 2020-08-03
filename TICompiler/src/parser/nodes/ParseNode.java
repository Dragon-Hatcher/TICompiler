package parser.nodes;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class ParseNode {	
	
	public int line = -1;
	public int col = -1;
	public Map<String, String> variables = new HashMap<String, String>();
	public Map<String, FunctionDeclerationPN> functions = new HashMap<String, FunctionDeclerationPN>();
	
	
	public abstract String toString();
	
	public void checkVariableUsedBeforeDeclared(Set<String> vars) throws Exception {		
	}
	
	public void setSubParseNodeVariables(Map<String, String> superVariables) throws Exception {
		this.variables = superVariables;
	}
	
	public void setFunctions(Map<String, FunctionDeclerationPN> functions) {
		this.functions = functions;
	}
	
	public void checkTypes(String returnType) throws Exception {		
	}
	
	public void checkFunctionNameAndLength() throws Exception {		
	};

	
	public ParseNode lineCol(int line, int col) {
		this.line = line;
		this.col = col;
		return this;
	}
	
	public String lcText() {
		return "line " + line + ", col " + col;
	}
}

