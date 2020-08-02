package semanticAnalyzer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import parser.nodes.*;
import semanticAnalyzer.exceptions.*;

public class SemanticAnalyzer {

	Set<String> types = new HashSet<String>(Arrays.asList("int", "float", "bool"));
	Map<String, FunctionDeclerationPN> functions = new HashMap<String, FunctionDeclerationPN>();
	
	public void analyze(MainLevelPN mainLevel) throws Exception {
		for(FunctionDeclerationPN i : mainLevel.functions) {
			if(functionShouldReturn(i) && !i.instructions.willReturn()) {
				throw new InccorectFunctionReturnException("Function \"" + i.name + "\" must return.");
			}
			
			if(i.instructions.hasIllegalBreak()) {
				throw new IllegalBreakException("Illegal break in function " + i.name + ".");
			}
			
			String iHITD = i.instructions.hasIllegalDeclerationType(types);
			if(iHITD != null) {
				//TODO change to passing back the decleration object
				throw new IllegalTypeDeclerationException("Illegal decleration of type " + iHITD + " in function " + i.name + ".");
			}
			
			for(VariableDeclerationPN param : i.parameters) {
				if(!types.contains(param.type)) {
					throw new IllegalTypeDeclerationException("Illegal decleration of type " + param.type + " for parameter " + param.name + " in function " + i.name + ".");
				}
			}
			
			functions.put(i.name, i);
		}
		
		if(mainLevel.main.hasIllegalBreak()) {
			throw new IllegalBreakException("Illegal break in main.");
		}		

		String iHITD = mainLevel.main.hasIllegalDeclerationType(types);
		if(iHITD != null) {
			throw new IllegalTypeDeclerationException("Illegal decleration of type " + iHITD + " in main.");
		}
		
		FunctionCallPN mainFC = mainLevel.main.checkFunctionNameAndLength(functions);
		if(mainFC != null) {
			if(!functions.containsKey(mainFC.functionName)) {
				throw new IllegalFunctionCallException("Illegal call to unknown function " + mainFC.functionName + " in main.");
			} else {
				throw new IllegalFunctionCallException("Call to function " + mainFC.functionName + " in main has " + mainFC.params.size() + " parameters instead of " + functions.get(mainFC.functionName).parameters.size() + ".");
			}
		}
		
		for(FunctionDeclerationPN i : mainLevel.functions) {
			FunctionCallPN funcFC = i.instructions.checkFunctionNameAndLength(functions);
			if(funcFC != null) {
				if(!functions.containsKey(funcFC.functionName)) {
					throw new IllegalFunctionCallException("Illegal call to unknown function " + funcFC.functionName + " in function " + i.name + ".");
				} else {
					throw new IllegalFunctionCallException("Call to function " + funcFC.functionName + " in function " + i.name + "has " + funcFC.params.size() + " parameters instead of " + functions.get(funcFC.functionName).parameters.size() + ".");
				}
			}			
		}

		// check functions return v/
		// check break is in a loop  v/
		// check illegal type declaration v/
		// check func param types v/
		// check function call exists
		// check function call length
	}
	
	private boolean functionShouldReturn(FunctionDeclerationPN func) {
		return !func.returnType.equals("");
	}
	
}