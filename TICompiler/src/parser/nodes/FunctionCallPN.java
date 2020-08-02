package parser.nodes;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

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


}
