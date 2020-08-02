package parser.nodes;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import parser.exceptions.UseOfUnknownFunctionException;

public class FunctionCallPN extends ParseNode implements Evaluable, Instruction, ContainsEvaluable {

	public String functionName = "";
	public ArrayList<Evaluable> params = new ArrayList<Evaluable>();
	
	public String toString() {
		ArrayList<String> paramLines = new ArrayList<String>();
		
		for(Evaluable p : params) {
			String[] pLines = p.toString().split("\n");
			for(String j : pLines) {
				paramLines.add("    " + j);
			}
		}
		
		return "(Function Call:\n  (Name: " + functionName + ")\n  (Params:\n" + String.join("\n", paramLines) + "\n  )\n)\n";
	}
	
	public boolean hasBadFunctionNameOrLength(Map<String, FunctionDeclerationPN> functions) {
		if(!functions.containsKey(functionName)) {
			return true;
		}
		
		if(functions.get(functionName).parameters.size() != params.size()) {
			return true;
		}
		
		return false;
	}

	@Override
	public FunctionCallPN checkFunctionNameAndLength(Map<String, FunctionDeclerationPN> functions) {
		if(hasBadFunctionNameOrLength(functions)) {
			return this;
		}
		
		for(Evaluable e : params) {
			if(e instanceof ContainsEvaluable) {
				FunctionCallPN eFC = ((ContainsEvaluable)e).checkFunctionNameAndLength(functions);
				if(eFC != null) {
					return eFC;
				}
			}
		}
		
		return null;
	}

	public void setSubParseNodeVariables(Map<String, String> superVariables) throws Exception {
		this.variables = superVariables;
		for(Evaluable param : params) {
			((ParseNode)param).setSubParseNodeVariables(superVariables);
		}
	}

	public void setFunctions(Map<String, FunctionDeclerationPN> functions) {
		this.functions = functions;
		for(Evaluable p : params) {
			((ParseNode)p).setFunctions(functions);
		}
	}

	@Override
	public String type() throws Exception {
		if(functions.containsKey(functionName)) {
			return functions.get(functionName).returnType;
		} else {
			throw new UseOfUnknownFunctionException("Use of unknown function " + functionName + ".");
		}
	}
}
