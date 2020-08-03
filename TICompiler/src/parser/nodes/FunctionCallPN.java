package parser.nodes;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import parser.exceptions.MismatchedTypeException;
import parser.exceptions.UseOfUnknownFunctionException;
import semanticAnalyzer.exceptions.IllegalFunctionCallException;

public class FunctionCallPN extends ParseNode implements Evaluable, Instruction {

	public String functionName = "";
	public ArrayList<Evaluable> params = new ArrayList<Evaluable>();
	
	@Override
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
	
	public void hasBadFunctionNameOrLength() throws Exception {
		if(!functions.containsKey(functionName)) {
			throw new IllegalFunctionCallException("Illegal call to unknown function " + functionName + " on " + lcText() + ".");
		}
		
		if(functions.get(functionName).parameters.size() != params.size()) {
			throw new IllegalFunctionCallException("Call to function " + functionName + " on " + lcText() + " has " + params.size() + " parameters instead of " + functions.get(functionName).parameters.size() + ".");
		}
	}

	@Override
	public void checkFunctionNameAndLength() throws Exception {
		hasBadFunctionNameOrLength();
		
		for(Evaluable e : params) {
			((ParseNode)e).checkFunctionNameAndLength();
		}
	}

	@Override
	public void setSubParseNodeVariables(Map<String, String> superVariables) throws Exception {
		this.variables = superVariables;
		for(Evaluable param : params) {
			((ParseNode)param).setSubParseNodeVariables(superVariables);
		}
	}

	@Override
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
			throw new UseOfUnknownFunctionException("Use of unknown function " + functionName + " on " + lcText() + ".");
		}
	}
	
	@Override
	public void checkTypes(String returnType) throws Exception {
		if(!functions.containsKey(functionName)) {
			throw new UseOfUnknownFunctionException("Use of unknown function " + functionName + " on " + lcText() + ".");			
		}
		FunctionDeclerationPN func = functions.get(functionName);
		
		for(int i = 0; i < params.size(); i++) {
			if(!func.parameters.get(i).type.equals(params.get(i).type())) {
				throw new MismatchedTypeException("Parameter " + i + " of function " + functionName + " is of type " + func.parameters.get(i).type + " but it was called with type " + params.get(i).type() + " on " + lcText() + ".");
			}
			((ParseNode)params.get(i)).checkTypes(returnType);
		}
	}

}
