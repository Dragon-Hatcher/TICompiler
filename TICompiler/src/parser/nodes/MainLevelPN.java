package parser.nodes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import semanticAnalyzer.exceptions.IncorectFunctionReturnException;
import toolkit.Copy;

public class MainLevelPN extends ParseNode {

	public ArrayList<FunctionDeclerationPN> functions = new ArrayList<FunctionDeclerationPN>();
	public InstructionSequencePN main = new InstructionSequencePN();

	@Override
	public String toString() {
		ArrayList<String> functionLines = new ArrayList<String>();
		ArrayList<String> instructionLines = new ArrayList<String>();

		for(FunctionDeclerationPN f : functions) {
			String[] fLines = f.toString().split("\n");
			for(String j : fLines) {
				functionLines.add("    " + j);
			}
		}
		
		String[] iLines = main.toString().split("\n");
		for(String j : iLines) {
			instructionLines.add("  " + j);
		}
		
		return "(Main:\n" + 
		"  (Functions:\n" +
		String.join("\n", functionLines) + "\n" + 
		"  )\n" +
		String.join("\n", instructionLines) + "\n" +
		")\n";
	}

	@Override
	public void setSubParseNodeVariables(Map<String, String> superVariables) throws Exception {
		Map<String, String> emptyMap = new HashMap<String, String>();
		main.setSubParseNodeVariables(emptyMap);
		for(FunctionDeclerationPN func : functions) {
			func.setSubParseNodeVariables(Copy.deepCopyMap(main.variables));
		}
	}
	
	@Override
	public void setFunctions(Map<String, FunctionDeclerationPN> functions) {
		main.setFunctions(functions);
		for(FunctionDeclerationPN func : this.functions) {
			func.setFunctions(functions);
		}
	}

	@Override
	public void checkTypes(String returnType) throws Exception {
		main.checkTypes("");
		for(FunctionDeclerationPN func : functions) {
			func.checkTypes(main.functions.get(func.name).returnType);
		}
	}
	
	public void checkFunctionNameAndLength(Map<String, FunctionDeclerationPN> functions) throws Exception {
		main.checkFunctionNameAndLength();
		for(FunctionDeclerationPN f : this.functions) {
			f.checkFunctionNameAndLength();
		}
	}
	
	public void hasIllegalDeclerationType(Set<String> types) throws Exception {
		main.hasIllegalDeclerationType(types);
		for(FunctionDeclerationPN f : this.functions) {
			f.hasIllegalDeclerationType(types);
		}
	}
	
	public void hasIllegalBreaks() throws Exception {
		main.hasIllegalBreak();
		for(FunctionDeclerationPN f : this.functions) {
			f.hasIllegalBreak();
		}		
	}

	public void checkFunctionsReturn() throws Exception{
		for(FunctionDeclerationPN i : functions) {
			if(i.shouldReturn() && !i.willReturn()) {
				throw new IncorectFunctionReturnException("Function " + i.name + " must return.");
			}
		}
	}

	@Override
	public void checkVariableUsedBeforeDeclared(Set<String> vars) throws Exception {
		main.checkVariableUsedBeforeDeclared(new HashSet<String>());
		for(FunctionDeclerationPN i : functions) {
			i.checkVariableUsedBeforeDeclared(Copy.deepCopySet(main.variables.keySet()));
		}
	}

}
